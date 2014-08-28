package uk.ac.dotrural.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.TwitterException;


public class App {


    static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) throws TwitterException {
	
	UserStreamListenerImpl irpTwitterImpl = new UserStreamListenerImpl();

//	logger.debug("getting followers id ...");
	logger.debug("Followers Id Count : " + irpTwitterImpl.twitterComponent.getFollowersId("magicfinger87").length);
//	logger.debug("Friends Id Count : " + irpTwitterImpl.twitterComponent.getFriendsId("magicfinger87").length);
//	logger.debug("user tweet Count : " + irpTwitterImpl.twitterComponent.getAllTweetsByUserScreenName("magicfinger87").size());


    }

   
}
