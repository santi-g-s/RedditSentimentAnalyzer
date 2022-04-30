package RedditSentimentAnalyzer;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedditPost {
    String title;
    String body;
    Date createdDate;
    List<String> tickers;

    public RedditPost(String title, String body, Date createdDate, List<String> tickers){
        this.title = title;
        this.body = body;
        this.createdDate = createdDate;
        this.tickers = tickers;
    }

    @Override
    public String toString() {
        return "*Post*: " + title + "\n"
                + "Tickers: " + tickers.toString()
                + "Created Date: " + createdDate
                + "\n" + body;
    }



}
