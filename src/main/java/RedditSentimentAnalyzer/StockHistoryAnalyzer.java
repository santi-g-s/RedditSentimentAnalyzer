package RedditSentimentAnalyzer;

import org.apache.log4j.BasicConfigurator;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.*;

public class StockHistoryAnalyzer {

    public static List<HistoricalQuote> getHist(String ticker, Date date) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        // gets all quotes from this date to present
        from.setTime(date);

        Stock stock;
        try {
            stock = YahooFinance.get(ticker);
            List<HistoricalQuote> stockHistQuotes = stock.getHistory(from, to, Interval.DAILY);
            return stockHistQuotes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
