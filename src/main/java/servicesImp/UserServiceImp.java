package servicesImp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entities.Message;

import entities.User;
import services.UserService;
import util.HibernateUtil;

public class UserServiceImp implements UserService{

	@Override
	public List<User> getAllUsers() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query req = session.createQuery("select u from User u");
		List<User> users = req.list();
		session.getTransaction().commit();
		return users;
	}

	@Override
	public User getUserById(Long id) {
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		try {
			
			Object obj = session.get(User.class, id);
			if(obj == null) {
				throw new RuntimeException("User not found !!!!!!");
			}
			User p = (User)obj;
			session.getTransaction().commit();
			return p;
		}catch(Exception e) {
			session.getTransaction().rollback();
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public void deleteUser(Long id) {
		
	}

	@Override
	public void updateUser(User user) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
	}

	@Override
	public void createNewUser(User user) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
	}

	@Override
	public List<Message> getUserMessages(Long id) {
		User user = getUserById(id);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "SELECT m FROM Message m WHERE (m.sender = :user OR m.receiver = :user) AND m.group is NULL ORDER BY m.dateEnvoie\r\n"
				+ "";
		Query query = session.createQuery(hql);
		query.setParameter("user", user);
	    List<Message> messages = query.setParameter("user", user).list();
	    session.getTransaction().commit();
	    return messages;
	}

	@Override
	public User getUserByUserName(String userName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "SELECT u FROM User u WHERE u.userName = :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", userName);
	    User user = (User) query.uniqueResult();
	    session.getTransaction().commit();
//	    session.flush();
	    return user;
	}

	@Override
	public List<User> getUserByMc(String mc) {
		List<User> users = null;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query req = session.createQuery("select p from User p where p.userName Like :x");
		req.setString("x","%" + mc + "%");
		users = req.list();
		session.getTransaction().commit();
		return users;
	}
	
}
