<%@page import="com.fasterxml.jackson.annotation.JsonSubTypes.Type"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="entities.*,java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User List</title>
  <style>
  a{
  	text-decoration: none;
  	color : white;
  }
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f5f5f5;
      color: #333;
      margin: 0;
      padding: 20px;
    }

    h1 {
      color: #3498db;
      text-align: center;
    }

    .user-list {
      list-style-type: none;
      padding: 0;
    }

    .user {
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 1px solid #ddd;
      padding: 15px;
      background-color: #fff;
      border-radius: 8px;
      margin-bottom: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .user img {
      border-radius: 50%;
      width: 50px;
      height: 50px;
      object-fit: cover;
      margin-right: 15px;
    }

    .user .user-info {
      flex-grow: 1;
    }

    .user-buttons {
      display: flex;
      align-items: center;
    }

    .user button {
      background-color: #27ae60;
      color: #fff;
      border: none;
      padding: 10px 15px;
      cursor: pointer;
      border-radius: 5px;
      margin-right: 10px;
      transition: background-color 0.3s;
    }

    .user button.ban {
      background-color: #e74c3c;
    }

    .user button:hover {
      background-color: #2ecc71;
    }

    .add-user-button {
      background-color: #3498db;
      color: #fff;
      border: none;
      padding: 15px 20px;
      cursor: pointer;
      border-radius: 5px;
      margin-top: 20px;
      transition: background-color 0.3s;
      display: block;
      margin-left: auto;
      margin-right: auto;
    }

    .add-user-button:hover {
      background-color: #2980b9;
    }
  </style>
</head>
<body>
	<%
		List<User> users = (List<User>)request.getAttribute("users");
		String type = request.getParameter("type");
		String currentUserName = (String)session.getAttribute("username");
	%>
  <h1>User List for <%=type %></h1>
  <ul class="user-list">
  <%for(int i=0;users!=null && i<users.size();i++){ %>
  <%if(users.get(i).getUserName().equals(currentUserName)) continue; %>
    <li class="user">
      <img src="images/profile.png" alt="User 1">
      <div class="user-info">
        <span style="font-size: 1.2em; font-weight: bold;"><%=users.get(i).getUserName() %></span>
      </div>
      <div class="user-buttons">
      <%if( type!=null && type.equals("moderateur") && !users.get(i).getUserType().equals(TypeUser.BANNED)){ %>
        <button class="ban"><a href="Groupchat?action=updateTypeUser&newType=banned&idUser=<%=users.get(i).getIdUser()%>">Banir</a></button>
       
      <%}else if(type!=null && "admin".equals(type)){ %>
       	<%if(users.get(i).getUserType().equals(TypeUser.BANNED)){ %>
        	<h4 style="color: red"><i>BANNED</i></h4>
        <%} else {%>
       	<button class="ban"><a href="Groupchat?action=updateTypeUser&newType=banned&idUser=<%=users.get(i).getIdUser()%>">Banir</a></button>
        <button><a href="Groupchat?action=updateTypeUser&newType=admin&idUser=<%=users.get(i).getIdUser()%>">Set as an Admin</a></button>
        <%}%>
        <%}else if(users.get(i).getUserType().equals(TypeUser.BANNED)){ %>
        	<h4 style="color: red"><i>BANNED</i></h4>
        <%} %>
        <div>
        <%if(users.get(i).getStatus().equals(Status.ONLINE)){%>
        <img alt="" src="images/online.png">
        <%}else if(users.get(i).getStatus().equals(Status.OFFLINE)){%>
        <img alt="" src="images/offline.png">
        <%} %>
        </div>
      </div>
    </li>
    <%} %>
  </ul>
</body>
</html>
    