package controlleur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import entities.Status;
import entities.User;
import metier.Authentification;
import services.UserService;
import servicesImp.UserServiceImp;


@WebServlet("/Login")
public class LoginController extends HttpServlet{
	Authentification auth;
	@Override
	public void init() throws ServletException {
		auth = new Authentification();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("username");
		String passwd = req.getParameter("password");
		UserService userService = new  UserServiceImp();
		HttpSession session = req.getSession();
		
		
		if(session!=null &&  session.getAttribute("username")!=null) {
			session.setAttribute("etat", "online");
			req.getRequestDispatcher("/WEB-INF/chat.page3.jsp").forward(req, resp);
		}
		if(userName != null && passwd != null) {
			
			boolean validCredentials  = auth.verifyCredentials(userName, passwd);
			if(validCredentials) {
				req.setAttribute("username", userName);
				
				session = req.getSession();
				session.setAttribute("username", userName);
				
				String userNameS = (String)session.getAttribute("username");
				User user = userService.getUserByUserName(userNameS);
				session.setAttribute("user", user);
				user.setStatus(Status.ONLINE);
				userService.updateUser(user);
				resp.sendRedirect("Chat");
			}else {
				req.setAttribute("message","Invalide Credentials" );
				req.getRequestDispatcher("/WEB-INF/login.page.jsp").forward(req, resp);
			}
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
		req.getRequestDispatcher("/WEB-INF/login.page.jsp").forward(req, resp);
	}

}
