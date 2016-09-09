package uk.co.abank.stocks.calculators;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import uk.co.abank.stocks.collector.StockPriceStats;
import uk.co.abank.stocks.collector.StockPriceStatsCollector;
import uk.co.abank.stocks.dataobjects.Trade;


/**
 * Class that is used to perform all the calculations of on a set of stocks
 * Calculates the stock price using streams Java8 way
 * and calculates prior to Java 8 as well using iteration
 * @author JohnM
 *
 */
public class StockCalculator {	
	
	// Static used to determine the trade time, enhancement maybe to use a resource file
	// or a as -D option to run it
	public static long tradeTime = 15;
	
	
	/**
	 * Takes in a list of trades and uses streams to perform the filtering
	 * and using a custom collector to perform the sum of price*quantity and quantity
	 * returning them in StockPriceStats 
	 * Assumes all the trades are for one stock only
	 * @param trades the list of stock trades
	 * @return the calculated stock price
	 */
	public BigDecimal calculateStockPriceStreams(List<Trade> trades) {
		
		StockPriceStats stockPriceStats = trades
			.stream()
			.filter(trade -> {
				LocalDateTime beginTrade = LocalDateTime.now().minusMinutes(tradeTime);
				return beginTrade.isBefore(trade.getTradeTime());
			})
			.collect(new StockPriceStatsCollector());
		return calculateStockPrice(stockPriceStats.getQuantity().getSum(), 
				stockPriceStats.getTradePriceQuant().getSum());
	}
	
	/**
	 * Takes a list of trades and uses iteration to perform the filtering and summing
	 * the price*quantity and quantity less efficient than the stream way
	 * Assumes all the trades are for the one stock only
	 * @param trades the list of stock trades
	 * @return the calculated stock price
	 */
	public BigDecimal calculateStockPrice(List<Trade> trades) {
		long sumQuantity = 0L;
		long sumTradePriceMultQuant = 0L;
		
		for (Trade trade: trades) {
			LocalDateTime minus15 = LocalDateTime.now().minusMinutes(tradeTime);
			if (trade.getTradeTime().isAfter(minus15)) {
				sumQuantity +=  trade.getQuantityShares();
				sumTradePriceMultQuant += trade.getTradePrice()* trade.getQuantityShares();	
			}
		}
		return calculateStockPrice(sumQuantity, sumTradePriceMultQuant);
	}
		
		
	/**
	 * Calculates the stock price from the {@link Trade} objects
	 * Checks if the sum quantity is 0 to prevent exception being thrown
	 * this shouldn't happen as the check is done earlier
	 * @param sumQuantity
	 * @param sumTradePriceQuantity
	 * @return
	 */
	private BigDecimal calculateStockPrice(long sumQuantity, long sumTradePriceQuantity) {
		if (sumQuantity == 0)  throw new IllegalArgumentException("Total Traded Quantity is Zero !!");
		
		double stockPrice = (double)sumTradePriceQuantity/sumQuantity;
		return roundTo5DP(stockPrice);
	}
	
	/**
	 * Performs the rounding of the stockPrice to 5dp, depending on its final use
	 * this method may become redundant
	 * @param number
	 * @return
	 */
	private BigDecimal roundTo5DP(double number) {
		return new BigDecimal(Math.round(number * 100000d) / 100000d);
	}
		 
	/**
	 * Calculates the share index from a list of sharePrices, and performs the rounding
	 * depending on final use rounding may not be performed
	 * Uses a stream to perform the calculation, with a reduction multiply each share price together
	 * Checks to ensure the list is not empty to ensure that exception is not generated from the calculation
	 * Not checking for zero share prices as they should get to this point
	 * however they could be removed, using a filter and only valid share prices returned
	 * @param sharePrices list of share prices
	 * @return
	 */
	public BigDecimal calculateAllShareIndex(List<BigDecimal> sharePrices) {
		
		if (sharePrices.isEmpty()) 
			throw new IllegalArgumentException("There are no Share Prices");
		
		Stream<BigDecimal> sharePricesStream = sharePrices.stream();
		
		OptionalDouble shareProduct = sharePricesStream
				.mapToDouble(bg -> bg.doubleValue())
				.reduce((a,b)-> a*b);
		
		if (shareProduct.isPresent()) { 
			return roundTo5DP(Math.pow(shareProduct.getAsDouble(), (double)1/sharePrices.size()));
		} else {
			return new BigDecimal(Double.NaN);
		}
	}
}