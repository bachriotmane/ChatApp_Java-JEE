package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMessage;
	private String messageText;
	private Date dateEnvoie;
	
	
	@ManyToOne
	private User sender;
	
	@ManyToOne
	private User receiver;
	
	@ManyToOne
	private Group group;
	
	public Message() {}
	
	public Message(Long idMessage, String messageText, Date dateEnvoie, User sender, User receiver, Group group) {
		super();
		this.idMessage = idMessage;
		this.messageText = messageText;
		this.dateEnvoie = dateEnvoie;
		this.sender = sender;
		this.receiver = receiver;
		this.group = group;
	}

	public Long getIdMessage() {
		return idMessage;
	}
	public void setIdMessage(Long idMessage) {
		this.idMessage = idMessage;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public Date getDateEnvoie() {
		return dateEnvoie;
	}
	public void setDateEnvoie(Date dateEnvoie) {
		this.dateEnvoie = dateEnvoie;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}	
	
	
	
	
	
	
	
	
	

}
