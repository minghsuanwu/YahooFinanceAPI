package YahooFinance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import StockIDCrawler.StockIDCrawler;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

public class YahooFinanceAPI {

	public void singleStock(String stockID, String stockName) {	
		/*
		 * intel: INTC
		 * 台積電: 2330.TW
		 * 大立光: 3008.TW
		 * google: GOOG
		 */
//		String stockID = "2357.TW";
		BigDecimal price = null;
		BigDecimal change = null;
		BigDecimal peg = null;
		BigDecimal dividend = null;
		try {
			Stock stock = YahooFinance.get(stockID);
			
			try {price = stock.getQuote().getPrice();} catch (Exception e) {}
			try {change = stock.getQuote().getChangeInPercent();} catch (Exception e) {}
			try {peg = stock.getStats().getPeg();} catch (Exception e) {}
			try {dividend = stock.getDividend().getAnnualYieldPercent();} catch (Exception e) {}
		 
			System.out.println(stock.getName()+" | "+stock.getSymbol()+": "+price+" "+stock.getCurrency());
//			stock.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Stock> multipleStock(String[] symbols) {
		Map<String, Stock> stockMap = null;
		try {
//			symbols = new String[] {"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};
			stockMap = YahooFinance.get(symbols); // single request
//			Stock intel = stocks.get("INTC");
//			intel.print();
//			Stock airbus = stocks.get("AIR.PA");
//			airbus.print();
//			Stock yahoo = stocks.get("YHOO");
//			yahoo.print();
			BigDecimal price = null;
			BigDecimal change = null;
			BigDecimal peg = null;
			BigDecimal dividend = null;
			for (Stock stock: stockMap.values()) {
				try {price = stock.getQuote().getPrice();} catch (Exception e) {}
				try {change = stock.getQuote().getChangeInPercent();} catch (Exception e) {}
				try {peg = stock.getStats().getPeg();} catch (Exception e) {}
				try {dividend = stock.getDividend().getAnnualYieldPercent();} catch (Exception e) {}
			 
				System.out.println(stock.getName()+" | "+stock.getSymbol()+": "+price+" "+stock.getCurrency());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stockMap;
	}
	
	/**
	 * Check the list of currency symbol in class FxSymbols
	 * @param FxSymbols
	 */
	public void FXQuotesByFxSymbol(String FxSymbol) {
		try {
			FxQuote usdeur = YahooFinance.getFx(FxSymbol);
			System.out.println(usdeur);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Query currency exchange rate by giving two currency symbol
	 * Check currency symbol from http://www.xe.com/symbols.php
	 * @param currencyFrom
	 * @param currencyTo
	 */
	public void FXQuotes(String currencyFrom, String currencyTo) {
		try {			
			FxQuote usdgbp = YahooFinance.getFx(currencyFrom.toUpperCase()+currencyTo.toUpperCase()+"=X");
			System.out.println(usdgbp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void FXQuotes(String symbol) {
		try {			
			FxQuote usdgbp = YahooFinance.getFx(symbol);
			System.out.println(usdgbp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Yahoo shut down service 
	 * @return
	 */
	@Deprecated
	public String callYahooFinanceAPI() {
		StringBuilder sb = new StringBuilder();
		try {
			int stockIDLimit = 1;
			for (int i = 0; i < stockIDLimit; i++) {
				/*
				 * 大立光: 3008.TW
				 */
				String stockID = "2357.TW";
				String queryTime = "1y";
				String returnFormat = "json";
				String urlString = "http://chartapi.finance.yahoo.com/instrument/1.0/"+stockID+"/chartdata;type=quote;range="+queryTime+"/"+returnFormat;
				URL yahooFinance = new URL(urlString);
				URLConnection yf = yahooFinance.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(yf.getInputStream(), "UTF-8"));
	
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					sb.append(inputLine);
				}
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public void allStockCrawler() {
		ArrayList<StockInfo> stockInfoList = new ArrayList<StockInfo>();
		int queryDataLimit = 20;
		
		System.out.print("[Stock] Fetching stock ID: ");
		StockIDCrawler.parser();
		
		ArrayList<String> stockIDList = new ArrayList<String>(StockIDCrawler.TWStockInfoMap.keySet());
		System.out.println(stockIDList.size());
		System.out.println("[Stock] Fetching stock infomation");
		ArrayList<String> tempList = new ArrayList<String>();
		for (int i = 0 ; i < stockIDList.size(); i++) {
			tempList.add(stockIDList.get(i));
			
			if ((i % queryDataLimit == 0) || i == (stockIDList.size()-1)) {
				String[] symbols = new String[tempList.size()];
				symbols = tempList.toArray(symbols);
				Map<String, Stock> stockMap = multipleStock(symbols);
				
				if (stockMap.size()!= symbols.length) {
					System.out.println("symbols: "+symbols);
				}
				
				StockInfo stockInfo;
				for (String symbol: symbols) {
					try {
						Stock stock = stockMap.get(symbol);
						
						BigDecimal price = null;
						BigDecimal change = null;
						BigDecimal peg = null;
						BigDecimal dividend = null;				
						try {price = stock.getQuote().getPrice();} catch (Exception e) {}
						try {change = stock.getQuote().getChangeInPercent();} catch (Exception e) {}
						try {peg = stock.getStats().getPeg();} catch (Exception e) {}
						try {dividend = stock.getDividend().getAnnualYieldPercent();} catch (Exception e) {}
						
						String stockName = StockIDCrawler.TWStockInfoMap.get(symbol);
						
						if (price != null) {
							stockInfo = new StockInfo(stockName,symbol,price.doubleValue(), stock.getCurrency(), stock.getName());
							stockInfoList.add(stockInfo);
						} else {
							System.out.println("[Stock] Stock is no data: "+stockName+" | "+symbol);
						}
					} catch (Exception e) {
						
					}
				}
				tempList.clear();
			}
		}
		
		for (StockInfo si: stockInfoList) {
			si.print();
		}
		System.out.println("[Stock] Stock info count: "+stockInfoList.size());
//		for (Entry<String, String> entry: StockIDCrawler.TWStockInfoMap.entrySet()) {
//			String stockID = entry.getKey();
//			String stockName = entry.getValue();
//			singleStock(stockID, stockName);
//		}
	}
	
	public static void main(String[] args) {
		YahooFinanceAPI testYahooFinance = new YahooFinanceAPI();
		// Query single stock
//		String stockID = "2330.TW";
//		String stockName = "";
//		testYahooFinance.singleStock(stockID, stockName);
		
		// Query multiple stock
//		String[] symbols = new String[] {"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};
//		testYahooFinance.multipleStock(symbols);
		
		// Currency exchange
		String FxSymbol = CurSymbols.USDEUR;
		FxSymbol = "USDTWD=X";
		testYahooFinance.FXQuotesByFxSymbol(FxSymbol);
		String symbol = CurSymbols.CNYTWD;
		testYahooFinance.FXQuotes(symbol);
		
//		String currencyFrom = "rmb";
//		String currencyTo = "usd";
//		testYahooFinance.FXQuotes(currencyFrom, currencyTo);
		
		// Query stock information through the web request and parse the feedback JSON 
//		testYahooFinance.callYahooFinanceAPI();
		
		// Query multiple stock with All Taiwan stock ID
//		testYahooFinance.allStockCrawler();
	}
}
