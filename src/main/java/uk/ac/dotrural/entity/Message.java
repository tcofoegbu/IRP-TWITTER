package uk.ac.dotrural.entity;


/**
 * 
 * @author charles
 *
 */

public class Message {
    private String text;
    private String replyTo;

    public Message(String text, String replyTo){
	this.text = text;
	this.replyTo = replyTo;
    }
    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public String getReplyTo() {
	return replyTo;
    }

    public void setReplyTo(String replyTo) {
	this.replyTo = replyTo;
    }
}
