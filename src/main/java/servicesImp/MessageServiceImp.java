package servicesImp;

import org.hibernate.Session;

import entities.Message;
import services.MessageService;
import util.HibernateUtil;

public class MessageServiceImp implements MessageService{

	@Override
	public void createNewMessage(Message message) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(message);
		session.getTransaction().commit();
	}

	@Override
	public Message getMessageById(Long id) {
		
		return null;
	}

	@Override
	public void deleteMessage(Long id) {
		
	}

	@Override
	public void updateMessage(Message message) {
		
	}

}
