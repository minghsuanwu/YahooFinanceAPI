package CurrencyConverter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;


public class GoogleCurrencyConverter implements CurrencyConverter {
    public double convert(String currencyFrom, String currencyTo) throws IOException {
    	CurrencyInfo ci = new CurrencyInfo(currencyFrom, currencyTo);
//        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpClient httpclient = HttpClients.createDefault();
//        String url = "https://www.google.com/finance/converter?a=1&from="+currencyFrom+"&to="+currencyTo;
        String url = " https://finance.google.com/finance/converter?a=1&from="+currencyFrom+"&to="+currencyTo;
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpclient.execute(request);
        
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(request, responseHandler);
        
        Document xmlDoc  = Jsoup.parse(responseBody);
        Element element = xmlDoc.getElementById("currency_converter_result");
        if (element != null) {
        	List<?> childNodeList = element.childNodes();
        	StringBuilder sb = new StringBuilder();
        	for (Object childeNode: childNodeList) {
        		if (childeNode instanceof TextNode) {
        			TextNode textNode = (TextNode) childeNode;
        			String str = textNode.getWholeText();
        			sb.append(str);
        		} else if (childeNode instanceof Element) {
        			Element e = (Element) childeNode;
        			List<?> childNodes = e.childNodes();
        			TextNode textNode = (TextNode) childNodes.get(0);
        			String str = textNode.getWholeText();
        			ci.setExchangeRateStr(str);
        			sb.append(str);
        			ci.setCurrency_converter(sb.toString());
        			break;
        		}
        	}

    		System.out.println(ci.getCurrency_converter());
        }
        
        double exchangeRate = ci.getExchangeRate();
        
//        URL url = new URL("http://quote.yahoo.com/d/quotes.csv?s=" + currencyFrom + currencyTo + "=X&f=l1&e=.csv"); 
//        HttpURLConnection http = (HttpURLConnection) url.openConnection();
//        http.setRequestMethod("POST");
//        InputStream input = http.getInputStream();
//        http.disconnect();
        
        return exchangeRate;
    }

    public static void main(String[] args) {
        GoogleCurrencyConverter gcc = new GoogleCurrencyConverter();
        try {
        	String currencyFrom = "JPY";
        	String currencyTo = "TWD";
            double current = gcc.convert(currencyFrom, currencyTo);
            System.out.println(current);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}