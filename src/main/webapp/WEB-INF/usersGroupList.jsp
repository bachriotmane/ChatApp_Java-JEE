<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="entities.*, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User List</title>
  <style>
	a{
	  text-decoration: none;
	  color: white;
	}
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f5f5f5;
      color: #333;
      margin: 0;
      padding: 20px;
    }

    h1{
      color: #3498db;
      text-align: center;
    }
    
    h4{
      color: red;
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
      padding: 30px;
      background-color: #fff;
      border-radius: 8px;
      margin-bottom: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      width : 60%;
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
		List<User> mmbrs = (List<User>) request.getAttribute("users");
		List<User> nonMmbrs = (List<User>) request.getAttribute("nonUsers");
		Group group = (Group)request.getAttribute("group");
		String currentUserName = (String)session.getAttribute("username");
    	boolean isAdmin = group!=null && currentUserName!=null && currentUserName.equals(group.getAdmins().getUserName());
	%>
  <h1>User List</h1>
  <ul class="user-list">
  	<%for(int i=0;nonMmbrs!=null && i<nonMmbrs.size();i++){ %>
    <li class="user">
      <img src="images/profile.png" alt="User 1">
      <div class="user-info">
        <span style="font-size: 1.2em; font-weight: bold;"><%=nonMmbrs.get(i).getUserName() %></span>
      </div>
      <%if(isAdmin){ %>
      <div class="user-buttons">
        <button><a href="Groupchat?action=addUserToGroup&idUser=<%=nonMmbrs.get(i).getIdUser()%>&idGroup=<%=group.getIdGroup()%>">Add</a></button>
      </div>
      <%}else if(group.getAdmins().getUserName().equals(nonMmbrs.get(i).getUserName())){%>
      <h4>ADMIN</h4>
      <%}if(nonMmbrs.get(i).getUserType().equals(TypeUser.BANNED)) {%>
      	<h4 style="color: red"><i>BANNED</i></h4>
      <%} %>
      <div>
        <%if(nonMmbrs.get(i).getStatus().equals(Status.ONLINE)){%>
        <img alt="" src="images/online.png" width="10">
        <%}else if(nonMmbrs.get(i).getStatus().equals(Status.OFFLINE)){%>
        <img alt="" src="images/offline.png" width = "10">
        <%} %>
        </div>
    </li>
    <%} %>
    
    <%for(int i=0; mmbrs !=null && i<mmbrs.size();i++){ %>
    <%if(mmbrs.get(i).getUserName().equals(currentUserName)) continue; %>
    <li class="user">
      <img src="images/profile.png" alt="User <%=mmbrs.get(i).getIdUser()%>">
      <div class="user-info">
        <span style="font-size: 1.2em; font-weight: bold;"><%=mmbrs.get(i).getUserName() %></span>
      </div>
      <%if(isAdmin){ %>
      <div class="user-buttons">
        <button class="ban"><a href="Groupchat?action=deleteUserFromGroup&idUser=<%=mmbrs.get(i).getIdUser()%>&idGroup=<%=group.getIdGroup()%>">Exclude</a></button>
      </div>
      <%}else if(group.getAdmins().getUserName().equals(mmbrs.get(i).getUserName())){%>
      <h4>ADMIN</h4>
      <%}if(mmbrs.get(i).getUserType().equals(TypeUser.BANNED)) {%>
      	<h4 style="color: red"><i>BANNED</i></h4>
      <%} %>
      <div>
        <%if(mmbrs.get(i).getStatus().equals(Status.ONLINE)){%>
        <img alt="" src="images/online.png" width="20">
        <%}else if(mmbrs.get(i).getStatus().equals(Status.OFFLINE)){%>
        <img alt="" src="images/offline.png" width="20">
        <%} %>
        </div>
      
    </li>
    <%}%>
  </ul>
</body>
</html>
