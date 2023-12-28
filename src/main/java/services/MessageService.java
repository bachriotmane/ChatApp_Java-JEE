package services;

import entities.*;

public interface MessageService {
	
	
	public void createNewMessage(Message message);
	public Message getMessageById(Long id);
	public void deleteMessage(Long id);
	public void updateMessage(Message message);
	
	
}
