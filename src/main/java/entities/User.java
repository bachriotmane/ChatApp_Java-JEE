package entities;


import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Entity
@Table(name="users")
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	private String userName;
	private String password;
	private TypeUser userType = TypeUser.CLASSIC;
	private Status status = Status.OFFLINE;
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public TypeUser getUserType() {
		return userType;
	}

	public void setUserType(TypeUser userType) {
		this.userType = userType;
	}

	
	
	@JsonIgnore
	@OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
	Set<Message> senderMessages;
	
	@JsonIgnore
	@OneToMany(mappedBy = "receiver", fetch = FetchType.EAGER)
	Set<Message> receiverMessages;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "memebers", fetch = FetchType.EAGER)
	Set<Group> group = new HashSet<Group>();

	@JsonIgnore
	@OneToMany(mappedBy = "admin",fetch = FetchType.EAGER )
	Set<Group> groupAdmin;

	
	
	public User() {}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Message> getSenderMessages() {
		return senderMessages;
	}

	public void setSenderMessages(Set<Message> senderMessages) {
		this.senderMessages = senderMessages;
	}

	public Set<Message> getReceiverMessages() {
		return receiverMessages;
	}

	public void setReceiverMessages(Set<Message> receiverMessages) {
		this.receiverMessages = receiverMessages;
	}

	public Set<Group> getGroup() {
		return group;
	}

	public void setGroup(Set<Group> group) {
		this.group = group;
	}

	public Set<Group> getGroupAdmin() {
		return groupAdmin;
	}

	public void setGroupAdmin(Set<Group> groupAdmin) {
		this.groupAdmin = groupAdmin;
	}
	
	
	
	
}
