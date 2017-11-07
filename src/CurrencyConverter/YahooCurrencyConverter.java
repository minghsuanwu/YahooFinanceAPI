package CurrencyConverter;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;

public class YahooCurrencyConverter implements CurrencyConverter{
	public double convert(String currencyFrom, String currencyTo) throws IOException {
    	CurrencyInfo ci = new CurrencyInfo(currencyFrom, currencyTo);
//        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpClient httpclient = HttpClients.createDefault();
        String url = "http://quote.yahoo.com/d/quotes.csv?s=" + currencyFrom + currencyTo + "=X&f=l1&e=.csv";
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpclient.execute(request);
        
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(request, responseHandler);
        double exchangeRate = Double.parseDouble(responseBody);
        ci.setExchangeRate(exchangeRate);
        
        ci.print();
        return exchangeRate;
	}
    public static void main(String[] args) {
    	YahooCurrencyConverter ycc = new YahooCurrencyConverter();
        try {
            double current = ycc.convert("JPY", "TWD");
//            System.out.println(current);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
