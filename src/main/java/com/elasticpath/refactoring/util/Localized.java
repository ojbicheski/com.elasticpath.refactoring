/**
 * 
 */
package com.elasticpath.refactoring.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.elasticpath.refactoring.enums.FulfillmentMessages;

/**
 * @author orlei
 *
 */
public class Localized {
	
	public static String resolveLocalizedString(final FulfillmentMessages msg, Locale locale, final Object...bindings) {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("messages", locale);
		String key = bundle.getString(msg.getMessageKey());
		return MessageFormat.format(key, bindings);
	}

}
