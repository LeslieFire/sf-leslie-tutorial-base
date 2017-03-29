
package ds.serv.bubo.arabic;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.util.Scanner; 
//** WEKA
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.attributeSelection.ChiSquaredAttributeEval;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Utils;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.MultiFilter;
//import weka.core.stemmers.ArabicLightStemmer;
import weka.classifiers.functions.LibLINEAR;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.Evaluation;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import weka.attributeSelection.Ranker;

/**
*
* @author Eshrag A. Refaee
*/
public class SAAT {

/**
* @param args the command line arguments
*/
public static void main(String[] args) {
// User input of a query
Scanner user_input = new Scanner( System.in );
String query;
System.out.print("Enter a query word: ");
query = user_input.next( );

Twitter twitter = new TwitterFactory(getConfig().build()).getInstance();
BufferedWriter csv = null;
String in;

int tweetCount = 200;
int neut_count=0;
int positive_count=0;
int negative_count=0;

try {
csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir")+"tweets.arff"), "UTF-8"));
alltweets = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir")+"alltweets.txt"), "UTF-8"));

//** ARFF file headers (polar vs. neutral)
csv.write("@relation tweet_SA");
csv.write("\r\n");
csv.write("\r\n");
csv.write("@attribute norm-word_Ar string");
csv.write("\r\n");
csv.write("@attribute sentiment_1 {polar,neutral}");
csv.write("\r\n");
csv.write("\r\n");
csv.write("@data");
csv.write("\r\n");
csv.newLine();

//** List of queries (from an external text file)
// in = new BufferedReader(new InputStreamReader(new
//FileInputStream(System.getProperty("user.dir")+"queries.txt"), "UTF-8"));
in= query ;
String entity;
entity=query;

//** Reading multiple queries in sequence
//  while ((entity = in.readLine()) != null) {itemNo++;
System.out.println(entity + "   " + itemNo);
showTweetBySearch(twitter, entity.trim(), csv, tweetCount);
csv.close();
alltweets.close();
Thread.sleep(6000);

//*** Calculating Annotation Time (start)
long starttime = System.currentTimeMillis();

//*** load trained model and annotate tweets (polar vs. neutral)
System.out.println(" ");
System.out.println(" ");
System.out.println("Annotating retrieved tweets as polar vs. neutral (get predictions/labels)");
System.out.println("************************************************");
System.out.println("label what? " + query + " tweets ");
System.out.println(" ");
System.out.println("load pre-trained serialized model ...");
System.out.println(" ");
DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
Date date = new Date();
System.out.println("Experiment date/time: " + dateFormat.format(date)); 
System.out.println(" ");
System.out.println(" ");

//*** load unlabeled data
System.out.println("load unlabeled data ...");
Instances unlabeled = new Instances(
new BufferedReader(
new FileReader(System.getProperty("user.dir")+"tweets.arff")));

//*** set class attribute
unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

//*** create copy
Instances labeled = new Instances(unlabeled);

//*** deserialize model (polar vs. neutral)
System.out.println("load and deserialize pre-trained model (polar vs. neutral)...");
System.out.println(" ");
Classifier fc = (Classifier) weka.core.SerializationHelper.read(System.getProperty("user.dir")+"polarVSneut_LibLinear_Ser.model");
//*** label instances
System.out.println("label the new instances ...");
System.out.println(" ");
for (int i = 0; i < unlabeled.numInstances(); i++)
{double fcLabel = fc.classifyInstance(unlabeled.instance(i));
labeled.instance(i).setClassValue(fcLabel);}
//*** save labeled data
System.out.println("labeled data about "+ query+" successfully written into an ARFF ...");
System.out.println(" ");
BufferedWriter writer = new BufferedWriter(
new FileWriter(System.getProperty("user.dir")+query+"_tweets_polarVSneutral-AutoLabeled.arff"));
writer.write(labeled.toString());
writer.newLine();
writer.flush();
writer.close();
System.out.println(" ");
//***********************************
//*** Annottaing the subjective tweets as positive vs. negative
// input file
FileReader reader = new FileReader(System.getProperty("user.dir")+query+"_tweets_polarVSneutral-AutoLabeled.arff");
BufferedReader bufferedReader = new BufferedReader(reader);

//*** output file = subjective tweets only
FileWriter writer_subjective = new FileWriter(System.getProperty("user.dir")+"subjective_tweets.arff");
writer_subjective.write("@relation tweets_subjective");
writer_subjective.write("\r\n");
writer_subjective.write("\r\n");
writer_subjective.write("@attribute norm-word_Ar string");
writer_subjective.write("\r\n");
writer_subjective.write("@attribute sentiment_1 {negative,positive}");
writer_subjective.write("\r\n");
writer_subjective.write("\r\n");
writer_subjective.write("@data");
writer_subjective.write("\r\n");

//** reading polar vs. neutral file
String line;
while ((line = bufferedReader.readLine()) != null)
{if(line.startsWith("@")){continue;}
else{if(line.contains(",polar")){writer_subjective.write(line.replaceAll(",polar", " ")+", ?");
writer_subjective.write("\r\n");}
else{
neut_count=neut_count+1;}}}
// close file
writer_subjective.flush();
writer_subjective.close();
Thread.sleep(6000);
//**************************************
//*** annotating subjective tweets as positive vs. negative
//*** load trained model and annotate tweets (positive vs negative)
System.out.println(" ");
System.out.println(" ");
System.out.println("Annotating subjective tweets as positive vs. negative  (get predictions/labels)");
System.out.println("************************************************");
System.out.println(" ");
System.out.println("load pre-trained serialized model ...");
System.out.println(" ");
System.out.println(" ");
System.out.println(" ");
//** load unlabeled data
System.out.println("load subjective data ...");
Instances subjective = new Instances(
new BufferedReader(
new FileReader(System.getProperty("user.dir")+"subjective_tweets.arff")));

//*** set class attribute
subjective.setClassIndex(subjective.numAttributes() - 1);

//*** create copy
Instances labeled_subjective = new Instances(subjective);

//*** deserialize model (positive vs. negative)

System.out.println("load and deserialize pre-trained model (positive vs. negative) ...");
System.out.println(" ");
Classifier fc_subjective = (Classifier) weka.core.SerializationHelper.read(System.getProperty("user.dir")+"posVSneg_LibLinear_Ser.model");

// label instances
System.out.println("label the subjective instances ...");
System.out.println(" ");
for (int i = 0; i < subjective.numInstances(); i++)
{
double fcLabel = fc_subjective.classifyInstance(subjective.instance(i));
labeled_subjective.instance(i).setClassValue(fcLabel);
}
// save labeled data
System.out.println("labeled data about "+ query+" successfully written into an ARFF ...");
System.out.println(" ");
BufferedWriter writer_posVSneg = new BufferedWriter(
new FileWriter(System.getProperty("user.dir")+query+"_tweets_positiveVSnegative-AutoLabeled.arff"));

writer_posVSneg.write(labeled_subjective.toString());
writer_posVSneg.newLine();
writer_posVSneg.flush();
writer_posVSneg.close();

// read pos vs neg and count instances
FileReader reader_posVSneg = new FileReader(System.getProperty("user.dir")+query+"_tweets_positiveVSnegative-AutoLabeled.arff");
BufferedReader bufferedReader_posVSneg = new BufferedReader(reader_posVSneg);

String line_posVSneg;
while ((line_posVSneg = bufferedReader_posVSneg.readLine()) != null)
{
if(line_posVSneg.contains(",positive"))
{positive_count= positive_count+1;
}
else
{negative_count=negative_count+1;}}
bufferedReader_posVSneg.close();

System.out.println("Total positive : "+positive_count);
System.out.println(" ");
System.out.println("Total negative : "+negative_count);
System.out.println(" ");
System.out.println("Total neutral : "+neut_count);
System.out.println(" ");

//***************************************************
//*** calc. annotation time (end)
long stoptime = System.currentTimeMillis();
long elapsedtime = stoptime - starttime;
long elapsedtime_seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedtime);
System.out.println("Total annotation time: " + elapsedtime_seconds + " Seconds");
System.out.println(" ");
//**************************************
//** where the output files are saved
System.out.println(" ");
System.out.println("Working Directory = " + System.getProperty("user.dir"));
} catch (Exception ex) {
try {
csv.close();
//in.close();
//   alltweets.close();
} catch (IOException ex1) {
Logger.getLogger(SAAT.class.getName()).log(Level.SEVERE, null, ex1);}}}
//***********************************************************
// public var
public static BufferedWriter alltweets = null;
public static long fileNumber = 1;
public static int itemNo = 0;
//*************************************************************
public static void WriteToFile(String fileName, String tweetText) {
try {
BufferedWriter out = new BufferedWriter(
new OutputStreamWriter(new FileOutputStream("tweets/" + fileName), "UTF16"));
alltweets.write(tweetText.replace('\r', ' ').replace('\n', ' '));
//alltweets.write(tweetText);
alltweets.newLine();
out.write(tweetText);
out.close();}
catch (UnsupportedEncodingException ex) {
System.err.println("unsupported encoding");

} catch (FileNotFoundException ex) {
System.err.println("File not found:\n" + ex.toString());

} catch (IOException ex) {
System.err.println("io exception");}}

//***********************************************************
// Twitter API Config. (credintials of the API) obtained via  https://dev.twitter.com/apps
public static ConfigurationBuilder getConfig()
{
ConfigurationBuilder cb = new ConfigurationBuilder();
cb.setDebugEnabled(true);
cb.setOAuthConsumerKey("");
cb.setOAuthConsumerSecret("");
cb.setOAuthAccessToken("");
cb.setOAuthAccessTokenSecret("");
return cb;}
//************************************************
public static void showTweetBySearch(Twitter twitter, String keyword, BufferedWriter csv, int count) throws IOException, InterruptedException
{
//  System.out.println("search");

String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
String UserNamePattern = "(@?:\\s|\\A)[@]+([A-Za-z0-9-_]+)";
String UserNamePattern2 = "^@?(\\w){1,15}$";
String hashtagPattern = "#([^#\\s]+)";
String neg_EMo_in_Qutation_1 = "\\:\\(.*\\)";
String neg_EMo_in_Qutation_2 = "\\:\\s\\(.*\\)";
String QesMrak_Eng = "\\?";
String QesMrak_Ar = "\\?";
String QuesMark_EnOrAr = "[\\?\\?]";
String neg_Emo_1 = "\\:\\(";
String neg_Emo_2 = "\\:\\s\\(";
String pos_Emo_1 = "\\:\\)";
String pos_Emo_2 = "\\:\\s\\)";
String qut = "?";
String qut2 = "?";
String punct= "\\p{Punct}|\\d";
String length="([\\u0600-\\u06FFA-Za-z0-9])\\1{2,}";
try
{Query query = new Query(keyword).lang("ar");// correctly restricts search to Arabic

QueryResult result;
int i = 0;
do{
result = twitter.search(query);
List<Status> tweets = result.getTweets();

for (Status tweet : tweets)
{/* re-tweet removal */
if (tweet.isRetweet())
{continue;}
++fileNumber;
if (fileNumber % 100 == 0)
System.out.println("fileno " + fileNumber);

// write cleaned up tweets as an ARFF file to be annotated by the model 
csv.write('"'+tweet.getText().replaceAll("\\r?\\n", " ").replaceAll(urlPattern," URL ").replaceAll(UserNamePattern, " USER-NAME ").replaceAll(UserNamePattern2, " USER-NAME ").replaceAll(hashtagPattern, " HashTag ").replaceAll(length, "$1$1  lengthening ").replaceAll(punct, " PUNC ").replaceAll(",", " ").replaceAll(";", " ") +'"'+", ?");

// write tweets as a JSON object
FileWriter json;
json = new FileWriter("tweets-json.txt", true);
json.write(tweet.toString().replaceAll("\\r?\\n", ""));
json.write("\r\n");
csv.newLine();
if (++i == count)
{return;}}
System.out.println("tweet number " + fileNumber);
System.out.println("waiting...");

//** control delay/waiting time between requests/queries
Thread.sleep(6000);}
while ((query = result.nextQuery()) != null);
System.out.println(" ");
System.out.println("retrieved tweets successfully written to output file...");
System.out.println(" ");}
catch (TwitterException te)
{te.printStackTrace();
System.out.println("Failed to search tweets: " + te.getMessage());
System.exit(-1);}
catch (IOException ex) {
System.err.println("io exception");}}}


