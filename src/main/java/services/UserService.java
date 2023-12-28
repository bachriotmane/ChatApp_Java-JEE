package services;
import entities.*;
import java.util.*;

public interface UserService {
	public void createNewUser(User user);
	public List<User> getAllUsers();
	public User getUserById(Long id);
	public void deleteUser(Long id);
	public void updateUser(User user);
	public List<Message> getUserMessages(Long id);
	public User getUserByUserName(String userName);
	public List<User> getUserByMc(String mc);
}
