package com.fonyou.api;

import java.time.ZonedDateTime;

import lombok.Value;

/**
 * Value object representing the format of an exception handled by the API.
 * @author Johan Ballesteros.
 * @version 1.0.0.
 */
@Value
public class APIException {

	private ZonedDateTime timestamp = ZonedDateTime.now();
	private String error;
	
}
