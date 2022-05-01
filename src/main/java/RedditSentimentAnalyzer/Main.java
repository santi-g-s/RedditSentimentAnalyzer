package RedditSentimentAnalyzer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static RedditSentimentAnalyzer.SentimentAnalyzer.findSentiment;
import static RedditSentimentAnalyzer.SentimentAnalyzer.init;

public class Main {

    public static void main(String[] args) {

        RedditPostParser parser = new RedditPostParser();

        SentimentAnalyzer.init();

        List<String> tickers = new LinkedList<>();
        tickers.add("NFLX");
        tickers.add("AAPL");
        tickers.add("INTC");

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        List<RedditPost> posts = new LinkedList<>();

        try {
            Date d1 = sdformat.parse("2022-01-01");
            Date d2 = sdformat.parse("2022-03-31");

            //posts = parser.getPostsForSubreddit("stocks", tickers, d1, d2);
            posts = parser.getPostsForTicker("NFLX", d1, d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (RedditPost post : posts) {
            if (!post.body.isEmpty()) {
                String sentiment = findSentiment(post.body);
                System.out.println(post.title + " | Tickers: " + post.tickers.toString() + " | Sentiment: " + sentiment);
            }
        }

    }

}

