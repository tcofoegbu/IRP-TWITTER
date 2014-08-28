/**
 * 
 */
package uk.ac.dotrural.entity;

import twitter4j.DirectMessage;

/**
 * @author charles
 *
 */
public class TwitterDirectMessage extends Message{
    private DirectMessage dm;
    public TwitterDirectMessage(DirectMessage dm) {
	super(dm.getText(), dm.getSenderScreenName());
	this.setDm(dm);
    }
    public DirectMessage getDm() {
	return dm;
    }
    public void setDm(DirectMessage dm) {
	this.dm = dm;
    }


}
