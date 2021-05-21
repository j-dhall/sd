package io.educative.mediaapp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseStatusError {
	/*
	//NOTE NOTE NOTE: lombak @AllArgsConstructor should have generated this, but it didn't. 
	public ResponseStatusError(int code, String message) {
		this.code = code;
		this.message = message;
	}
	*/
	@JsonProperty("status_code")
	private int code;
	@JsonProperty("status_message")
	private String message;
}
