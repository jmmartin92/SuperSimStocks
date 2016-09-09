package uk.co.abank.stocks.calculators;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.abank.stocks.calculators.TickerCalculator;
import uk.co.abank.stocks.dataobjects.Stock;
import uk.co.abank.stocks.dataobjects.StockBuilder;

/**
 * Junit test cases for testing the P/E Ratio calculation 
 * @author JohnM
 *
 */
public class TestPERatio {

	TickerCalculator tickerCalculator;
	
	@Before
	public void before() throws Exception {
		tickerCalculator = new TickerCalculator();
	}
	
	@After //Teardown
	public void after() throws Exception {
		tickerCalculator = null;
		assertNull(tickerCalculator);
	}


	/**
	 * Tests that the calculation of the P/E Ratio for zero ticker price
	 * and common type
	 * Due to using a double need to check for correct roundup as
	 * result is rounded to 5dp, so tolerance is to five dp
	 */
	@Test
	public void testPERatioZeroTickerPrice() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("POP")
				.setStockType("Common")
				.setLastDividend(8)
				.setFixedDividend("")
				.setParValue(100)
				.validate()
				.build();
		
		BigDecimal bigDecimal = tickerCalculator.calculatePERatio(stock, 0);
		
		assertEquals(bigDecimal.doubleValue(), new Double(0D), 0.00001D);
	}
	
	/**
	 * Tests that the calculation of the P/E Ratio for Common stocks
	 * nonzero ticker price
	 * Due to using a double need to check for correct roundup as
	 * result is rounded to 5dp, so tolerance is to five dp
	 */
	@Test
	public void testPERatioCommonNonZeroTickerPrice() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("POP")
				.setStockType("Common")
				.setLastDividend(8)
				.setFixedDividend("")
				.setParValue(100)
				.validate()
				.build();
		
		BigDecimal bigDecimal = tickerCalculator.calculatePERatio(stock, 33);
		
		double pERatio = (double)33/8;
		
		assertEquals(bigDecimal.doubleValue(), new Double(pERatio), 0.00001D);
	}

	/**
	 * Tests that the calculation of the P/E Ratio and Preferred stocks
	 * with non zero ticker price
	 * Due to using a double need to check for correct roundup as
	 * result is rounded to 5dp, so tolerance is to five dp
	 */
	@Test
	public void testPERationPreferred() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("GIN")
				.setStockType("Preferred")
				.setLastDividend(8)
				.setFixedDividend("2")
				.setParValue(100)
				.validate()
				.build();
		
		double pERatio = (double)33/((2D/100D)*100);
		BigDecimal bigDecimal = tickerCalculator.calculatePERatio(stock, 33);
		
		assertEquals(bigDecimal.doubleValue(), new Double(pERatio), 0.00001D);
	}
	
	/**
	 * Tests that the calculation of the P/E Ratio for common type
	 * and last dividend zero, throws an {@link IllegalArgumentException}
	 * 
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testPERatioLastDividendZeroException() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("GIN")
				.setStockType("Common")
				.setLastDividend(0)
				.setFixedDividend("2")
				.setParValue(100)
				.validate()
				.build();
		
		BigDecimal bigDecimal = tickerCalculator.calculatePERatio(stock, 33);		
	}

	/**
	 * Tests that the calculation of the P/E Ratio for preferred type
	 * and fixed dividend zero, throws an {@link IllegalArgumentException}
	 * instead of {@link ArithmeticException} due to divide by 0
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testPERatioFixedDividedZeroException() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("GIN")
				.setStockType("Preferred")
				.setLastDividend(8)
				.setFixedDividend("")
				.setParValue(100)
				.validate()
				.build();
		
		BigDecimal bigDecimal = tickerCalculator.calculatePERatio(stock, 33);
	}

	/**
	 * Tests that the calculation of the P/E Ratio for preferred type
	 * and ParValue zero, throws an {@link IllegalArgumentException}
	 * instead of {@link ArithmeticException} due to divide by 0
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testPERatioParValueZeroException() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("GIN")
				.setStockType("Preferred")
				.setLastDividend(8)
				.setFixedDividend("2")
				.setParValue(0)
				.validate()
				.build();
		
		BigDecimal bigDecimal = tickerCalculator.calculatePERatio(stock, 33);
	}
}