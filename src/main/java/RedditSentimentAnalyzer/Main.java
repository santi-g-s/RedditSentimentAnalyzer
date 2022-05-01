package RedditSentimentAnalyzer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        RedditPostParser parser = new RedditPostParser();

        List<String> tickers = new LinkedList<>();
        tickers.add("RBLX");

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date d1 = sdformat.parse("2022-01-01");
            Date d2 = sdformat.parse("2022-01-31");

            parser.getPostsForSubreddit("stocks", tickers,d1, d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }




    }

}

