/**
 * 
 */
package com.elasticpath.refactoring;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.testng.annotations.Test;

import com.elasticpath.refactoring.enums.OrderStatus;
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

	@Test(priority = 0)
	public void testPaymentGoogle() {
		Map<PaymentAttributeKey, Object> attributes = new HashMap<>();
		attributes.put(PaymentAttributeKey.SHIPMENT_NUMBER, "01");
		attributes.put(PaymentAttributeKey.EMAIL, "email@email.com");
		
		PaymentMethodDetails paymentMethodDetails = new PaymentMethodDetails();
		paymentMethodDetails.setAttributeMap(attributes);
		
		paymentMethodDetails.setPaymentType("GOOGLE_CHECKOUT"); // GOOGLE_CHECKOUT

		System.out.println("RESULT: " + 
				fulfillment.getPaymentString(paymentMethodDetails, Locale.US));
		System.out.println("*********");
	}

	@Test(priority = 1)
	public void testPaymentGift() {
		Map<PaymentAttributeKey, Object> attributes = new HashMap<>();
		attributes.put(PaymentAttributeKey.SHIPMENT_NUMBER, "03");
		attributes.put(PaymentAttributeKey.EMAIL, "email-gift@email.com");
		
		PaymentMethodDetails paymentMethodDetails = new PaymentMethodDetails();
		paymentMethodDetails.setAttributeMap(attributes);
		
		paymentMethodDetails.setPaymentType("GIFT_CERTIFICATE"); // GOOGLE_CHECKOUT

		System.out.println("RESULT: " + 
				fulfillment.getPaymentString(paymentMethodDetails, Locale.US));
		System.out.println("*********");
	}

	@Test(priority = 1)
	public void testPaymentPaypal() {
		Map<PaymentAttributeKey, Object> attributes = new HashMap<>();
		attributes.put(PaymentAttributeKey.SHIPMENT_NUMBER, "03");
		attributes.put(PaymentAttributeKey.EMAIL, "email-paypal@email.com");
		
		PaymentMethodDetails paymentMethodDetails = new PaymentMethodDetails();
		paymentMethodDetails.setAttributeMap(attributes);
		
		paymentMethodDetails.setPaymentType("PAYPAL_EXPRESS"); // GOOGLE_CHECKOUT
		paymentMethodDetails.setOrderStatus(OrderStatus.AWAITING_EXCHANGE);

		System.out.println("RESULT: " + 
				fulfillment.getPaymentString(paymentMethodDetails, Locale.US));
		System.out.println("*********");
		
	}

	@Test(priority = 1)
	public void testPaymentReturn() {
		Map<PaymentAttributeKey, Object> attributes = new HashMap<>();
		attributes.put(PaymentAttributeKey.SHIPMENT_NUMBER, "03");
		attributes.put(PaymentAttributeKey.EMAIL, "email-paypal@email.com");
		
		PaymentMethodDetails paymentMethodDetails = new PaymentMethodDetails();
		paymentMethodDetails.setAttributeMap(attributes);
		
		paymentMethodDetails.setPaymentType("RETURN_AND_EXCHANGE"); // GOOGLE_CHECKOUT
		paymentMethodDetails.setOrderStatus(OrderStatus.AWAITING_EXCHANGE);

		System.out.println("RESULT: " + 
				fulfillment.getPaymentString(paymentMethodDetails, Locale.US));
		System.out.println("*********");
		
	}
}
