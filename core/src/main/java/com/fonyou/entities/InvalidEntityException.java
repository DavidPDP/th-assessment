package com.fonyou.entities;

/**
 * Custom exception for cases where an entity does not have a valid state.
 * @author Johan Ballesteros.
 * @version 1.0.0.
 */
public class InvalidEntityException extends Throwable {

	private static final long serialVersionUID = -4806163115622526288L;

	public InvalidEntityException(String message) {
        super(message);
    }
	
}
