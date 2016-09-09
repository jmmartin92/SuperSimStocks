package uk.co.abank.stocks.collector;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import uk.co.abank.stocks.dataobjects.Trade;

/**
 * Custom built collector to take a stream of trades 
 * and work out the sum of the quantity of trades, and the
 * sum of the trades price * quantity
 * Type of elements for reduction are Trade
 * Type of elements for the accumulation are StockPriceStats
 * Type of elements for the result of the reduction are StockPriceStats 
 * @author JohnM
 *
 */
public class StockPriceStatsCollector implements Collector<Trade, StockPriceStats, StockPriceStats>{


	/**
	 * Performs the combining of the stocks and trade into the 
	 * stockPriceStats for the total shares and the tradePrice*Quantity
	 */
	@Override
	public BiConsumer<StockPriceStats, Trade> accumulator() {
		return (stockPriceStats, trade) -> {
			stockPriceStats.getQuantity().accept(trade.getQuantityShares());
			stockPriceStats.getTradePriceQuant().accept(trade.getQuantityShares()*trade.getTradePrice());			
		};
	}

	/**
	 * The final function being identity function, so can be ommitted
	 */
	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		return Collections.singleton(Characteristics.IDENTITY_FINISH);
	}
	
	/**
	 * Performs the combining of two partial results folds one into
	 * the other other and returns this 
	 */
	@Override
	public BinaryOperator<StockPriceStats> combiner() {
		return (stockPriceStats, otherSPS) -> {
			stockPriceStats.getQuantity().combine(otherSPS.getQuantity());
			stockPriceStats.getTradePriceQuant().combine(otherSPS.getTradePriceQuant());
			return stockPriceStats;
		};
	}

	/**
	 * Perform the final transformation, as accumulation and final result 
	 * are of the same type just maps from one form to the other
	 * i.e. no final transformation is required
	 * Could also be returned as null
	 */
	@Override
	public Function<StockPriceStats, StockPriceStats> finisher() {
		return (stockPriceStats -> stockPriceStats);
	}

	/**
	 * Provides the result container
	 */
	@Override
	public Supplier<StockPriceStats> supplier() {
		return () -> new StockPriceStats();
	}

}