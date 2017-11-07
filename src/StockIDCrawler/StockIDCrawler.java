package StockIDCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class StockIDCrawler {
public static LinkedHashMap<String, String> TWStockInfoMap = new LinkedHashMap<String, String>();
	
	public static void parser() {
		try {
			String stockIDPage2 = "http://isin.twse.com.tw/isin/C_public.jsp?strMode=2";	// 上市證券
			String stockIDPage4 = "http://isin.twse.com.tw/isin/C_public.jsp?strMode=4";	// 上櫃證券
			String stockIDPage5 = "http://isin.twse.com.tw/isin/C_public.jsp?strMode=5";	// 興櫃證券
			URL url = new URL(stockIDPage2);
			Document xmlDoc = Jsoup.parse(url, 30000);
			Elements elements = xmlDoc.select("td[bgcolor=#FAFAD2]");
			
			for (Element element: elements) {
				element.getAllElements();
				for (Node node: element.childNodes()) {
					node.siblingIndex();
					String nodeText = node.toString();
					if (nodeText.contains("　")) {
						String stockID = nodeText.split("　")[0];
						String stockName = nodeText.split("　")[1];
						if (stockID.length() == 4) {
							TWStockInfoMap.put(stockID+".TW", stockName);
						} else {
							break;
						}
					}
				}
			}

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		StockIDCrawler.parser();
		for (Entry<String, String> entry: StockIDCrawler.TWStockInfoMap.entrySet()) {
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
	}

}
