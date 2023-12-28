package controlleur;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.Group;
import entities.Message;
import entities.TypeUser;
import entities.User;
import metier.GroupMetier;
import services.GroupService;
import services.MessageService;
import services.UserService;
import servicesImp.GroupServiceImp;
import servicesImp.MessageServiceImp;
import servicesImp.UserServiceImp;


@WebServlet("/Groupchat")
public class GroupChat extends HttpServlet{
	
	GroupService groupService ;
	UserService userService;
	MessageService messageService;
	GroupMetier groupMetier;
	@Override
	public void init() throws ServletException {
		groupService = new GroupServiceImp();
		userService = new UserServiceImp();
		messageService = new MessageServiceImp();
		groupMetier = new GroupMetier();
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if(action !=null && "getGroups".equals(action)) {
			System.out.println("Fetch All groups");
			
			String userName = (String)req.getSession().getAttribute("username");
			User user = userService.getUserByUserName(userName);
			
			List<Group> groups = groupService.getAllGroups(user);
			
			

			ObjectMapper objectMapper = new ObjectMapper();
			
			String jsonString = objectMapper.writeValueAsString(groups);
			System.out.println(jsonString);
			resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
	        PrintWriter out = resp.getWriter();
	        out.print(jsonString);
	        out.flush();
	        }
		if(action!=null && "getConvs".equals(action)) {
			String id = req.getParameter("id");
			Long idN = Long.valueOf(id);
			
			List<Message> messages = groupService.getMessageOfGroup(idN);
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(messages);
			resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
	        PrintWriter out = resp.getWriter();
	        System.out.println(jsonString);
	        out.print(jsonString);
	        out.flush();
		}
		if(action!=null && "sendMessage".equals(action)) {
			
			
			String messageText = req.getParameter("messageText");
			String sender = req.getParameter("sender");
			String receiver = req.getParameter("receiver");
			String date = req.getParameter("dateEnvoie");
			System.out.println("Receiver : ::::: " + receiver);
			
			
			
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Date receivedDate = null;
	        try {
	            receivedDate = dateFormat.parse(date);
	            System.out.println("Received Date: " + receivedDate);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
			
			User senderObj = userService.getUserByUserName(sender);
			Group receiverObj = groupService.getGroupById(Long.valueOf(receiver));
			
			Message message = new Message();
			message.setMessageText(messageText);
			message.setSender(senderObj);
			message.setGroup(receiverObj);
			message.setDateEnvoie(receivedDate);
			
			messageService.createNewMessage(message);
		}
		if(action!=null && "createNewGroup".equals(action)) {
			String groupName = req.getParameter("groupName");
			String groupDesc = req.getParameter("groupDesc");
			String groupAdmin = req.getParameter("groupAdmin");
			 Group group = new Group();
			 group.setGrouName(groupName);
			 group.setGroupDescription(groupDesc);
			 User user = userService.getUserByUserName(groupAdmin);
			 
			 group.setAdmins(user);
			 groupService.createNewGroup(group);
			 group = groupService.getGroupById(group.getIdGroup());
			 groupService.addUserToGroup(user, group);
		}
		if(action!=null && "memebresList".equals(action)) {
			String idG = req.getParameter("id");
			
			Group group = groupService.getGroupById(Long.valueOf(idG));
			List<User> users =  groupMetier.getMemebersOfGrou(Long.valueOf(idG));
			List<User> nonUsers = groupMetier.getNonMembersOfGroup(Long.valueOf(idG));
			
			System.err.println("memebresList");
			req.setAttribute("users", users);
			req.setAttribute("nonUsers", nonUsers);
			req.setAttribute("group", group);
			req.getRequestDispatcher("/WEB-INF/usersGroupList.jsp").forward(req, resp);
		}
		
	}
		
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		
		if(action!=null && "addUserToGroup".equals(action)) {
			Long idUser = Long.valueOf(req.getParameter("idUser")) ;
			Long idGroup =Long.valueOf(req.getParameter("idGroup"));
			Group group = groupService.getGroupById(idGroup);
			User user = userService.getUserById(idUser);
			groupService.addUserToGroup(user, group);
			req.getRequestDispatcher("Chat").forward(req, resp);
		}
		if(action!=null && "deleteUserFromGroup".equals(action)) {
			Long idUser = Long.valueOf(req.getParameter("idUser")) ;
			Long idGroup =Long.valueOf(req.getParameter("idGroup"));
			Group group = groupService.getGroupById(idGroup);
			User user = userService.getUserById(idUser);
			groupService.removeUserToGroup(user, group);
			req.getRequestDispatcher("Chat").forward(req, resp);	
		}
		if(action!=null && "displayUsers".equals(action)) {
			String type = req.getParameter("type");
			List<User> users = userService.getAllUsers();
			req.setAttribute("users", users);
			req.setAttribute("type", type);
			System.out.println(type);
			req.getRequestDispatcher("/WEB-INF/usersAdminModerateur.jsp").forward(req, resp);
		}
		if(action !=null && "updateTypeUser".equals(action)) {
			String newType = req.getParameter("newType");
			String uId = req.getParameter("idUser");
			User user = userService.getUserById(Long.valueOf(uId));
			if(user!=null && newType!=null) {
				if("admin".equals(newType)) {
					user.setUserType(TypeUser.ADMIN);
					userService.updateUser(user);
				}
				else if("banned".equals(newType)) {
					user.setUserType(TypeUser.BANNED);
					userService.updateUser(user);
				}
			}
		}
	}
}
