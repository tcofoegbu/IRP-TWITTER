package uk.ac.dotrural.twitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamListener;
import uk.ac.dotrural.entity.Message;
import uk.ac.dotrural.util.EcoSystemGateway;

/**
 * 
 * @author charles
 *
 */
public class TwitterComponent implements EcoSystemGateway{

    private static Twitter twitter;
    private static UserStreamListener userStreamListenerImpl;
    
    public TwitterComponent(UserStreamListener customUserStreamListenerImpl){
	TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
	twitter = TwitterFactory.getSingleton();
	userStreamListenerImpl = customUserStreamListenerImpl;
	twitterStream.addListener(userStreamListenerImpl);	
	twitterStream.user();
	
    }



    public void sendDirectMessage(String screenName, String text) {
	try {
	    twitter.sendDirectMessage(screenName, text);
	} catch (TwitterException e) {
	    e.printStackTrace();
	}
	
    }

    public Collection<Status> getAllTweetsByUserScreenName(String screenName) {
	List<Status> userTweets = new ArrayList<Status>();
	try {
	    List<Status> userTimeLine = twitter.getUserTimeline(screenName);
	    for(Status status : userTimeLine){
		if(status.getUser().getScreenName().equalsIgnoreCase(screenName)){
		    userTweets.add(status);
		}
	    }
	} catch (TwitterException e) {
	    e.printStackTrace();
	}
	return userTweets;
    }
    
    public Collection<Status> getTweetsBySearchTerm(String searchTerm) {
	List<Status> tweetList = new ArrayList<Status>();
        try {
            Query query = new Query(searchTerm);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                tweetList.addAll(tweets);
            } while ((query = result.nextQuery()) != null);
            
        } catch (TwitterException te) {
            te.printStackTrace();
        }
	return tweetList;
    }

    public Collection<Status> getTweetsByHashTag(String userId, String hashTag) {
	List<Status> tweetList = new ArrayList<Status>();
        try {
            Query query = new Query(hashTag);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                tweetList.addAll(tweets);
            } while ((query = result.nextQuery()) != null);
            
        } catch (TwitterException te) {
            te.printStackTrace();
        }
	return tweetList;
    }

    public Collection<Status> getTweetsByTwitterHandle(String userId, String twitterHandle) {
	// TODO Aimplement getTweetsByTwitterHandle
	return null;
    }

    public Collection<Status> getTweetsByUserIdAndSearchTerm(String userId, String searchTerm) {
	// TODO implement getTweetsByUserIdAndSearchTerm
	return null;
    }

    public Collection<Status> getTweetsByUserIdAndHashTag(String userId, String hashTag) {
	// TODO implement getTweetsByUserIdAndHashTag
	return null;
    }

    public Collection<Status> getTweetsByUserIdAndTwitterHandle(String userId, String twitterHandle) {
	// TODO implement getTweetsByUserIdAndTwitterHandle
	return null;
    }

    public long[] getFollowersId(String screenName) {
	long[] followersIds = null; 
	    try {
		followersIds = twitter.getFollowersIDs(screenName, -1).getIDs();
	    } catch (TwitterException e) {
		e.printStackTrace();
	    }
	return followersIds;
    }

    public long[] getFriendsId(String screenName) {
	long[] friendsIds = null; 
	    try {
		friendsIds = twitter.getFriendsIDs(screenName, -1).getIDs();
	    } catch (TwitterException e) {
		e.printStackTrace();
	    }
	return friendsIds;
    }

    public Collection<?> retrieveConversationBetweenUsers(String tweetEntryId,
	    String userId1, String userId2) {
	// TODO implement retrieving of conversations b/w two users
	return null;
    }

    public void followUser(String screenName) {
	try {
	    twitter.createFriendship(screenName);
	} catch (TwitterException e) {
	    e.printStackTrace();
	}
	
    }

    public void unFollowUser(String screenName) {
	try {
	    twitter.destroyFriendship(screenName);
	} catch (TwitterException e) {
	    e.printStackTrace();
	}
	
    }

    public void sendMessage(Message msg) {
	sendDirectMessage(msg.getReplyTo(), msg.getText());	
    }


    
}
