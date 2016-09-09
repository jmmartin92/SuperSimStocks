package uk.co.abank.stocks.calculators;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.abank.stocks.dataobjects.Trade;
import uk.co.abank.stocks.dataobjects.TradeBuilder;
import uk.co.abank.stocks.services.TradeService;
import uk.co.abank.stocks.services.memory.TradeServiceMemoryImpl;

/**
 * Tests the calculation of the stock price
 * using both the stream way and earlier iteration way
 * @author JohnM
 *
 */
public class TestStockPriceCalculator {

	TradeService tradeService;
	StockCalculator stockCalculator;
	
	@Before
	public void before() throws Exception{
		tradeService = new TradeServiceMemoryImpl();
		stockCalculator = new StockCalculator();
	}
	
	@After
	public void after() throws Exception{
		tradeService = null;
		stockCalculator = null;
		assertNull(tradeService);
		assertNull(stockCalculator);
	}
	
	/**
	 * Creates a number of trades for the same stock, records them (this is already tested)
	 * then uses these to test for the correct stock price.
	 * Uses the iterative calculation
	 */
	@Test
	public void testCorrectQuantityandPrice() {
		
		tradeService.recordTrade(new TradeBuilder()
				.setStockSymbol("GIN")
				.setTradeTime(LocalDateTime.now())
				.setQuantityShares(8L)
				.setTradePrice(100)
				.setBuyNotSell(true).validate().build());
		tradeService.recordTrade(new TradeBuilder()
				.setStockSymbol("GIN")
				.setTradeTime(LocalDateTime.now())
				.setQuantityShares(24L)
				.setTradePrice(105)
				.setBuyNotSell(true).validate().build());
		tradeService.recordTrade(new TradeBuilder()
				.setStockSymbol("GIN")
				.setTradeTime(LocalDateTime.now().minusMinutes(10))
				.setQuantityShares(30L)
				.setTradePrice(102)
				.setBuyNotSell(true).validate().build());
		tradeService.recordTrade(new TradeBuilder()
				.setStockSymbol("GIN")
				.setTradeTime(LocalDateTime.now().minusMinutes(15))
				.setQuantityShares(30L)
				.setTradePrice(55)
				.setBuyNotSell(true).validate().build());
		
		List<Trade> trades = tradeService.getTrades();
		
		
		BigDecimal stockPriceCalc = stockCalculator.calculateStockPrice(trades);
		double stockPriceActual = ((double)(8*100) + (24*105) + (30*102))/ (8+24+30);
		
		assertEquals(stockPriceCalc.doubleValue(), new Double(stockPriceActual), 0.00001D);
	}

	/**
	 * Creates a number of trades for the same stock, records them (this is already tested)
	 * then uses these to test for the correct stock price
	 * Uses the stream implementation
	 */	
	@Test
	public void testCorrectQuantityandPriceStream() {
		TradeService tradeService = new TradeServiceMemoryImpl();
		
		
		tradeService.recordTrade(new TradeBuilder()
				.setStockSymbol("GIN")
				.setTradeTime(LocalDateTime.now())
				.setQuantityShares(8L)
				.setTradePrice(100)
				.setBuyNotSell(true).validate().build());
		tradeService.recordTrade(new TradeBuilder()
				.setStockSymbol("GIN")
				.setTradeTime(LocalDateTime.now())
				.setQuantityShares(24L)
				.setTradePrice(105)
				.setBuyNotSell(true).validate().build());
		tradeService.recordTrade(new TradeBuilder()
				.setStockSymbol("GIN")
				.setTradeTime(LocalDateTime.now().minusMinutes(10))
				.setQuantityShares(30L)
				.setTradePrice(102)
				.setBuyNotSell(true).validate().build());
		tradeService.recordTrade(new TradeBuilder()
				.setStockSymbol("GIN")
				.setTradeTime(LocalDateTime.now().minusMinutes(15))
				.setQuantityShares(30L)
				.setTradePrice(55)
				.setBuyNotSell(true).validate().build());
		
		List<Trade> trades = tradeService.getTrades();
		
		BigDecimal stockPriceCalc = stockCalculator.calculateStockPriceStreams(trades);
		double stockPriceActual = ((double)(8*100) + (24*105) + (30*102))/ (8+24+30);
		
		assertEquals(stockPriceCalc.doubleValue(), new Double(stockPriceActual), 0.00001D);
	}


	/**
	 * Creates a number of stock prices calculates the SharePriceIndex
	 * then uses these to test for the correct stock price
	 */	
	@Test
	public void testAllShareIndex() {
		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal(120D));
		list.add(new BigDecimal(120D));
		list.add(new BigDecimal(120D));
		
		StockCalculator stockCalculator = new StockCalculator();
		BigDecimal stockIndex = stockCalculator.calculateAllShareIndex(list);
	
		double stockPriceExp = Math.pow(120D*120D*120D, (double)1/3);
		
		assertEquals(stockIndex.doubleValue(), new Double(stockPriceExp), 0.00001D);
	}
	
	/**
	 * Creates a number of stock prices calculates the SharePriceIndex
	 * then uses these to test for the correct stock price
	 */	
	@Test
	public void testAllShareIndex2() {
		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal(120.67D));
		list.add(new BigDecimal(168.53D));
		list.add(new BigDecimal(356.88D));
		list.add(new BigDecimal(520.45D));
		list.add(new BigDecimal(734.53D));
		list.add(new BigDecimal(891.88D));
		
		BigDecimal stockIndex = stockCalculator.calculateAllShareIndex(list);
	
		double stockPriceExp = Math.pow(120.67D*168.53D*356.88D*520.45D*734.53D*891.88D, (double)1/6);
		
		assertEquals(stockIndex.doubleValue(), new Double(stockPriceExp), 0.00001D);
	}

	/**
	 * Creates a emptylist of stock prices and checks that an {@link IllegalArgumentException}
	 * is thrown
	 * 
	 */	
	@Test (expected = IllegalArgumentException.class)
	public void testNoStocks() {
		List<BigDecimal> list = new ArrayList<>();
		
		BigDecimal stockIndex = stockCalculator.calculateAllShareIndex(list);
	}
}
