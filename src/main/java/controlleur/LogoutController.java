package controlleur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Status;
import entities.User;
import services.UserService;
import servicesImp.UserServiceImp;


@WebServlet("/Logout")
public class LogoutController extends HttpServlet{
	UserService userService = new UserServiceImp();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session !=null) {
			String userName = (String)session.getAttribute("username");
			User user = userService.getUserByUserName(userName);
			
			if(user!=null) {
				user.setStatus(Status.OFFLINE);
				userService.updateUser(user);
			}
			
			session.invalidate();
			resp.sendRedirect("http://localhost:8080/ChatAppTP/Login");
		}
		
	}
}
