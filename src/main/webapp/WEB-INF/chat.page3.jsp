<%@page import="entities.*"%>
<%@page import="org.hibernate.usertype.UserType"%>
<%@page import="javax.sound.midi.SysexMessage"%>
<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Chat page</title>
<style type="text/css">
	.initial-text{
		position: absolute;
		top: 10%;
		left: 50%;
		transform: translate(-50%,-10%);
		text-align: center;
		background-image: url("/images/login.png");
	}
	.initial-image{
		position: absolute;
		top: 60%;
		left: 50%;
		transform: translate(-50%,-50%);
		text-align: center;
		width: 70%;
		z-index: 100;
	}
	.white{
		position: absolute;
		top: 90%;
		transform: translate(-50%,-50%);
		text-align: center;
		width: 200%;
		height : 75px;
		z-index: 100;
	}
</style>
<link rel="stylesheet" href="styles/ChatPageStyle.css">
<link rel="stylesheet" href="styles/popupdialog.css">
<link rel="stylesheet" href="styles/popupdiloguserslist.css">
</head>
<%
	if(session.getAttribute("username") == null){
		System.out.println("************************session is NULL ");
		response.sendRedirect("http://localhost:8080/ChatAppTP/Login");
		return;
	}
	User user = (User)session.getAttribute("user");
	

	String userType = (String)session.getAttribute("usertype");
	boolean isModerateur = userType!=null && userType.equals(TypeUser.MODERATEUR.toString());
	boolean isAdministarteur =userType!=null && userType.equals(TypeUser.ADMIN.toString());
%>
<%if(user!=null && user.getUserType().equals(TypeUser.BANNED)) {%>
	<script type="text/javascript">
		alert("You are banned you can not access your account!")
	</script>
<%return;} %>

<body>
	
	<div id="myModal" class="modal">
	 <div class="modal-content">
	   <span class="close">x</span>
	   <ul class="member-list" id="member-list">
	  </ul>
	</div>
	</div>

    <div id="dialog" class="dialog-overlay">
        <button class="close-btn" id="closeBtn">X</button>
        <div class="dialog-box">
            <h2>Create New Group</h2>
            <input type="text" id="groupNameInput" placeholder="Enter Group Name" required>
            <input type="text" id = "groupDescInput" placeholder="Enter Group Description (optional)">
            <button id="creategr">Create</button>
        </div>
    </div>
        
    <div id="dialog-add-user" class="dialog-overlay">
        <button class="close-btn" id="closeBtn">X</button>
        <div class="dialog-box">
            <h2>Add new membere</h2>
            <input type="text" id="groupNameInput" placeholder="Enter Group Name" required>
            <input type="text" id = "groupDescInput" placeholder="Enter Group Description (optional)">
            <button id="creategr">Create</button>
        </div>
    </div>
	<div id="chat-container">
	
    <div id="user-list">
    <div>
		<button class="logoutbtn"><a href="Logout" class = "logoutlink">Log out</a></button>
		<button class="logoutbtn" id="createBtn"><a class = "logoutlink">Add new Group</a></button>
		<%if(isAdministarteur){ %>
			<button  class="logoutbtn" id="createBtn"><a href="Groupchat?type=admin&action=displayUsers" class = "logoutlink">List Users</a></button>
		<%}if(isModerateur){ %>
			<button  class="logoutbtn" id="createBtn"><a href="Groupchat?type=moderateur&action=displayUsers" class = "logoutlink">List Users</a></button>
		<%} %>
    	<h1>Welcome mr <%=session.getAttribute("username") %></h1>    
        <div id="search-bar">
            <input id="anmanyay-search-box" name="q" size="40" type="text" placeholder="Search"/>
            <input id="namanyay-search-btn" value="Go" type="submit"/>
        </div>
        
        <div class = "groups-users">
        	<button class="logoutbtn users" id="users-btn">Users</button>
        	<button class="logoutbtn groups" id="groups-btn">Groups</button>
        </div>
	</div>
    </div>

    <div id="chat-messages-container">
        <div id="navbar">
            <div class="user-item">
            </div>
        </div>
        <div id="chat-messages">
        	<h1 class="initial-text">Join us, community awaits you!</h1>
        	<img src="images/login.png" class="initial-image"></img>
        	<img src="images/Solid_white.png" class="white"></img>
        </div>

        <div id="input-container">
            <input type="text" placeholder="Type your message..." id = "message-input">
            <button id="send-btn">Send</button>
        </div>
    </div>
</div>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		window.currentUserName = '<%=session.getAttribute("username")%>';
		window.userType = '<%=session.getAttribute("usertype")%>';
	</script>
	<script>
    function createDialog(message, type) {
  const dialog = document.createElement('div');
  dialog.classList.add('dialog', type);
  
  const messageElement = document.createElement('p');
  messageElement.textContent = message;
  dialog.appendChild(messageElement);
  
  const closeButton = document.createElement('button');
  closeButton.textContent = 'Close';
  closeButton.addEventListener('click', () => {
    document.body.removeChild(dialog);
  });
  dialog.appendChild(closeButton);

  document.body.appendChild(dialog);
}

function openInfoDialog(str) {
  createDialog(str, 'info');
}

function openWarningDialog(str) {
	createDialog(str, 'warning');}</script>
	<script type="text/javascript" src = "js/usersFetch.js"></script>
	<script type="text/javascript" src = "js/groupsFetch.js"></script>
	<script>
		var modal = document.getElementById("myModal");
		
		
		window.onclick = function(event) {
		  if (event.target == modal) {
		    modal.style.display = "none";
		  }
		}
</script>
	
</body>
</html>