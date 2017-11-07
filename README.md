# YahooFinanceAPI
Sample code for Yahoo Finance API

Fork from https://github.com/natehefner/yahoostocks-java

Entry Point: Call main method in YahooFinanceAPI.java

Query Method:
1. singleStock(String stockID, String stockName): 
  Only query one stock information

2. multipleStock(String[] symbols): 
  Query multiple stock information
3. callYahooFinanceAPI(): 
  Query stock information through the web request and parse the feedback JSON.
  But Yahoo shut down the service. I will try other way to query the stock data.
  
4. allStockCrawler():
  Query multiple stock with All Taiwan stock ID


by Ming 2017/11/7
