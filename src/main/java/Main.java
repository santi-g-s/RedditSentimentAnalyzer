import masecla.reddit4j.client.Reddit4J;
import masecla.reddit4j.client.UserAgentBuilder;
import masecla.reddit4j.exceptions.AuthenticationException;
import masecla.reddit4j.objects.subreddit.RedditSubreddit;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Reddit4J client = Reddit4J.rateLimited()
                .setClientId("5uKgfFURIYyFAcMyHIMjAA").setClientSecret("mh_37gjzxDMK-56dJ6nqyrftUPjU-A")
                .setUserAgent(new UserAgentBuilder().appname("StockSentiment").author("AccidentSensitive237").version("1.0"));


        try {
            client.userlessConnect();

            RedditSubreddit stocksSubreddit = client.getSubreddit("wallstreetbets");
            String image = stocksSubreddit.getBannerImg();
            System.out.println(image);

        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }






    }



}

