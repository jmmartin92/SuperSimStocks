package uk.co.abank.stocks.dataobjects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Class that is used to perform the building of a {@link Trade} object 
 * Also performs some validation of the input variables that can be inferred
 * from the spec
 * It assumes tradeTime is supplied from system, so doesn't require validation
 * this could easily be altered, if human input 
 * i.e. convert from string to date object etc and for the other fields if there was a
 * set of validation rules
 * For more complex validation would create separate validation classes
 * or use for example Hibernate bean validator 
 * @author JohnM
 *
 */
public class TradeBuilder extends Validated implements Builder<Trade>, Validator<TradeBuilder> {

	private String stockSymbol;
	private long quantityShares;
	private boolean buyNotSell;
	private LocalDateTime tradeTime;
	private long tradePrice;

	/**
	 * Containing the list of validation errors that can be represented to the user
	 */
	private List<String> validationErrors = new ArrayList<>();
		
	/**
	 * As the stock symbol appears to be unique must validate
	 * to ensure it is populated
	 * @param stockSymbol
	 * @return
	 */
	public TradeBuilder setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
		return this;
	}
	
	/**
	 * Trade price for a trade must be set and must not be equal to zero
	 * @param tradePrice
	 * @return
	 */
	public TradeBuilder setTradePrice(long tradePrice) {
		this.tradePrice = tradePrice;
		return this;
	}

	/**
	 * 
	 * @param tradeTime
	 * @return
	 */
	public TradeBuilder setTradeTime(LocalDateTime tradeTime) {
		this.tradeTime = tradeTime;
		return this;
	}

	/**
	 * Validation for quantity being greater than 0
	 * depending on origin more validation/conveersion from string maybe required
	 * @param quantityShares
	 * @return
	 */
	public TradeBuilder setQuantityShares(long quantityShares) {		
		this.quantityShares = quantityShares;
		return this;
	}

	/**
	 * 
	 * @param buyNotSell
	 * @return
	 */
	public TradeBuilder setBuyNotSell(boolean buyNotSell) {
		this.buyNotSell = buyNotSell;
		return this;
	}
	
	/**
	 * Performs simple validation
	 * 
	 */
	public TradeBuilder validate() {
		validateStockSymbol();
		validateTradePrice();
		validateQuantityShares();
		setValidated(true);
		return this;
	}
	/**
	 * Performs the building of the {@link Trade} object, ensures that if validation
	 * errors have occurred then object doesn't get built
	 */
	public Trade build() {
		if (!isValidated()) throw new IllegalStateException("Trade has not been validated");
		if (validationErrors.size() > 0)
			throw new IllegalArgumentException("Invalid Trade" + validationErrors.toString());
		return new Trade(stockSymbol, quantityShares, buyNotSell, tradeTime, tradePrice);
	}
	
	/**
	 * 
	 * @see uk.co.abank.stocks.dataobjects.Validator#getValidationErrors()
	 */
	@Override
	public List<String> getValidationErrors() {
		return Collections.unmodifiableList(validationErrors);
	}
	
	/**
	 * Validates the stock symbol
	 */
	private void validateStockSymbol() {
		if (stockSymbol == null || stockSymbol.equals(""))
			validationErrors.add("StockSymbol: is empty");
	}
	
	/**
	 * Validates the trade price
	 */
	public void validateTradePrice() {
		if (tradePrice == 0L)
			validationErrors.add("Trade Price cannot be zero");
	}


	/**
	 * Validation for quantity of shares
	 */
	private void validateQuantityShares() {
		if (quantityShares == 0L)
			validationErrors.add("Quantity of shares cannot be zero");
	}
}
