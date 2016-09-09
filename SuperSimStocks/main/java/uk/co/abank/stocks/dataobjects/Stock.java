package uk.co.abank.stocks.dataobjects;

/**
 * Class used to represent the Global Beverage Corporation Exchange data
 * final class so that it can't be modified once created
 * Uses {@link StockBuilder} to build them
 * @author JohnM
 *
 */
public final class Stock {
	
	private final String stockSymbol;
	private final StockType stockType;
	private final long lastDividend;
	private final long fixedDividend;
	private final long parValue;
	
	/**
	 * Constructor 
	 * @param stockSymbol 
	 * @param stockType enumeration representing the type of stock either StockType.Common or StockType.Preferred
	 * @param lastDividend
	 * @param fixedDividend
	 * @param parValue
	 */
	public Stock(String stockSymbol, StockType stockType, 
			long lastDividend, long fixedDividend, long parValue) {
		this.stockSymbol = stockSymbol;
		this.stockType = stockType;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}
	
	/**
	 * 
	 * @return the stock symbol
	 */
	public String getStockSymbol() {
		return stockSymbol;
	}

	/**
	 * 
	 * @return the stock type
	 */
	public StockType getStockType() {
		return stockType;
	}
	
	/**
	 * 
	 * @return the last dividend
	 */
	public long getLastDividend() {
		return lastDividend;
	}
	
	/**
	 * 
	 * @return the fixed dividend
	 */
	public long getFixedDividend() {
		return fixedDividend;
	}
	
	/**
	 * 
	 * @return the par value
	 */
	public long getParValue() {
		return parValue;
	}

	/**
	 * Used for logging in case of exception being produced
	 */
	@Override
	public String toString() {
		return "Stock [stockSymbol=" + stockSymbol + ", stockType=" + stockType + ", lastDividend=" + lastDividend
				+ ", fixedDividend=" + fixedDividend + ", parValue=" + parValue + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (fixedDividend ^ (fixedDividend >>> 32));
		result = prime * result + (int) (lastDividend ^ (lastDividend >>> 32));
		result = prime * result + (int) (parValue ^ (parValue >>> 32));
		result = prime * result + ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result + ((stockType == null) ? 0 : stockType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (fixedDividend != other.fixedDividend)
			return false;
		if (lastDividend != other.lastDividend)
			return false;
		if (parValue != other.parValue)
			return false;
		if (stockSymbol == null) {
			if (other.stockSymbol != null)
				return false;
		} else if (!stockSymbol.equals(other.stockSymbol))
			return false;
		if (stockType != other.stockType)
			return false;
		return true;
	}
}