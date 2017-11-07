package YahooFinance;

public class StockInfo {
	public String stockName;
	public String stockID;
	public double price;
	public String currency;
	public String companyName;
	
	public StockInfo() {
	}
	
	public StockInfo(String stockName, String stockID, double price, String currency, String companyName) {
		super();
		this.stockName = stockName;
		this.stockID = stockID;
		this.price = price;
	}
	
	public String getStockName() {
		return stockName;
	}
	
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	
	public String getStockID() {
		return stockID;
	}
	
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void print() {
		System.out.println(stockName+" | "+stockID+": "+price);
	}
}
