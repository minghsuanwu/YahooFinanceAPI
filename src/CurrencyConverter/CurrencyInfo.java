package CurrencyConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CurrencyInfo {
	String currencyFrom = "";
	String currencyTo = "";
	
	String exchangeRateStr = "";
	double exchangeRate = 0.0;
	
	String currency_converter = "";
	
	public CurrencyInfo(String currencyFrom, String currencyTo) {
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
	}

	public String getExchangeRateStr() {
		return exchangeRateStr;
	}

	public void setExchangeRateStr(String exchangeRateStr) {		
		this.exchangeRateStr = exchangeRateStr;
		
		Pattern p = Pattern.compile("[^0-9.]");   
		Matcher m = p.matcher(exchangeRateStr);
		double d = Double.parseDouble(m.replaceAll("").trim());
		setExchangeRate(d);
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getCurrency_converter() {
		return currency_converter;
	}

	public void setCurrency_converter(String currency_converter) {
		this.currency_converter = currency_converter;
	}
	
	public void print() {
		if (!currency_converter.isEmpty()) {
			System.out.println(currency_converter);
		} else {
			System.out.println(currency_converter = "1 "+currencyFrom+" = "+exchangeRate+" "+currencyTo);
		}
	}
}