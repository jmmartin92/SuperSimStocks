package uk.co.abank.stocks.dataobjects;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.abank.stocks.dataobjects.Stock;
import uk.co.abank.stocks.dataobjects.TradeBuilder;
import uk.co.abank.stocks.dataobjects.StockType;

/**
 * JUint tests for the {@link TradeBuilder}
 * @author JohnM
 *
 */
public class TestTradeBuilder {

	TradeBuilder tradeBuilder;
	
	@Before
	public void before() throws Exception {
		tradeBuilder = new TradeBuilder();
	}
	
	@After
	public void after() throws Exception {
		tradeBuilder = null;
		assertNull(tradeBuilder);
	}
	
	/**
	 * Test the validation of the complete trade
	 */
	@Test (expected = IllegalStateException.class)
	public void testValidationSetting() {
		tradeBuilder.setStockSymbol("GIN")
			.setTradeTime(LocalDateTime.now())
			.setQuantityShares(8L)
			.setTradePrice(100)
			.setBuyNotSell(true)
		.build();
	}

	
	
	/**
	 * Test the validation of the stock symbol
	 */
	@Test
	public void testValidationStockSymbol() {
		tradeBuilder.setStockSymbol("")
			.setTradeTime(LocalDateTime.now())
			.setQuantityShares(8L)
			.setTradePrice(100)
			.setBuyNotSell(true)
			.validate();
		assertEquals(1,tradeBuilder.getValidationErrors().size());
	}	
	
	/**
	 * Test the correct build for the Stocks
	 */
	@Test
	public void testCorrectBuild() {
		
		Trade trade = tradeBuilder.setStockSymbol("GIN")
					.setTradeTime(LocalDateTime.now())
					.setQuantityShares(8L)
					.setTradePrice(100)
					.setBuyNotSell(true)
					.validate()
					.build();
		
		assertNotNull(trade);
		assertEquals(0, tradeBuilder.getValidationErrors().size());		
	}

		
	/**
	 * Tests to ensure with an invalid stock symbol
	 * 
	 */
	@Test
	public void testIncorrectBuildStockSymbol() {
		tradeBuilder.setStockSymbol("")
					.setTradeTime(LocalDateTime.now())
					.setQuantityShares(8)
					.setTradePrice(100)
					.setBuyNotSell(true)
					.validate();
		
		assertEquals(1, tradeBuilder.getValidationErrors().size());
	}
	
	/**
	 * Test to ensure with an invalid quantity of shares
	 */
	@Test
	public void testInvalidQuantityShares() {
		TradeBuilder tradeBuilder = new TradeBuilder();
		tradeBuilder.setStockSymbol("TEA")
					.setTradeTime(LocalDateTime.now())
					.setQuantityShares(0)
					.setTradePrice(100)
					.setBuyNotSell(true)
					.validate();
		
	}
	
	/**
	 * Test to ensure with an invalid trade price of shares
	 */
	@Test
	public void testInvalidBuildTradePrice() {
		TradeBuilder tradeBuilder = new TradeBuilder();
		tradeBuilder.setStockSymbol("TEA")
					.setTradeTime(LocalDateTime.now())
					.setQuantityShares(10)
					.setTradePrice(0)
					.setBuyNotSell(true)
					.validate();
		
		assertEquals(1,tradeBuilder.getValidationErrors().size());
	}

	/**
	 * Test to ensure with an invalid trade, when
	 * build {@link IllegalArgumentException} is thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidBuild() {
		TradeBuilder tradeBuilder = new TradeBuilder();
		tradeBuilder.setStockSymbol("")
					.setTradeTime(LocalDateTime.now())
					.setQuantityShares(0)
					.setTradePrice(0)
					.setBuyNotSell(true)
					.validate()
					.build();
		
	}

}
