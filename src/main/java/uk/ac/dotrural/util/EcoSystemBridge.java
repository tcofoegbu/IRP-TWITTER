package uk.ac.dotrural.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import uk.ac.dotrural.entity.Message;

/**
 * 
 * @author charles
 * 
 */

public class EcoSystemBridge {
    
    static Logger logger = LoggerFactory.getLogger(EcoSystemBridge.class);      
    private static Properties prop = new PropertyFileUtil("EcosystemMessaging.properties", "Properties used to configure ecosystem messaging service").getProperties();
    private static Client client = Client.create(); 
    private static final String TWITTER_HANDLE_OR_HASHTAG_PATTERN = "(@|#)([A-Za-z0-9_]{1,15})";
    
    public static void main (String [] args){
//	Pattern pattern = Pattern.compile(TWITTER_HANDLE_OR_HASHTAG_PATTERN);
//	System.out.println(" >>>>>>>>>> "+ pattern.matcher("#busm8 STOP 23 to Aberdeen @busm8 bla bla bla #oknal").matches());

//	Message msg = new Message("#busm8 service 6900421 X95","blan");
//	Message msg2 = new Message("#busm8 stop 6900421","blan");
//	processRequest(new EcoSystemGatewayMock(), msg2);
//	
//	String newText = rawText.replaceAll(EcoSystemBridge.TWITTER_HANDLE_OR_HASHTAG_PATTERN, "").trim();
//	System.out.println(newText);
//	marshalRequest(newText);

	
//	System.out.println(Arrays.toString(stringToSplit.split("(?<=\\G.{139})")));

    }
    
    
    
    public static void processRequest(EcoSystemGateway ecoSysGateway, Message msg) {
	String response = "@" + msg.getReplyTo() + ", " + prop.getProperty("message.welcome");
	String rawTweetText = msg.getText();
	String cleanedTweet = rawTweetText.replaceAll(TWITTER_HANDLE_OR_HASHTAG_PATTERN, "").trim();
	response = marshalRequest(cleanedTweet);
	
	/**
	 * if message is greater than 140 characters; then break down to streams of 140 chars and send accordingly
	 */
	if (response.length() > 139) {
	    List<String> msgChunks = splitTextToChunks(response, 139);
	    for (String msgChunk : msgChunks) {
		msg.setText(msgChunk);
		ecoSysGateway.sendMessage(msg);
		System.out.println(msgChunk + "\n\n");

	    }
	} else {
	    msg.setText(response);
	    ecoSysGateway.sendMessage(msg);
	}
    }
    
    public static List<String> splitTextToChunks(String textToSplit, int size) {
	    List<String> chunkArray = new ArrayList<String>((textToSplit.length() + size - 1) / size);
	    for (int start = 0; start < textToSplit.length(); start += size) {
		chunkArray.add(textToSplit.substring(start, Math.min(textToSplit.length(), start + size)));
	    }
	    return chunkArray;
	}
    
    public static String marshalRequest(String msg) {
	String response = "";
	
	if (msg.isEmpty()) {
	    return prop.getProperty("message.badrequest");
	}
	
	String[] msgParameters = msg.split(" ");
	
	if(msgParameters[0].equalsIgnoreCase("help") && msgParameters.length == 1){
	    return prop.getProperty("message.help");
	}	
	
	if(msgParameters[0].equalsIgnoreCase("help") && msgParameters.length == 2){
	    return prop.getProperty("message.help."+msgParameters[1].toLowerCase());
	}

	String foundProp = prop.getProperty("request."+msgParameters[0].toLowerCase());
	if (foundProp.isEmpty()) {
	    throw new IllegalArgumentException();
	}

	String[] propertyValues = foundProp.split(";");
	if ((msgParameters.length) < propertyValues.length) {
	    throw new IllegalArgumentException();
	}

	try {
	    logger.debug("service >>>>>>>>>>>>>>>>>>> " + propertyValues[0]);
	    WebResource webResource = client.resource(propertyValues[0]);
	    if (msgParameters.length > 1) {
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();

		if (msgParameters[0].equalsIgnoreCase("locate")) {
		    logger.debug(">>>>>>>>>>>>>>>>>>>> about building geolocation params");
		    StringBuilder addressBuilder = new StringBuilder();
		    for (int x = 1; x < msgParameters.length; x++) {
			addressBuilder.append(msgParameters[x]).append(" ");
			logger.debug("found >>>>>>>>>>>>>>>>>>>>>>>> "+ msgParameters[x]);
		    }
		    logger.debug("compiled address >>>>>>>>>>>>>>>>>>>>>>>>>> "+ addressBuilder.toString());
		    queryParams.add(propertyValues[1], addressBuilder.toString());
		} else {
		    logger.debug(">>>>>>>> Query paramenters <<<<<<<<");
		    for (int x = 1; x < msgParameters.length; x++) {
			queryParams.add(propertyValues[x], msgParameters[x]);
			logger.debug(propertyValues[x] + " : "	+ msgParameters[x]);
		    }
		}
		response = webResource.queryParams(queryParams).get(String.class);
	    } else {
		response = webResource.get(String.class);
		// LocationSummary ls = (LocationSummary)
		// webResource.accept(MediaType.TEXT_PLAIN).get(Class.forName("LocationSummary"));
	    }
	} catch (ClientHandlerException e) {
	    response = "This service is unavailable at the moment please try again later.";
	    // TODO develop method to notify service admin of service
	    // unavailability?.
	}

	JSONObject jobj = new JSONObject(response);
	String plainResponse = jobj.getString("text_message");
	return plainResponse;
    }
}
