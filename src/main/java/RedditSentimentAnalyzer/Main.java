package RedditSentimentAnalyzer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static RedditSentimentAnalyzer.SentimentAnalyzer.*;

public class Main {

    public static void printTickerInfo(String ticker, int sentiment, Date d1, Date d2){

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

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
                ": The mean sentiment of " + ticker +  " was " + sentiment + ": '" + sentimentStr + "'.");
    }

    public static void main(String[] args) {

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        TickerAnalysis analysis = new TickerAnalysis();

        List<String> tickers = Arrays.asList("TSLA", "NVDA", "FB", "AMD", "AAPL", "GME", "SDC", "NOK", "BBBY",
                "BB", "TGT", "KO", "WFC", "CVS", "GM");

        for (String ticker : tickers) {
            try {
                //EDIT DATE INFO HERE
                Date d1 = sdformat.parse("2021-11-01");
                Date d2 = sdformat.parse("2021-12-31");

                int sentiment = analysis.getAverageSentimentOfTicker(ticker, d1, d2);

                printTickerInfo(ticker, sentiment, d1, d2);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}

