package services;
import java.util.List;

import entities.*;


public interface GroupService {
	
	
	public List<Group> getAllGroups(User user);
	public void createNewGroup(Group group);
	public void addUserToGroup(User user, Group group);
	public void removeUserToGroup(User user, Group group);
	public Group getGroupById(Long id);
	public void deleteDelete(Long id);
	public void updateGroup(Group group);
	List<Message> getMessageOfGroup(Long idGroup);
	
}
