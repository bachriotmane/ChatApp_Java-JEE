package metier;


import java.util.*;
import entities.User;
import services.UserService;
import servicesImp.UserServiceImp;

public class Authentification {
	
	
	UserService userService = new UserServiceImp();
	
	public Authentification() {}
	
	
	public boolean userExisteAlready(User user) {
		List<User> users = userService.getAllUsers();
		for(User u : users) {
			if(u.getUserName().equals(user.getUserName())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyCredentials(String userName, String password) {
		List<User> users = userService.getAllUsers();
		for(User u : users) {
			if(metier.Test.checkPassword(password, u.getPassword()) && u.getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
}
