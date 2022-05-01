package RedditSentimentAnalyzer;

import org.apache.log4j.BasicConfigurator;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static RedditSentimentAnalyzer.SentimentAnalyzer.*;

public class Main {

    public static void main(String[] args) {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        TickerAnalysis analysis = new TickerAnalysis();

        /**
         * ENTER TICKER NAME HERE
         *
         * (TO RUN QUICKER TESTS: go to TickerAnalysis.java and uncomment 'sublist')
         */
        String ticker = "AAPL";

        try {
            //EDIT DATE INFO HERE
            Date d1 = sdformat.parse("2022-01-01");
            Date d2 = sdformat.parse("2022-03-31");

            int sentiment = analysis.getAverageSentimentOfTicker(ticker, d1, d2);
            String sentimentStr = "NULL";

            if (sentiment == 0) {
                sentimentStr = "Very negative";
            } else if (sentiment == 1){
                sentimentStr = "Negative";
            } else if (sentiment == 2){
                sentimentStr = "Neutral";
            } else if (sentiment == 3){
                sentimentStr = "Positive";
            } else if (sentiment == 4){
                sentimentStr = "Very Positive";
            }

            System.out.println("\n" + "Between " + sdformat.format(d1) + " and " + sdformat.format(d2) +
                    ": The average sentiment of " + ticker +  " was '" + sentimentStr + "'.");

            List<HistoricalQuote> appleHistQuotes = StockHistoryAnalyzer.getHist(ticker, d2);
            BigDecimal reddit = appleHistQuotes.get(0).getClose();
            BigDecimal current = appleHistQuotes.get(appleHistQuotes.size() - 1).getClose();
            BigDecimal difference = current.subtract(reddit);

            System.out.println("\nThe difference between the current price and the price at the" +
                    "time of the last reddit post is " + difference.toPlainString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}

