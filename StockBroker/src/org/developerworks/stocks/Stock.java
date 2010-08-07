package org.developerworks.stocks;

import java.util.ArrayList;
import java.util.List;

import org.developerworks.stocks.Stocks.Portfolio;
import org.developerworks.stocks.Stocks.Quote;

public class Stock {
	public Stock(String symbol, String name, double price) {
		this.symbol = removeChars(symbol);
		this.name = removeChars(name);
		this.price = price;
	}
	
	private final String symbol;
	private final String name;
	private final double price;
	
	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	@Override 
	public int hashCode(){
		return symbol.hashCode();
	}
	
	@Override 
	public boolean equals(Object other){
		if (other instanceof Stock){
			Stock otherStock = (Stock) other;
			return otherStock.symbol.equals(symbol);
		}
		return false;
	}
	
	public String toXml(){
		return "<stock><symbol>" + symbol + "</symbol><name><![CDATA[" +
			name + "]]></name><price>" + price + "</price></stock>";
	}
	
	public String toJson(){
		return "{ 'stock' : { 'symbol' : " +symbol +", 'name':" + name + 
			", 'price': '" + price + "'}}";
	}
	
	public static String toXml(List<Stock> stocks){
		StringBuilder xml = new StringBuilder("<stocks>");
		for (Stock s : stocks){
			xml.append(s.toXml());
		}
		xml.append("</stocks>");
		return xml.toString();
	}
	
	public static String toJson(List<Stock> stocks){
		StringBuilder json = new StringBuilder("{'stocks' : [");
		for (Stock s : stocks){
			json.append(s.toJson());
			json.append(',');
		}
		json.deleteCharAt(json.length() - 1);
		json.append("]}");
		return json.toString();
	}
	
	public static Stocks.Portfolio toProtoBuf(List<Stock> stocks){
		List<Stocks.Quote> quotes = new ArrayList<Stocks.Quote>(stocks.size());
		for (Stock s : stocks){
			Quote q = 
				Quote.newBuilder()
					.setName(s.name)
					.setSymbol(s.symbol)
					.setPrice(s.price)
					.build();
			quotes.add(q);
		}
		return Portfolio.newBuilder().addAllQuote(quotes).build();
	}
	
	private static String removeChars(String s){
		return s.replaceAll("\"", "").replaceAll("'","");
	}
}
