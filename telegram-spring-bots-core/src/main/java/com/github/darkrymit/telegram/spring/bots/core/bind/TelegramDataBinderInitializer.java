package com.github.darkrymit.telegram.spring.bots.core.bind;

import org.springframework.validation.DataBinder;

public interface TelegramDataBinderInitializer {

	/**
	 * Initialize the given DataBinder.
	 * @param binder the DataBinder to initialize
	 */
	void initBinder(DataBinder binder);

}
