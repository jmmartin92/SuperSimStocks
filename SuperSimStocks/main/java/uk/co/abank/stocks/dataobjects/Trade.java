package uk.co.abank.stocks.dataobjects;

import java.time.LocalDateTime;

/**
 * Class used to represent a Trade
 * final class so that it can't be modified once created
 * Uses {@link TradeBuilder} to build it
 * @author JohnM
 *
 */
public final class Trade {
	
	private final String stockSymbol;
	private final long quantityShares;
	private final boolean buyNotSell;
	private final LocalDateTime tradeTime;
	private final long tradePrice;
	
	/**
	 * Constructor
	 * @param stockSymbol
	 * @param quantityShares
	 * @param buyNotSell
	 * @param tradeTime
	 * @param tradePrice
	 */
	public Trade(String stockSymbol, long quantityShares, boolean buyNotSell, LocalDateTime tradeTime,
			long tradePrice) {
		super();
		this.stockSymbol = stockSymbol;
		this.quantityShares = quantityShares;
		this.buyNotSell = buyNotSell;
		this.tradeTime = tradeTime;
		this.tradePrice = tradePrice;
	}
	
	/**
	 * @return
	 */
	public String getStockSymbol() {
		return stockSymbol;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getTradePrice() {
		return tradePrice;
	}
	
	/**
	 * 
	 * @return
	 */
	public LocalDateTime getTradeTime() {
		return tradeTime;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getQuantityShares() {
		return quantityShares;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isBuyNotSell() {
		return buyNotSell;
	}

	@Override
	public String toString() {
		return "Trade [stockSymbol=" + stockSymbol + ", quantityShares=" + quantityShares + ", buyNotSell=" + buyNotSell
				+ ", tradeTime=" + tradeTime + ", tradePrice=" + tradePrice + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (buyNotSell ? 1231 : 1237);
		result = prime * result + (int) (quantityShares ^ (quantityShares >>> 32));
		result = prime * result + ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result + (int) (tradePrice ^ (tradePrice >>> 32));
		result = prime * result + ((tradeTime == null) ? 0 : tradeTime.hashCode());
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
		Trade other = (Trade) obj;
		if (buyNotSell != other.buyNotSell)
			return false;
		if (quantityShares != other.quantityShares)
			return false;
		if (stockSymbol == null) {
			if (other.stockSymbol != null)
				return false;
		} else if (!stockSymbol.equals(other.stockSymbol))
			return false;
		if (tradePrice != other.tradePrice)
			return false;
		if (tradeTime == null) {
			if (other.tradeTime != null)
				return false;
		} else if (!tradeTime.equals(other.tradeTime))
			return false;
		return true;
	}
}