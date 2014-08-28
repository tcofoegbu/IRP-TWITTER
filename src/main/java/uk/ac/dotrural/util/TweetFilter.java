package uk.ac.dotrural.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.DirectMessage;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

/**
 * 
 * @author charles
 * 
 */

public class TweetFilter {
    
    static Logger logger = LoggerFactory.getLogger(TweetFilter.class);    
    private static Properties prop = new PropertyFileUtil("EcosystemMessaging.properties", "Properties used to configure ecosystem messaging service").getProperties();
    
    private static String currentOathAccountScreenName;
    private static List<String> hashTags;
    private static List<String> twitterHandles;
    private static List<String> searchTerms;
    private static boolean ignoreTweetFromMyAccount;

    static {
	List<String> filterHashTags = getPropertyList(prop, "tweetfilter.hashtags", ";");
	List<String> filterHandles = getPropertyList(prop, "tweetfilter.handles", ";");
	List<String> filterSearchTerms = getPropertyList(prop, "tweetfilter.searchTerms", ";");
	boolean ignoreAccountTweets = Boolean.valueOf(prop.getProperty("tweetfilter.ignoreTweetFromMyAccount"));
	String accountScreenName = prop.getProperty("twitter.accountScreenName");

	TweetFilter.setIgnoreTweetFromMyAccount(ignoreAccountTweets);
	TweetFilter.setCurrentOathAccountScreenName(accountScreenName);
	TweetFilter.setSearchTerms(filterSearchTerms);
	TweetFilter.setHashTags(filterHashTags);
	TweetFilter.setTwitterHandles(filterHandles);
    }

    public static List<String> getPropertyList(Properties prop, String key, String delimiter){
	String[] propertyValues = prop.getProperty(key).split(delimiter);
	
	List<String> propertyList = new ArrayList<String>();
	for(String property : propertyValues){
	    propertyList.add(property);
	}
	
	return propertyList;
    }
    public static boolean filterTweet(Status tweet) {
	if (isIgnoreTweetFromMyAccount() && tweet.getUser().getScreenName().equalsIgnoreCase(getCurrentOathAccountScreenName())) {
	    return false;
	}
	
	if(TweetFilter.filterByHashTags(tweet)){
	    return true;	    
	}
	
	if(TweetFilter.filterByTwitterHandle(tweet)){
	    return true;
	}
	if(TweetFilter.filterBySearchTerms(tweet)){
	    return true;
	}
	return false;
    }

    public static boolean filterDM(DirectMessage dm){
	if (isIgnoreTweetFromMyAccount() && dm.getSenderScreenName().equalsIgnoreCase(getCurrentOathAccountScreenName())) {
	    return false;
	}
	return true;
    }
    public static boolean filterBySearchTerms(Status status) {
	for(String searchTerm : TweetFilter.getSearchTerms()){
	    if(status.getText().contains(searchTerm)){
		return true;
	    }
	}
	return false;
    }

    public static boolean filterByHashTags(Status tweet) {
	HashtagEntity[] hashTags = tweet.getHashtagEntities();
	if (hashTags.length < 1) {
	    return false;
	}
	for (HashtagEntity hashTag : hashTags) {
	    if (TweetFilter.getHashTags().contains(hashTag.getText())) {
		return true;
	    }
	}
	return false;
    }

    public static boolean filterByTwitterHandle(Status tweet) {
	UserMentionEntity[] handles = tweet.getUserMentionEntities();
	if (handles.length < 1) {
	    return false;
	}
	for (UserMentionEntity tHandle : handles) {
	    if (TweetFilter.getTwitterHandles().contains(tHandle.getText())) {
		return true;
	    }
	}
	return false;
    }


    public static String getCurrentOathAccountScreenName() {
	return currentOathAccountScreenName;
    }

    public static void setCurrentOathAccountScreenName(String currentOathAccountScreenName) {
	TweetFilter.currentOathAccountScreenName = currentOathAccountScreenName;
    }

    public static List<String> getHashTags() {
	return hashTags;
    }

    public static void setHashTags(List<String> hashTags) {
	TweetFilter.hashTags = hashTags;
    }

    public static List<String> getTwitterHandles() {
	return twitterHandles;
    }

    public static void setTwitterHandles(List<String> twitterHandles) {
	TweetFilter.twitterHandles = twitterHandles;
    }

    public static List<String> getSearchTerms() {
	return searchTerms;
    }

    public static void setSearchTerms(List<String> searchTerms) {
	TweetFilter.searchTerms = searchTerms;
    }

    public static boolean isIgnoreTweetFromMyAccount() {
	return ignoreTweetFromMyAccount;
    }

    public static void setIgnoreTweetFromMyAccount(boolean ignoreTweetFromMyAccount) {
	TweetFilter.ignoreTweetFromMyAccount = ignoreTweetFromMyAccount;
    }

}
