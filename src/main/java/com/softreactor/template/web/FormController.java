package com.softreactor.template.web;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * FormController class.
 * Base class for controllers that uses forms.
 */
public abstract class FormController {

	/**
	 * Initializes data binder.
	 * Trims form input.
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}
