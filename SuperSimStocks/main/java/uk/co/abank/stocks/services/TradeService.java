package uk.co.abank.stocks.services;

import java.util.List;
import uk.co.abank.stocks.dataobjects.Trade;

/**
 * Interface to record a trade and retrieve a list of trades
 * depending on origin this could be implemented for
 * a database or from a set of mock objects
 * @author JohnM
 *
 */
public interface TradeService {

	/**
	 * Used to record a trade, returns <code>true</code> if correctly recorded
	 * returns <code>false</code> if not recorded
	 * @param trade
	 */
	public boolean recordTrade(Trade trade);
	
	/**
	 * Gets a list of trades for a particular stock
	 * @return
	 */
	public List<Trade> getTrades();
}
