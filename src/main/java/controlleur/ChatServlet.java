package controlleur;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

import entities.Message;
import entities.TypeUser;
import entities.User;
import services.MessageService;
import services.UserService;
import servicesImp.MessageServiceImp;
import servicesImp.UserServiceImp;


@WebServlet("/Chat")
public class ChatServlet extends HttpServlet{
	UserService userService = new UserServiceImp();
	MessageService messageService = new MessageServiceImp();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext application = getServletContext();
		String id = req.getParameter("id");
		String action = req.getParameter("action");
		
		if(action!=null && "getUsers".equals(action)) {
		List<User> users = userService.getAllUsers();

		ObjectMapper objectMapper = new ObjectMapper();
		
		String jsonString = objectMapper.writeValueAsString(users);
		System.out.println(jsonString);
		resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
		}
		
		if(action!=null && "getConvs".equals(action)) {
			Long idUser = Long.valueOf(id);
			List<Message> messages = userService.getUserMessages(idUser);
			System.out.println("==========Then id : "+ id);
			
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

		
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			Date receivedDate = null;
	        try {
	            receivedDate = dateFormat.parse(date);
	            System.out.println("Received Date: " + receivedDate);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
			
			User senderObj = userService.getUserByUserName(sender);
			User receiverObj = userService.getUserById(Long.valueOf(receiver));
			
			Message message = new Message();
			message.setMessageText(messageText);
			message.setSender(senderObj);
			message.setReceiver(receiverObj);
			message.setDateEnvoie(receivedDate);
			messageService.createNewMessage(message);
			System.out.println(message.getMessageText());
			System.out.println("=========================++++++++++++++++++++Message CREATED+++++++++++++++++++++++++++================");
			
		}
		
		
		if(action !=null && "getUsersByUserName".equals(action)) {
			String key = req.getParameter("key");
			List<User> users = userService.getUserByMc(key);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			String jsonString = objectMapper.writeValueAsString(users);
			System.out.println(jsonString);
			resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
	        PrintWriter out = resp.getWriter();
	        out.print(jsonString);
	        out.flush();
			
		}
        
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		UserService userService = new UserServiceImp();
		if(session !=null) {
			System.out.println("SESSION");
			String username = (String)session.getAttribute("username");
			User user = userService.getUserByUserName(username);
			
			if(user!=null &&  user.getUserType().equals(TypeUser.MODERATEUR)) {
				session.setAttribute("usertype", TypeUser.MODERATEUR.toString());
			}else if(user!=null && user.getUserType().equals(TypeUser.ADMIN)) {
				session.setAttribute("usertype", TypeUser.ADMIN.toString());
			}else if(user!=null &&  user.getUserType().equals(TypeUser.BANNED)) {
				session.setAttribute("usertype", TypeUser.BANNED.toString());
			}else {
				session.setAttribute("usertype", TypeUser.CLASSIC.toString());
			}
		}
		req.getRequestDispatcher("/WEB-INF/chat.page3.jsp").forward(req, resp);
	}

}
