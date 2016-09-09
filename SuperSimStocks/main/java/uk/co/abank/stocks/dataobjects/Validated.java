package uk.co.abank.stocks.dataobjects;

/**
 * Contains a flag to say that variable that states that the Builder class has been validated
 * without this flag objects can't be built
 * Simple validation only for more extensive validation use a bean validator
 * @author JohnM
 *
 */
public abstract class Validated {
	
	private boolean isValidated = false;

	/**
	 * 
	 * @return <code>true</code> if been validated
	 * <code>false</code> if not
	 */
	protected boolean isValidated() {
		return isValidated;
	}

	/**
	 * 
	 * @param isValidated <code>true</code> if been validated
	 * 					  <code>false</code> if not validated
	 */
	protected void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

}
