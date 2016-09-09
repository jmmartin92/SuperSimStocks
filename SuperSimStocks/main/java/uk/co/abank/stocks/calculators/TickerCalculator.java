package uk.co.abank.stocks.calculators;

import java.math.BigDecimal;

import uk.co.abank.stocks.dataobjects.Stock;

public class TickerCalculator {
	
	/**
	 * Default shouldn't be needed in switch statement but just to make 
	 * @param stock
	 * @return
	 */
	public BigDecimal calculateDividendYield(Stock stock, long tickerPrice) {
		
		if (tickerPrice == 0 ) 
			throw new IllegalArgumentException("Ticker price is zero" + stock.toString());
		
		double dividendByYield = getDividend(stock)/tickerPrice;
		
		
		return roundTo5DP(dividendByYield);
		
	}
	
	/**
	 * 
	 * @param stock
	 * @param tickerPrice
	 * @return
	 */
	private double getDividend(Stock stock) {
	
		double dividend = 0D;
		
		
		switch (stock.getStockType()) {
			case Common:
				dividend = (double)stock.getLastDividend();
				break;
			case Preferred:
				dividend = (double)(stock.getFixedDividend()/100.0D)* stock.getParValue();
				break;
			default:
				throw new IllegalArgumentException("Stock has no stocktype" + stock.toString());
		}
		
		return dividend;
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	private BigDecimal roundTo5DP(double number) {
	
		return new BigDecimal(Math.round(number * 100000d) / 100000d);
	}
	
	
	/**
	 * 
	 * @param stock
	 * @param tickerPrice
	 * @return
	 */
	public BigDecimal calculatePERatio(Stock stock, long tickerPrice) {

		double dividend = getDividend(stock);
		if (Math.round(dividend) == 0)
				throw new IllegalArgumentException("Dividend is zero" + stock.toString());
		
		double pERatio = tickerPrice/dividend;
		
		return roundTo5DP(pERatio);
		
	}	
}
