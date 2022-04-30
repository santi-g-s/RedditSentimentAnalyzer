package RedditSentimentAnalyzer;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.*;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;

import java.util.LinkedList;
import java.util.List;

public class RedditPostParser {

    UserAgent userAgent = new UserAgent("stockBot", "com.example.stockBot",
            "v0.1", "SantiagoSEAS");

    Credentials credentials = Credentials.script("SantiagoSEAS", "grea_haum7BINT8fass",
            "hzf8QeszcgN6HThrT4tDoA", "72Ot_XupQxzWJs9PYoiZwn5JpY3X3g");

    NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);

    RedditClient reddit = OAuthHelper.automatic(adapter, credentials);

    //TOD0: FIX THIS!!!!!!

    private static List<String> extractTickersFromString(List<String> tickers, String text) {

        List<String> output = new LinkedList<>();

        List<String> comparison = new LinkedList<>();
        comparison.add("RBLX");

        String[] words = text.split(" ");
        for(String word : words) {
            word = word.replace("$", "");
            word = word.replaceAll("\\p{Punct}", "");
            if (comparison.contains(word)) {
                output.add(word);
            }
        }
        System.out.println(output);
        return output;
    }

    public static boolean isUpperCase(String s)
    {
        for (int i=0; i<s.length(); i++)
        {
            if (!Character.isUpperCase(s.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }


    public List<RedditPost> getPostsForSubreddit(String subredditName, List<String> tickers){

        List<RedditPost> posts = new LinkedList<>();

        // frontPage() returns a Paginator.Builder
        DefaultPaginator<Submission> paginator = reddit.subreddit(subredditName)
                .posts()
                .sorting(SubredditSort.TOP)
                .timePeriod(TimePeriod.DAY)
                .limit(5)
                .build();

        Listing<Submission> submissions = paginator.next();
        for (Submission s : submissions) {
            RedditPost post = new RedditPost(s.getTitle(), s.getSelfText(),s.getCreated(), extractTickersFromString(tickers, s.getSelfText()));
            posts.add(post);
            //System.out.println(post);
        }
        return posts;
    }

}

