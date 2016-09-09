package uk.co.abank.stocks.dataobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that is used to perform the building of a stock object 
 * Also performs some validation of the input variables that can be inferred
 * from the spec
 * The data might come from a db table, with correct types, negating 
 * some of the validation required, or maybe all VARCHAR's so requiring more 
 * validation
 * For more complex validation would create separate validation classes
 * or use for example Hibernate bean validator 
 * @author JohnM
 *
 */
public class StockBuilder extends Validated implements Builder<Stock>, Validator<StockBuilder> {

	private String stockSymbol;
	private String stockTypeStr;
	private StockType stockType; 
	private long lastDividend;
	private String fixedDividendStr;
	private long fixedDividend;
	private long parValue;
	
	/**
	 * Used to contain any validation errors
	 */
	private List<String> validationErrors = new ArrayList<>();
			
	
	/**
	 * Tests for empty/null stock symbol as this is unique for the data
	 * @param stockSymbol
	 * @return
	 */
	public StockBuilder setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
		return this;
	}
	
	/**
	 * Use of enum as from stock type appears to be either Common or
	 * Preferred. Might not be appropriate in real world, as these might
	 * come from a DB table or resource file
	 * @param stockType
	 * @return this

	 */
	public StockBuilder setStockType(String stockTypeStr) {
		this.stockTypeStr = stockTypeStr;
		return this;
	}
	
	/**
	 * 
	 * @param lastDividend
	 * @return
	 */
	public StockBuilder setLastDividend(long lastDividend) {
		this.lastDividend = lastDividend;
		return this;
	}
	
	/**
	 * Checks the value of dividend, data seems to indicate it might be a string,
	 * if so it might require extra check for the % and substring'ing
	 * Catches NumberFormatException if invalid number and adds error 
	 * into validation errors 
	 * @param fixedDividend
	 * @return
	 */
	public StockBuilder setFixedDividend(String fixedDividendStr) {
		this.fixedDividendStr = fixedDividendStr;
		return this;
	}

	/**
	 * 
	 * @param parValue
	 * @return
	 */
	public StockBuilder setParValue(long parValue) {
		this.parValue = parValue;
		return this;
	}
	
	/**
	 * @return list of validation errors, unmodifiable to ensure it can't be changed
	 * @see uk.co.abank.stocks.dataobjects.Validator#getValidationErrors()
	 */
	@Override
	public List<String> getValidationErrors() {
		return Collections.unmodifiableList(validationErrors);
	}
	

	/**
	 * Performs the validation of clunky if knew of origin of data then construct data
	 * validation classes
	 * @see uk.co.abank.stocks.dataobjects.Builder#validate()
	 */
	@Override
	public StockBuilder validate() {
		validateStockSymbol();
		validateStockType();
		validateFixedDividend();
		setValidated(true);
		return this;
	}
	/**
	 * Performs the build of the stock if no validation errors are found
	 * otherwise exception thrown, depending on the use this could be changed to a
	 * CheckedException
	 * @return stock
	 * @see uk.co.abank.stocks.dataobjects.Builder#build()
	 */
	@Override
	public Stock build() {
		if (!isValidated()) throw new IllegalStateException("Stock has not been validated");
		if (validationErrors.size() > 0)
			throw new IllegalArgumentException("Invalid stock" + validationErrors.toString());
		return new Stock(stockSymbol,stockType,lastDividend,fixedDividend, parValue);
	}
	
	/**
	 * Used to validate the stock Symbol
	 */
	private void validateStockSymbol() {
		if (stockSymbol == null || stockSymbol.equals(""))
			validationErrors.add("StockSymbol: is empty");
	}
	
	/**
	 * Used to validate the stock type
	 */
	private void validateStockType() {
		switch (stockTypeStr) {
		case "Common":
			stockType = StockType.Common;
			break;
		case "Preferred":
			stockType = StockType.Preferred;
			break;
		default:
			validationErrors.add("StockType is not correctly defined");
		}
	}
	
	/**
	 * Used to validate the fixed dividend
	 */
	private void validateFixedDividend() {
		
		if (fixedDividendStr == null || fixedDividendStr.equals("")) {
			fixedDividend = 0;
		}
		else {
			try{
				fixedDividend = Long.parseLong(fixedDividendStr);
			} catch (NumberFormatException nfe){
				validationErrors.add("Fixed divided" + fixedDividend + "is not a number");
			}
		}	
	}
}