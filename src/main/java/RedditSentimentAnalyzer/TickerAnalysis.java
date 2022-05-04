package RedditSentimentAnalyzer;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static RedditSentimentAnalyzer.SentimentAnalyzer.getExtremePostSentiment;
import static RedditSentimentAnalyzer.SentimentAnalyzer.getPostSentiment;

public class TickerAnalysis {

    RedditPostParser parser = new RedditPostParser();

    public TickerAnalysis() {
        SentimentAnalyzer.init();
    }


    public int getAverageSentimentOfTicker(String ticker, Date startDate, Date endDate) {

        int totalSentimentScore = 0;

        List<RedditPost> posts = parser.getPostsForTicker(ticker, startDate, endDate);

        System.out.println("Calculating sentiment | This may take a while");

        System.out.println("Analyzing...");

        //For testing purposes, un-comment this
        //posts = posts.subList(0,10);

        int numberOfPosts = 0;

        for (RedditPost post : posts) {
            if (!post.body.isEmpty()) {

                //System.out.print(".");
                //int sentiment = getPostSentiment(post.body, 0.4f);
                int sentiment = getExtremePostSentiment(post.body);
                System.out.print(sentiment);

                numberOfPosts += 1;

                totalSentimentScore += sentiment;

            }
        }

        int avrgSentiment = Math.round((float) totalSentimentScore/numberOfPosts);

        return avrgSentiment;
    }

}
