/**
 * 
 */
package com.elasticpath.refactoring;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.testng.annotations.Test;

import com.elasticpath.refactoring.enums.PaymentAttributeKey;

/**
 * @author orlei
 *
 */
@Test
public class PaymentTest {
	
	private FulfillmentPresenter fulfillment;
	
	@PostConstruct
	public void start() {
		fulfillment = new FulfillmentPresenter();
	}

	@Test
	public void testPaymentGoogle() {
		Map<PaymentAttributeKey, Object> attributes = new HashMap<>();
		attributes.put(PaymentAttributeKey.SHIPMENT_NUMBER, "01");
		attributes.put(PaymentAttributeKey.EMAIL, "email@email.com");
		
		PaymentMethodDetails paymentMethodDetails = new PaymentMethodDetails();
		paymentMethodDetails.setAttributeMap(attributes);
		
		paymentMethodDetails.setPaymentType("GIFT_CERTIFICATE"); // GOOGLE_CHECKOUT
		

		System.out.println("RESULT: " + 
				fulfillment.getPaymentString(paymentMethodDetails, Locale.US));
	}
}
