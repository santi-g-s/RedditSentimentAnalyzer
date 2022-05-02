package RedditSentimentAnalyzer;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalyzer {
    static StanfordCoreNLP pipeline;

    public static void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public static void main(String[] args) {
        init();
    }

    /**
     * Weighted-average sentiment
     *
     * For a post it is very likely that the first and last sentence of the post are the most consequential
     * when it comes to the perceived sentiment of the post.
     *
     * This is adapted, using code from:
     * https://blogs.oracle.com/javamagazine/post/java-sentiment-analysis-stanford-corenlp
     *
     * The weighting factor is passed in as a real number in the range [0, 1]. Adapting by multiplying with
     * number of sentences.
     *
     * @param post The multi-sentence post with which you want to process
     * @param weight A real number in [0,1]
     */
    public static int getWeightedAveragePostSentiment(String post, float weight)
    {
        int sentenceSentiment;
        int reviewSentimentAverageSum = 0;
        int reviewSentimentWeightedSum = 0;
        Annotation annotation = pipeline.process(post);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        int numOfSentences = sentences.size();
        int factor = Math.round(numOfSentences*weight);
        if (factor == 0) {
            factor = 1;
        }
        int divisorLinear = numOfSentences;
        int divisorWeighted = 0;

        for (int i = 0; i < numOfSentences; i++)
        {
            Tree tree = sentences.get(i).get(SentimentAnnotatedTree.class);
            sentenceSentiment = RNNCoreAnnotations.getPredictedClass(tree);
            reviewSentimentAverageSum = reviewSentimentAverageSum + sentenceSentiment;
            if(i == 0 || i == numOfSentences -1) {
                reviewSentimentWeightedSum = reviewSentimentWeightedSum + sentenceSentiment*factor;
                divisorWeighted += factor;
            }
            else
            {
                reviewSentimentWeightedSum = reviewSentimentWeightedSum + sentenceSentiment;
                divisorWeighted += 1;
            }
        }
//        System.out.println("Number of sentences:\t\t" + numOfSentences);
//        System.out.println("Adapted weighting factor:\t" + factor);
//        System.out.println("Weighted average sentiment:\t" + Math.round((float) reviewSentimentWeightedSum/divisorWeighted));
//        System.out.println("Linear average sentiment:\t" + Math.round((float) reviewSentimentAverageSum/divisorLinear));

        return Math.round((float) reviewSentimentWeightedSum/divisorWeighted);
    }

    /**
     * Calculates the sentiment value of a reddit post.
     *
     * Ignores all sentences in the post with a "Neutral" sentiment to gather
     * a more real-world reflective value.
     * @param post The reddit post to analyse
     * @return A value for sentiment in the integer range [0..4]
     */
    public static int getExtremePostSentiment(String post) {
        int sentenceSentiment;
        int totalSentimentSum = 0;
        Annotation annotation = pipeline.process(post);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        int numOfSentences = 0;

        for (int i = 0; i < sentences.size(); i++) {
            Tree tree = sentences.get(i).get(SentimentAnnotatedTree.class);
            sentenceSentiment = RNNCoreAnnotations.getPredictedClass(tree);
            //System.out.println(sentences.get(i) + " | Sentiment: " + sentenceSentiment);

            //ignore sentiment of 2
            if (sentenceSentiment != 2) {
                totalSentimentSum = totalSentimentSum + sentenceSentiment;
                numOfSentences += 1;
            }
        }

        int average = Math.round((float) totalSentimentSum / numOfSentences);
        return average;
    }

}
