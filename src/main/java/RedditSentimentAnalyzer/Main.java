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

    private static String sentimentStringOf(Integer sentiment) {
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

        return sentimentStr;
    }

    public static Integer medianOf(List<Integer> values) {
        Collections.sort(values);

        if (values.size() % 2 == 1)
            return values.get((values.size() + 1) / 2 - 1);
        else {
            Integer lower = values.get(values.size() / 2 - 1);
            Integer upper = values.get(values.size() / 2);

            return Math.toIntExact(Math.round((float) (lower + upper) / 2.0));
        }
    }

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
            Date d1 = sdformat.parse("2021-10-01");
            Date d2 = sdformat.parse("2021-12-31");

            List<Integer> sentiments = analysis.getAverageSentimentOfTicker(ticker, d1, d2);

            Integer totalSentimentSum = 0;

            for (Integer sentiment : sentiments) {
                totalSentimentSum += sentiment;
            }

            Integer meanSentiment = Math.round((float) totalSentimentSum / sentiments.size());

            String meanSentimentStr = sentimentStringOf(meanSentiment);
            String medianSentimentStr = sentimentStringOf(medianOf(sentiments));


            System.out.println("\n💰 $" + ticker +
                    "\n" + "Between " + sdformat.format(d1) + " and " + sdformat.format(d2) +
                    ": \n The mean sentiment of " + ticker +  " was " + meanSentiment + ": '" + meanSentimentStr + "'."
                    +  "\n The median sentiment of " + ticker + " was " + medianOf(sentiments) + " '"
                    + medianSentimentStr + "'.");

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

