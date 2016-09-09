package uk.co.abank.stocks.collector;

import java.util.LongSummaryStatistics;

/**
 * Class used to contain the statistics for the stream collector
 * Contains the stats for the sum of the quantities and the 
 * sum of the trade price* quantity
 * @author JohnM
 *
 */
public class StockPriceStats {
	
	
	private LongSummaryStatistics quantity = new LongSummaryStatistics();
    private LongSummaryStatistics tradePriceQuant = new LongSummaryStatistics();
	
    
    /**
     * 
     * @return the stats associated with the quantity
     */
    public LongSummaryStatistics getQuantity() {
		return quantity;
	}
    
    /**
     * 
     * @param quantity statistics associated with the summation of share quantities
     */
	public void setQuantity(LongSummaryStatistics quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * 
	 * @return the stats associated with the tradeprice*quantity
	 */
	public LongSummaryStatistics getTradePriceQuant() {
		return tradePriceQuant;
	}
	
	/**
	 * 
	 * @param tradePriceQuant the statistic associates with the tradeprice*quantity
	 * of shares
	 */
	public void setTradePriceQuant(LongSummaryStatistics tradePriceQuant) {
		this.tradePriceQuant = tradePriceQuant;
	}
}
