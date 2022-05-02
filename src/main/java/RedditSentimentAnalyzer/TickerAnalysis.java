package RedditSentimentAnalyzer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static RedditSentimentAnalyzer.SentimentAnalyzer.getExtremePostSentiment;
import static RedditSentimentAnalyzer.SentimentAnalyzer.getWeightedAveragePostSentiment;

public class TickerAnalysis {

    RedditPostParser parser = new RedditPostParser();

    public TickerAnalysis() {
        SentimentAnalyzer.init();
    }


    public List<Integer> getAverageSentimentOfTicker(String ticker, Date startDate, Date endDate) {

        int totalSentimentScore = 0;

        List<RedditPost> posts = parser.getPostsForTicker(ticker, startDate, endDate);

        System.out.println("Calculating sentiment | This may take a while");

        System.out.print("Analyzing");

        //For testing purposes, un-comment this
        //posts = posts.subList(0,5);

        List<Integer> sentiments = new LinkedList<>();

        for (RedditPost post : posts) {
            if (!post.body.isEmpty()) {

                System.out.print(".");
                //int sentiment = getWeightedAveragePostSentiment(post.body, 0.4f);
                int sentiment = getExtremePostSentiment(post.body);
                //System.out.println(post.title + "| Extreme Sentiment: " + sentiment);

                sentiments.add(sentiment);

                totalSentimentScore += sentiment;

            }
        }

        int avrgSentiment = Math.round((float) totalSentimentScore/posts.size());

        return sentiments;
    }

}
