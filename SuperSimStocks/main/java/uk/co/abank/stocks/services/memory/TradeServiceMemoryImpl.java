package uk.co.abank.stocks.services.memory;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import uk.co.abank.stocks.dataobjects.Trade;
import uk.co.abank.stocks.services.TradeService;

/**
 * Used to record a trade into a Stack and return a List of trades
 * As the data is to be held in memory the trades are stored in this class
 * @author JohnM
 *
 */
public class TradeServiceMemoryImpl implements TradeService {

	private List<Trade> trades = new Stack<>();
	
	/**
	 * Records a trade
	 * @see {@link TradeService}
	 */
	@Override
	public boolean recordTrade(Trade trade) {
		return trades.add(trade);
	}

	/**
	 * To ensure that the data cannot be altered returns and unmodifiable list
	 * @return 
	 */
	public List<Trade> getTrades() {
		return Collections.unmodifiableList(trades);
	}
}
