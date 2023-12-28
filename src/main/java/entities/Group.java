package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name ="groups")
public class Group{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGroup;
	private String grouName;
	private String groupDescription;
	

	@ManyToOne
	private User admin;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> memebers = new ArrayList<User>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "group")
	private List<Message> messages;
	
	public Group() {}

	public Group(String grouName, String groupDescription) {
		
		this.grouName = grouName;
		this.groupDescription = groupDescription;
	}

	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public String getGrouName() {
		return grouName;
	}

	public void setGrouName(String grouName) {
		this.grouName = grouName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public User getAdmins() {
		return admin;
	}

	public void setAdmins(User admin) {
		this.admin = admin;
	}

	public List<User> getMemebers() {
		return memebers;
	}

	public void setMemebers(List<User> memebers) {
		this.memebers = memebers;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
	
	
	
	
}
