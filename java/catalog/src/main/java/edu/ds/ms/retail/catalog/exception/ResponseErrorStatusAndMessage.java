package edu.ds.ms.retail.catalog.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseErrorStatusAndMessage {
	@JsonProperty("status_code")
	private int code;
	@JsonProperty("status_message")
	private String message;
}
