package com.elasticpath.refactoring;

import java.util.HashMap;
import java.util.Map;

import com.elasticpath.refactoring.enums.OrderStatus;
import com.elasticpath.refactoring.enums.PaymentAttributeKey;

public final class PaymentMethodDetails {

	private String paymentType;
	private OrderStatus orderStatus;
	private Map<PaymentAttributeKey, Object> attributeMap;

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(final String paymentType) {
		this.paymentType = paymentType;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(final OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Map<PaymentAttributeKey, Object> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(final Map<PaymentAttributeKey, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public void addAttribute(final PaymentAttributeKey key, final Object value) {
		if (attributeMap == null) {
			attributeMap = new HashMap<>();
		}
		attributeMap.put(key, value);
	}

	public Object getAttribute(final PaymentAttributeKey key) {
		if (attributeMap != null) {
			return attributeMap.get(key);
		} else {
			return null;
		}
	}

	public String getAttributeAsString(final PaymentAttributeKey key) {
		return (String) getAttribute(key);
	}

}
