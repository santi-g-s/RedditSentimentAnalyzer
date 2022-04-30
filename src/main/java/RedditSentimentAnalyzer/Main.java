package RedditSentimentAnalyzer;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        RedditPostParser parser = new RedditPostParser();

        List<String> tickers = new LinkedList<>();
        tickers.add("RBLX");

        parser.getPostsForSubreddit("stocks", tickers);

    }

}

