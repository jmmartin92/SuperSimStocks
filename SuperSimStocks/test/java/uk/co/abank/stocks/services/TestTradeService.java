package uk.co.abank.stocks.services;

import static org.junit.Assert.*;

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
 * JUnit tests for the {@link TradeService} methods
 * @author JohnM
 *
 */
public class TestTradeService {

	TradeBuilder tradeBuilder;
	TradeService tradeService;
	
	@Before
	public void before() throws Exception {
		tradeBuilder = new TradeBuilder();
		tradeService = new TradeServiceMemoryImpl();
	}
	
	@After
	public void after() throws Exception {
		tradeBuilder = null;
		tradeService = null;
		
		assertNull(tradeBuilder);
		assertNull(tradeService);
	}
	

	/**
	 * Tests that the recording of a trade occurs
	 */
	@Test
	public void testRecordTrade() {
		
		Trade trade = tradeBuilder.setStockSymbol("GIN")
					.setTradeTime(LocalDateTime.now())
					.setQuantityShares(8L)
					.setTradePrice(100)
					.setBuyNotSell(true)
					.validate()
					.build();
				
		boolean addedTrade = tradeService.recordTrade(trade);
		
		assertTrue(addedTrade);
	}

	/**
	 * Tests to ensure only one trade is created in the list
	 */
	@Test
	public void testGetTrades() {
		Trade trade = tradeBuilder.setStockSymbol("GIN")
					.setTradeTime(LocalDateTime.now())
					.setQuantityShares(8L)
					.setTradePrice(100)
					.setBuyNotSell(true)
					.validate()
					.build();
				
		boolean addedTrade = tradeService.recordTrade(trade);
		List<Trade> trades;
		
		if (!addedTrade) {
			trades = new ArrayList<Trade>();  
		} else {
			trades = tradeService.getTrades();			
		}
		
		assertNotNull(trades);
		assertEquals(trades.size(),1);
	}

	/**
	 * Tests to ensure that the trade recorded is the same as the one 
	 * retrieved
	 */
	@Test
	public void testGetTradeSame() {
		Trade trade = tradeBuilder.setStockSymbol("GIN")
					.setTradeTime(LocalDateTime.now())
					.setQuantityShares(8L)
					.setTradePrice(100)
					.setBuyNotSell(true)
					.validate()
					.build();
				
		boolean addedTrade = tradeService.recordTrade(trade);
		List<Trade> trades;
		
		if (!addedTrade) {
			trades = new ArrayList<Trade>();  
		} else {
			trades = tradeService.getTrades();			
		}
		
		assertNotNull(trades);
		
		Trade tradeRecorded = trades.get(0);
		assertEquals(trade, tradeRecorded);
	}
}