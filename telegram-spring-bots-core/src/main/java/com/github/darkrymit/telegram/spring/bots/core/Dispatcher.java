package com.github.darkrymit.telegram.spring.bots.core;


import com.github.darkrymit.telegram.spring.bots.core.exception.NestedTelegramException;
import com.github.darkrymit.telegram.spring.bots.core.exception.NoExceptionHandlerFoundException;
import com.github.darkrymit.telegram.spring.bots.core.exception.NoHandlerFoundException;
import com.github.darkrymit.telegram.spring.bots.core.exception.TelegramException;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.ExceptionHandlerAdapter;
import com.github.darkrymit.telegram.spring.bots.core.exception.handler.ExceptionHandlerResolver;
import com.github.darkrymit.telegram.spring.bots.core.handler.HandlerAdapter;
import com.github.darkrymit.telegram.spring.bots.core.handler.HandlerExecutionChain;
import com.github.darkrymit.telegram.spring.bots.core.handler.HandlerResolver;
import com.github.darkrymit.telegram.spring.bots.core.support.ModelAndView;
import com.github.darkrymit.telegram.spring.bots.core.support.View;
import com.google.common.base.Stopwatch;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;


public class Dispatcher {

  private static final Logger log = LoggerFactory.getLogger(Dispatcher.class);

  private final List<BotRegistration> bots = new ArrayList<>();

  private final TelegramRequestResolver resolver;

  private final HandlerResolver handlerResolver;

  private final HandlerAdapter handlerAdapter;

  private final ExceptionHandlerResolver exceptionHandlerResolver;

  private final ExceptionHandlerAdapter exceptionHandlerAdapter;

  private final ExecutorService executorService = new ThreadPoolExecutor(0, 6, 60L,
      TimeUnit.SECONDS, new SynchronousQueue<>());

  public Dispatcher(List<BotRegistration> registrations, TelegramRequestResolver resolver,
      HandlerResolver handlerResolver, HandlerAdapter handlerAdapter,
      ExceptionHandlerResolver exceptionHandlerResolver,
      ExceptionHandlerAdapter exceptionHandlerAdapter) {
    this.resolver = resolver;
    this.handlerResolver = handlerResolver;
    this.handlerAdapter = handlerAdapter;
    this.exceptionHandlerResolver = exceptionHandlerResolver;
    this.exceptionHandlerAdapter = exceptionHandlerAdapter;
    this.bots.addAll(registrations);
  }

  public void start() {
    log.debug("Begin starting dispatcher with bots: {} ", bots);
    bots.forEach(botRegistration -> botRegistration.getBot().setUpdatesListener(updates -> {
      executorService.execute(
          () -> updates.forEach(update -> handleUpdate(botRegistration, update)));
      return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }, new GetUpdates()));
    log.info("Dispatcher started with bots: {} ", bots);
  }

  public void handleUpdate(BotRegistration botRegistration, Update update) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    try {
      doHandleUpdate(botRegistration, update);
    } catch (Exception e) {
      log.info("Exception occur and not handled", e);
    } catch (Throwable e) {
      log.info("Throwable occur and not handled", e);
    } finally {
      stopwatch.stop();
      if (log.isTraceEnabled()) {
        log.trace("Handle update [{}] in time: {}", update.updateId(), stopwatch);
      }
    }
  }

  private void doHandleUpdate(BotRegistration botRegistration, Update update) throws Exception {
    TelegramRequest processedRequest = new TelegramRequest(botRegistration, update,
        UpdateType.from(update), null, null);
    HandlerExecutionChain executionChain = null;
    try {
      ModelAndView modelAndView = null;
      Exception dispatchException = null;
      try {
        TelegramRequest request = resolveUpdateData(botRegistration, update);
        processedRequest = request;

        executionChain = resolveHandler(request);
        if (executionChain == null) {
          throw new NoHandlerFoundException("No handler found ", request);
        }
        if (!executionChain.applyPreHandle(request)) {
          log.debug("Execution cancelled of {} for {}", executionChain, request);
          return;
        }

        modelAndView = invokeHandler(executionChain, request);

        executionChain.applyPostHandle(request);
      } catch (Exception e) {
        dispatchException = e;
      } catch (Throwable e) {
        dispatchException = new NestedTelegramException("HandlerMapping dispatch failed", e);
      }
      processDispatchResult(processedRequest, executionChain, modelAndView, dispatchException);
    } catch (Exception e) {
      if (executionChain != null) {
        //processedRequest will not be null here
        executionChain.triggerAfterCompletion(processedRequest, e);
      }
      throw e;
    } catch (Throwable e) {
      if (executionChain != null) {
        //processedRequest will not be null here
        executionChain.triggerAfterCompletion(processedRequest,
            new NestedTelegramException("HandlerMapping processing failed", e));
      }
      throw e;
    }
  }

  private void processDispatchResult(TelegramRequest processedRequest,
      @Nullable HandlerExecutionChain executionChain, @Nullable ModelAndView modelAndView,
      @Nullable Exception dispatchException) throws Exception {

    ModelAndView mv = modelAndView;

    if (dispatchException != null) {
      mv = processHandlerException(processedRequest, executionChain, dispatchException);
    }

    if (mv != null) {
      render(mv, processedRequest.getBot());
    }

    if (executionChain != null) {
      // Exception (if any) is already handled..
      executionChain.triggerAfterCompletion(processedRequest, null);
    }
  }

  private void render(ModelAndView mv, TelegramBot bot) throws Exception {
    Stopwatch stopwatch = Stopwatch.createStarted();
    View view = mv.getView();
    String viewName = mv.getViewName();

    if (view == null && viewName == null) {
      throw new TelegramException("No view or view name specified in ModelAndView");
    }

    if (viewName != null) {
      //TODO: resolve view by name
      return;
    }

    view.render(mv.getModel(), bot);
    stopwatch.stop();
    if (log.isTraceEnabled()) {
      log.trace("Render view [{}] took: {}", view, stopwatch);
    }

  }

  private TelegramRequest resolveUpdateData(BotRegistration botRegistration, Update update) {
    Stopwatch stopwatch = Stopwatch.createStarted();
    TelegramRequest telegramRequest = resolver.resolve(botRegistration, update);
    stopwatch.stop();
    if (log.isTraceEnabled()) {
      log.trace("Update [{}] data resolving took: {}", update.updateId(), stopwatch);
    }
    return telegramRequest;
  }

  private @Nullable HandlerExecutionChain resolveHandler(TelegramRequest telegramRequest)
      throws Exception {
    Stopwatch stopwatch = Stopwatch.createStarted();
    HandlerExecutionChain handlerExecutionChain = handlerResolver.getHandler(telegramRequest);
    stopwatch.stop();
    if (log.isTraceEnabled()) {
      log.trace("Update [{}] handler resolving took: {}", telegramRequest.getUpdate().updateId(),
          stopwatch);
    }
    return handlerExecutionChain;
  }

  @Nullable
  private ModelAndView invokeHandler(HandlerExecutionChain executionChain,
      TelegramRequest telegramRequest) throws Exception {
    Stopwatch stopwatch = Stopwatch.createStarted();
    ModelAndView mv = handlerAdapter.handle(telegramRequest, executionChain.getHandler());
    stopwatch.stop();
    if (log.isTraceEnabled()) {
      log.trace("Update [{}] handler invoke took: {}", telegramRequest.getUpdate().updateId(),
          stopwatch);
    }
    return mv;
  }

  @Nullable
  private ModelAndView processHandlerException(TelegramRequest telegramRequest,
      @Nullable HandlerExecutionChain executionChain, Exception exception) throws Exception {
    Object handler = executionChain != null ? executionChain.getHandler() : null;

    Object exceptionHandler = resolveExceptionHandler(telegramRequest, exception, handler);

    if (exceptionHandler == null) {
      throw new NoExceptionHandlerFoundException(
          "No exception handler found fot update " + telegramRequest.getUpdate().updateId(),
          exception);
    }

    return invokeExceptionHandler(telegramRequest, exception, handler, exceptionHandler);
  }

  @Nullable
  private ModelAndView invokeExceptionHandler(TelegramRequest telegramRequest, Exception exception,
      @Nullable Object handler, Object exceptionHandler) throws Exception {
    Stopwatch stopwatch = Stopwatch.createStarted();
    ModelAndView modelAndView = exceptionHandlerAdapter.handleException(telegramRequest, handler,
        exception, exceptionHandler);
    stopwatch.stop();
    if (log.isTraceEnabled()) {
      log.trace("Update [{}] exception handler invoke took: {}",
          telegramRequest.getUpdate().updateId(), stopwatch);
    }
    return modelAndView;
  }

  @Nullable
  private Object resolveExceptionHandler(TelegramRequest telegramRequest, Exception exception,
      @Nullable Object handler) throws Exception {
    Stopwatch stopwatch = Stopwatch.createStarted();
    Object exceptionHandler = exceptionHandlerResolver.getExceptionHandler(telegramRequest, handler,
        exception);
    stopwatch.stop();
    if (log.isTraceEnabled()) {
      log.trace("Update [{}] exception handler resolving took: {}",
          telegramRequest.getUpdate().updateId(), stopwatch);
    }
    return exceptionHandler;
  }

  public void stop() {
    log.debug("Begin stopping dispatcher with bots: {} ", bots);
    bots.forEach(botRegistration -> botRegistration.getBot().removeGetUpdatesListener());
    log.info("Dispatcher stopped with bots: {} ", bots);
  }
}
