package controlleur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import metier.*;
import services.UserService;
import servicesImp.UserServiceImp;

@WebServlet("/Register")
public class RegisterController extends HttpServlet{
	Authentification auth;
	UserService userService;
	
	@Override
	public void init() throws ServletException {
		auth = new Authentification();
		userService = new UserServiceImp();
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		if(userName!=null && password!=null && !userName.isEmpty() && !password.isEmpty()) {
			User user = new User();
			String hashedPassword = metier.Test.hashPassword(password);
			user.setUserName(userName);
			user.setPassword(hashedPassword);
			boolean isExist = auth.userExisteAlready(user);
			if(isExist) {
				req.setAttribute("error", "A user with user name :"+ userName +" Already exist in database!!!");
				req.getRequestDispatcher("/WEB-INF/register.page.jsp").forward(req, resp);
			}else {
				
				userService.createNewUser(user);
				req.getRequestDispatcher("/WEB-INF/login.page.jsp").forward(req, resp);
			}
		}else {
			req.getRequestDispatcher("/WEB-INF/register.page.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
