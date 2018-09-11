package com.elasticpath.refactoring;

import java.util.Locale;

import com.elasticpath.rules.Result;
import com.elasticpath.rules.RulesProcessor;

public class FulfillmentPresenter {

	/**
	 * Converts an PaymentMethodDetails object into a human-readable string representation.
	 *
	 * @param paymentMethodDetails a DTO representing the payment details
	 * @return human-readable string description of the payment method
	 */
	public String getPaymentString(final PaymentMethodDetails paymentMethodDetails) {
		return getPaymentString(paymentMethodDetails, Locale.getDefault());
	}

	/**
	 * Converts an PaymentMethodDetails object into a human-readable string representation.
	 *
	 * @param paymentMethodDetails a DTO representing the payment details
	 * @param locale the user's current locale
	 * @return human-readable string description of the payment method
	 */
	public String getPaymentString(final PaymentMethodDetails paymentMethodDetails, final Locale locale) {
		String paymentString = "";
		if (paymentMethodDetails == null) {
			return paymentString;
		}
		
		RulesProcessor processor = RulesProcessor.builder().sessionName("paymentKS").build();

		processor.compile();
		
		processor.addFact(paymentMethodDetails);
		processor.addFact(locale);
		processor.addFact(AuthorizationService.getInstance());
		
		Result<String> result = new Result<String>();
		
		processor.fire();
		
		//result = (Result<String>) processor.getFact(handler);
		
		return result.get();
	}
}
