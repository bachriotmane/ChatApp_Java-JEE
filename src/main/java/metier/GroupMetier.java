package metier;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import entities.Group;
import entities.User;
import services.GroupService;
import services.UserService;
import servicesImp.GroupServiceImp;
import servicesImp.UserServiceImp;
import util.HibernateUtil;

public class GroupMetier {
	GroupService groupService = new GroupServiceImp();
	UserService userService = new UserServiceImp();
	
	public List<User> getMemebersOfGrou(Long id) {
		Group grp = groupService.getGroupById(id);
		List<User> users = grp.getMemebers();
		return users;
	}

	public List<User> getNonMembersOfGroup(Long id) {
		Group group = groupService.getGroupById(id);
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();

	    try {
	        String hql = "FROM User u WHERE :group NOT MEMBER OF u.group";
	        Query query = session.createQuery(hql);
	        query.setParameter("group", group);

	        List<User> resultList = query.list();
	        session.getTransaction().commit();
	        return resultList;
	    } catch (Exception e) {
	        session.getTransaction().rollback();
	        System.out.println(e.getMessage());
	        return null;
	    }
	}
}
