package servicesImp;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entities.Group;
import entities.Message;
import entities.User;
import services.GroupService;
import util.HibernateUtil;

public class GroupServiceImp implements GroupService{

	@Override
	public void createNewGroup(Group group) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(group);
		session.getTransaction().commit();
	}

	@Override
	public void addUserToGroup(User user, Group group) {
		
		user.getGroup().add(group);
		group.getMemebers().add(user);
		
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(group);
		session.update(user);
		session.getTransaction().commit();
	}

	@Override
	public Group getGroupById(Long id) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Object obj = session.get(Group.class, id);
			if(obj == null) {
				throw new RuntimeException("Group not found !!!!!!");
			}
			Group  p = (Group)obj;
			session.getTransaction().commit();
			return p;
		}catch(Exception e) {
			session.getTransaction().rollback();
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public void deleteDelete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGroup(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Group> getAllGroups(User user) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query req = session.createQuery("select g from Group g");
		List<Group> groups = req.list();
		
		List<Group> groupsForUser = new ArrayList<Group>();	
		for(Group g : groups) {
			
			List<User> members = g.getMemebers();
			
			if(members !=null) {
				for(User u : members) {
					if(u.getUserName().equals(user.getUserName())) {
						groupsForUser.add(g);
					}
				}
			}
		}
		session.getTransaction().commit();
		return groupsForUser;
	}

	@Override
	public List<Message> getMessageOfGroup(Long idGroup) {
		Group group = getGroupById(idGroup);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "SELECT m FROM Message m WHERE m.group = :group ORDER BY m.dateEnvoie\r\n"
				+ "";
		Query query = session.createQuery(hql);
		query.setParameter("group", group);
	    List<Message> messages = query.list();
	    session.getTransaction().commit();
	    return messages;
	}

	@Override
	public void removeUserToGroup(User user, Group group) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

	    try {
	    	session.beginTransaction();

	        String sql = "DELETE FROM groups_users " +
	                     "WHERE memebers_idUser = :userId AND group_idGroup = :idGr";

	        session.createSQLQuery(sql)
	            .setParameter("userId", user.getIdUser())
	            .setParameter("idGr", group.getIdGroup())
	            .executeUpdate();
	        
	        session.getTransaction().commit();
	    } catch (Exception e) {
	    	System.out.println(e);
	    } 
		
	}

	
	
	

}
