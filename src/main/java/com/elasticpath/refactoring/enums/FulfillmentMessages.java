package com.elasticpath.refactoring.enums;

public enum FulfillmentMessages {
	GOOGLE("payment.fulfillment.google"),
	GIFT_CERTIFICATE("payment.fulfillment.gift.certificate"),
	EXCHANGE_PENDING("payment.fulfillment.exchange.pending"),
	EXCHANGE_COMPLETED("payment.fulfillment.exchange.completed"),
	CARD_DESCRIPTION("payment.fulfillment.card");

	private String messageKey;

	private FulfillmentMessages(final String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}

}
