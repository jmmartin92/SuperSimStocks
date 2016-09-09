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
 * JUnit test cases to test the DividendYield 
 * @author JohnM
 *
 */
public class TestDividendYield {

	TickerCalculator tickerCalculator;
	
	
	@Before //Setup
	public void before() throws Exception {
		tickerCalculator = new TickerCalculator();
	}

	@After //Teardown
	public void after() throws Exception {
		tickerCalculator = null;
		assertNull(tickerCalculator);
	}

	/**
	 * Tests that the calculation of the DividendYield for zero lastdividend
	 * and common type
	 * Due to using a double need to check for correct roundup as
	 * result is rounded to 5dp, so tolerance is to five dp
	 */
	@Test
	public void testDividendYieldCommonZeroDividend() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("TEA")
				.setStockType("Common")
				.setLastDividend(0)
				.setFixedDividend("")
				.setParValue(100)
				.validate()
				.build();
		
		BigDecimal bigDecimal = tickerCalculator.calculateDividendYield(stock, 33);
		
		assertEquals(bigDecimal.doubleValue(), new Double(0D), 0.00001D);	
	}
	
	/**
	 * Tests the calculation of the DividendYield for nonzero dividend
	 * Due to using a double need to check for correct roundup as
	 * result is rounded to 5dp, so tolerance is to five dp
	 */
	@Test
	public void testDividendYieldCommonNonZeroDividend() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("POP")
				.setStockType("Common")
				.setLastDividend(8)
				.setFixedDividend("")
				.setParValue(100)
				.validate()
				.build();

		BigDecimal bigDecimal = tickerCalculator.calculateDividendYield(stock, 33);
		
		double dividendYield = (double)8/33;
		
		assertEquals(bigDecimal.doubleValue(), new Double(dividendYield), 0.00001D);
	}

	/**
	 * Tests the calculation of the DividendYield for Preferred stocks
	 * Due to using a double need to check for correct roundup as
	 * result is rounded to 5dp, so tolerance is to five dp
	 */
	@Test
	public void testDividendYieldPreferred() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("GIN")
				.setStockType("Preferred")
				.setLastDividend(8)
				.setFixedDividend("2")
				.setParValue(100)
				.validate()
				.build();
		
		double dividendYield = (double)(2.0D/100)*100/33;
		
		BigDecimal bigDecimal = tickerCalculator.calculateDividendYield(stock, 33);
		
		assertEquals(bigDecimal.doubleValue(), new Double(dividendYield), 0.00001D);
	}

	/**
	 * Tests the calculation of the DividendYield for Preferred stocks
	 * with zero fixed dividend
	 * Due to using a double need to check for correct roundup as
	 * result is rounded to 5dp, so tolerance is to five dp
	 */
	@Test
	public void testDividendYieldPreferredZeroFixedDividend() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("GIN")
				.setStockType("Preferred")
				.setLastDividend(8)
				.setFixedDividend("0")
				.setParValue(100)
				.validate()
				.build();
		
		double dividendYield = (double)(0D/100)*100;
		
		BigDecimal bigDecimal = tickerCalculator.calculateDividendYield(stock, 33);
		
		assertEquals(bigDecimal.doubleValue(), new Double(dividendYield), 0.00001D);
	}
	
	/**
	 * Tests the calculation of the DividendYield for Preferred stocks
	 * with zero par value, as might be zero
	 * Due to using a double need to check for correct roundup as
	 * result is rounded to 5dp, so tolerance is to five dp
	 */
	@Test
	public void testDividendYieldPreferredZeroParValue() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("GIN")
				.setStockType("Preferred")
				.setLastDividend(8)
				.setFixedDividend("2")
				.setParValue(0L)
				.validate()
				.build();
		
		double dividendYield = (double)(2.0D/100)*0D/33;
		
		BigDecimal bigDecimal = tickerCalculator.calculateDividendYield(stock, 33);
		
		assertEquals(bigDecimal.doubleValue(), new Double(dividendYield), 0.00001D);
	}

	
	/**
	 * Test that an {@link IllegalArgumentException} is thrown when ticker value is zero
	 * rather than {@link ArithmeticException}
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testDividendYieldTickerZeroException() {
		
		// This should build correctly
		Stock stock = new StockBuilder()
				.setStockSymbol("GIN")
				.setStockType("Preferred")
				.setLastDividend(8)
				.setFixedDividend("2")
				.setParValue(100)
				.validate()
				.build();
	
		BigDecimal bigDecimal = tickerCalculator.calculateDividendYield(stock, 0);	
	}
}