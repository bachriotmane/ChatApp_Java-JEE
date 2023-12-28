package entities;

import java.util.*;

import org.hibernate.Session;

import metier.GroupMetier;
import services.GroupService;
import services.MessageService;
import services.UserService;
import servicesImp.GroupServiceImp;
import servicesImp.MessageServiceImp;
import servicesImp.UserServiceImp;
import util.HibernateUtil;

public class Test {
	public static void main(String[] args) {
		UserService userService = new UserServiceImp();
		GroupService groupService = new GroupServiceImp();
		GroupMetier grpMetier = new GroupMetier();
		
		//groupService.removeUserToGroup(user, group);
		String motPass = "123456";
		String hasedPasswoed = metier.Test.hashPassword(motPass);
		User user1 = new User("xxx", hasedPasswoed);
		userService.createNewUser(user1);
		
		
		User user = userService.getUserById(1L);
		String hashedPassword = user.getPassword();
		
		boolean isTrue = metier.Test.checkPassword(motPass, hashedPassword);
		System.out.println(isTrue);
		
//		MessageService nessagerService = new MessageServiceImp();
//		List<User> members = new ArrayList<>();
//		
//		User user1 = new User("otmane","12345");
//		User user2 = new User("chiboub","12345");
//		User user3 = new User("Salah","12345");
//		
//		userService.createNewUser(user1);
//		userService.createNewUser(user2);
//		userService.createNewUser(user3);
//		
//		members.add(user1);
//		members.add(user2);
//		members.add(user3);
//		
//		
//		
//		Group group = new Group("RCA", "Group descriptionn");
//		group.setMemebers(members);
//		
//		List<Message> messages = new ArrayList<Message>();
//		
//		
//		List<Group>grps = new ArrayList<Group>();
//		
//		grps.add(group);
//		
//		Message message1 = new Message();
//		message1.setDateEnvoie(new Date());
//		message1.setMessageText("Bonjour !");
//		message1.setReceiver(user1);
//		message1.setSender(user3);
//		
//		Message message2 = new Message();
//		message2.setDateEnvoie(new Date());
//		message2.setMessageText("Comment va tu !");
//		message2.setReceiver(user3);
//		message2.setSender(user1);
//		
//		Message message3 = new Message();
//		message3.setDateEnvoie(new Date());
//		message3.setMessageText("Hamdulah cv");
//		message3.setReceiver(user1);
//		message3.setSender(user3);
		
		//User user1 = userService.getUserById(5L);
		//Group group = groupService.getGroupById(2L);
		//groupService.removeUserToGroup(user1, group);
//		Message message4 = new Message();
//		message4.setDateEnvoie(new Date());
//		message4.setMessageText("Salam drari cv");
//		message4.setSender(user3);
//		message4.setGroup(group);
//		
//		Message message5 = new Message();
//		message5.setDateEnvoie(new Date());
//		message5.setMessageText("Hmdlh khoya lahfdk ,ont cv");
//		message5.setSender(user1);
//		message5.setGroup(group);
//		
//		
//		Message message6 = new Message();
//		message6.setDateEnvoie(new Date());
//		message6.setMessageText("Drari wach kayn cours gheda ?");
//		message6.setSender(user2);
//		message6.setGroup(group);
//		
//		messages.add(message4);
//		messages.add(message5);
//		messages.add(message6);
//		
//		nessagerService.createNewMessage(message1);
//		nessagerService.createNewMessage(message2);
//		nessagerService.createNewMessage(message3);
//		nessagerService.createNewMessage(message4);
//		nessagerService.createNewMessage(message5);
//		nessagerService.createNewMessage(message6);
		
//		User user5 = new User("redoune", "12345");
//		userService.createNewUser(user5);
//		
//		List<Group> messages1 = groupService.getAllGroups(user5);
//		
		
//		
//		groupService.createNewGroup(group);
//		
//		groupService.addUserToGroup(user5, group);
//		for(Group m : messages1) {
//			System.out.println("Group name : ||||||" + m.getGrouName());
//		}
	}
}
