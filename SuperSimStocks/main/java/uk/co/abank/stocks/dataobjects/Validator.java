package uk.co.abank.stocks.dataobjects;

import java.util.List;

/**
 * Interface to define the behaviour of a builder object
 * to ensure validation is performed
 * @author JohnM
 *
 */
public interface Validator<T> {
	
	 
	/**
	 * 
	 * @return a list of validation errors
	 */
	 List<String> getValidationErrors();

	 /**
	  * Forces the implementation to perform some validation
	  */
	 T validate();
}
