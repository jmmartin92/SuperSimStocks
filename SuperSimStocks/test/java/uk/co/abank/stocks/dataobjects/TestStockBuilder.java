package uk.co.abank.stocks.dataobjects;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.abank.stocks.dataobjects.Stock;
import uk.co.abank.stocks.dataobjects.StockBuilder;
import uk.co.abank.stocks.dataobjects.StockType;

/**
 * JUnit tests for {@link StockBuilder}
 * @author JohnM
 *
 */
public class TestStockBuilder {

	StockBuilder stockBuilder;

	@Before
	public void before() throws Exception {
		stockBuilder = new StockBuilder();
	}
	
	@After
	public void after() throws Exception {
		stockBuilder = null;
		assertNull(stockBuilder);
	}
	
	
	/**
	 * Test the validation of the stock Symbol
	 */
	@Test
	public void testValidationStockSymbol() {
		stockBuilder.setStockSymbol("")
			.setStockType("Common")
			.setLastDividend(8)
			.setFixedDividend("3")
			.setParValue(100)
			.validate();
			
		assertEquals(1,stockBuilder.getValidationErrors().size());
	}	
	
	/**
	 * Test the validation of the complete stock
	 */
	@Test (expected = IllegalStateException.class)
	public void testValidationSetting() {
		stockBuilder.setStockSymbol("")
			.setStockType("Common")
			.setLastDividend(8)
			.setFixedDividend("3")
			.setParValue(100)
			.build();			
	}
	
	
	/**
	 * Test the validation of the stocktype
	 */
	@Test
	public void testValidationStockType() {
		stockBuilder.setStockSymbol("POP")
			.setStockType("")
			.setLastDividend(8)
			.setFixedDividend("3")
			.setParValue(100)
			.validate();
		
		assertEquals(1,stockBuilder.getValidationErrors().size());
	}
	
	/**
	 * Test the validation of the fixed dividend if not number
	 * replace with 0
	 */
	@Test
	public void testValidationFixedDividend() {
		stockBuilder.setStockSymbol("POP")
			.setStockType("Common")
			.setLastDividend(8)
			.setFixedDividend("Test")
			.setParValue(100)
			.validate();

		assertEquals(1,stockBuilder.getValidationErrors().size());
	}
		
	/**
	 * At the moment only concerned about the errors that will be produced as
	 * the stock cannot be built anyway, could test each individual message
	 */	
	@Test
	public void testValidationErrors() {
		stockBuilder.setStockSymbol("")
					.setStockType("")
					.setLastDividend(0)
					.setFixedDividend("Test")
					.setParValue(0)
					.validate();
		assertEquals(3,stockBuilder.getValidationErrors().size());
	}
	
	/**
	 * Tests for the throwing of {@link IllegalArgumentException}
	 * when validation errors are present
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testIllegalArgumentException() {
		stockBuilder.setStockSymbol("")
					.setStockType("")
					.setLastDividend(0)
					.setFixedDividend("")
					.setParValue(0)
					.validate();
		stockBuilder.build();
	}
	
	/**
	 * Tests that the enumeration for StockType.Common
	 * is correctly populated
	 */
	@Test
	public void testCommonStockType() {
		stockBuilder.setStockSymbol("POP")
					.setStockType("Common")
					.setLastDividend(8)
					.setFixedDividend("")
					.setParValue(100)
					.validate();
		
		Stock stock = stockBuilder.build();
		assertEquals(StockType.Common, stock.getStockType());
	}

	/**
	 * Tests that the StockType.Preferred is correctly
	 * populated
	 */
	@Test
	public void testPreferredStockType() {
		stockBuilder.setStockSymbol("POP")
					.setStockType("Preferred")
					.setLastDividend(8)
					.setFixedDividend("")
					.setParValue(100)
					.validate();
		Stock stock = stockBuilder.build();
		assertEquals(StockType.Preferred, stock.getStockType());
	}
	
	/**
	 * Tests that the stock is built correctly when the correct parameters
	 * are used i.e. no exceptions, no validation errors
	 */
	@Test
	public void testCorrectBuild() {
		Stock stock = stockBuilder.setStockSymbol("GIN")
					.setStockType("Preferred")
					.setLastDividend(8)
					.setFixedDividend("2")
					.setParValue(100)
					.validate()
					.build();
		assertNotNull(stock);
		assertEquals(0,stockBuilder.getValidationErrors().size());		
	}
}