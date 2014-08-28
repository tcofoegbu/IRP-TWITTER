package uk.ac.dotrural.entity;

import twitter4j.Status;

/**
 * 
 * @author charles
 *
 */

public class TwitterMessage extends Message{
    private Status tweet;
    public TwitterMessage(Status tweet) {
	super(tweet.getText(), tweet.getUser().getScreenName());
	this.tweet = tweet;
    }
    public Status getTweet() {
	return tweet;
    }
    public void setTweet(Status tweet) {
	this.tweet = tweet;
    }

}
