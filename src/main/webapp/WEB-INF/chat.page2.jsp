<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Chat page</title>
<link rel="stylesheet" href="styles/ChatPageStyle.css">
</head>
<%
	if(session.getAttribute("username") == null){
		System.out.println("************************session is NULL ");
		response.sendRedirect("http://localhost:8080/ChatAppTP/Login");
		return;
	}

%>

<body>
	<div id="chat-container">
    <div id="user-list">
    	<button class="logoutbtn"><a href="Logout" class = "logoutlink">Log out</a></button>
    	<h1>Welcome mr <%=session.getAttribute("username") %></h1>    
        <div id="search-bar">
            <input id="namanyay-search-box" name="q" size="40" type="text" placeholder="Search"/>
            <input id="namanyay-search-btn" value="Go" type="submit"/>
        </div>
        <!--Her is the users items -->
    </div>

    <div id="chat-messages-container">
        <div id="navbar">
            <div class="user-item">
            </div>
        </div>
        <div id="chat-messages">
            <!-- <div class="message sender">
                <p>Hello, how are you?</p>
            </div>
            <div class="message receiver">
                <p>Hi! I'm good, thanks for asking.</p>
            </div>-->
        </div>

        <div id="input-container">
            <input type="text" placeholder="Type your message..." id = "message-input">
            <button id="send-btn">Send</button>
        </div>
    </div>
</div>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
			function fetchAll(){
		    $.ajax({
		        type: "POST",
		        url: "Chat",
		        data: { action: "getUsers"},
		        dataType: "json",
		        success: function(users) {
		            display(users);
		        }
		    });}
			
			function display(users) {
				//$("#user-list").empty();
				
			    for (var i = 0; i < users.length; i++) {
			        (function() {
			            var userItem = document.createElement('div');
			            userItem.className = 'user-item';
			            userItem.id = 'user-item-' + users[i].idUser;

			            var profileImage = document.createElement('img');
			            profileImage.className = 'profile-image';
			            profileImage.src = "images/profile.jpg";

			            userItem.appendChild(profileImage);

			            var userDetails = document.createElement('div');
			            userDetails.className = 'user-details';

			            var userName = document.createElement('div');
			            userName.className = 'user-name';
			            userName.innerHTML = users[i].userName;

			            userDetails.appendChild(userName);

			            var lastMessage = document.createElement('div');
			            lastMessage.className = 'last-message';

			            userDetails.appendChild(lastMessage);

			            userItem.appendChild(userDetails);

			            var dateMessage = document.createElement('div');
			            dateMessage.className = 'date';
			            userItem.appendChild(dateMessage);
			            $("#user-list").append(userItem);

			            
			            $("#" + userItem.id).click(function() {
			                console.log(userItem.id);
			                //$("#chat-messages").empty();
			                
			                var idS = userItem.id.split('-')[2];
			                
			                var navbar = document.querySelector('#navbar');
			                navbar.innerHTML = "";
			                
			                var userItemN = document.createElement('div');
			                userItemN.className = 'user-item';
			                
			                var userName = document.createElement('div');
			                userName.className = 'user-name';
			                var userId = parseInt(idS)-1;
			                userName.innerHTML = users[userId].userName;
			                
			                var profileImage = document.createElement('img');
			                profileImage.className = 'profile-image';
			                profileImage.src = "images/profile.jpg";
			                
			                userItemN.append(profileImage);
			                userItemN.append(userName);
			                navbar.append(userItemN);
			                
			                
			                $.ajax({
			                    type: "POST",
			                    url: ("Chat"),
			                    data: { action: "getConvs", id: idS },
			                    dataType: "json",
			                    success: function(convs) {
			                        var chatMessages = document.getElementById('chat-messages');
			                        chatMessages.innerHTML = "";
			                        for(var i =0;i<convs.length;i++){
			                        	if(convs[i].sender.userName == '<%=session.getAttribute("username")%>'){
			                            	
			                            	var message = document.createElement('div');
			                            	message.className = 'message sender'
			                            	
			                            	var dateMessage = document.createElement('div');
			                            	dateMessage.innerHTML = (new Date(convs[i].dateEnvoie)).getHours() + ':' + (new Date(convs[i].dateEnvoie)).getMinutes()
			                            	
			                            	var paragraph = document.createElement('p');
			                            	paragraph.innerHTML = convs[i].messageText;
			                            	//console.log(convs[i].messageText);
			                            	message.append(paragraph);
			                            	message.append(dateMessage);
			                            	chatMessages.append(message);
			                        	}else {
			                            	var message = document.createElement('div');
			                            	message.className = 'message receiver'
			                            	
			                            	var dateMessage = document.createElement('div');
			                            	dateMessage.innerHTML = (new Date(convs[i].dateEnvoie)).getHours() + ':' + (new Date(convs[i].dateEnvoie)).getMinutes()
			                            	
			                            	var paragraph = document.createElement('p');
			                            	paragraph.innerHTML = convs[i].messageText;
			                            	message.append(paragraph);
			                            	message.append(dateMessage)
			                            	chatMessages.append(message);
			                        	}
			                        }
			                    }
			                });

			                var chatMessagesContainer = $('#chat-messages');
			                
			                
			                setTimeout(() => {
			                	chatMessagesContainer.scrollTop(chatMessagesContainer.prop('scrollHeight'));
							}, 50);
			                $('#send-btn').off('click').on('click', function() {
			                	
			                	var messageText = $('#message-input').val(); // Use jQuery to get the input value
			                	// Clear the input value
			                	console.log('Message : ' + messageText)
			            	    var currentDate = new Date();
			            	    var localDate = new Date(currentDate.getTime() - currentDate.getTimezoneOffset() * 60000);
			            	    var formattedDate = localDate.toISOString();
			            	    
			            	    //console.log(formattedDate);
			            	    messageText ? $.ajax({
			            	        type: "POST",
			            	        url: "Chat",
			            	        data: { action: "sendMessage", messageText: messageText, dateEnvoie: formattedDate, sender: '<%=session.getAttribute("username")%>' , receiver : idS },
			            	        success: function(response) {
			            	            //console.log(response);
			            	        },
			            	        error: function(error) {
			            	            console.error("Erreur lors de l'envoi du message : " + error);
			            	        }
			            	    }) : console.log("Empty String");
			            	    $('#message-input').val(''); 
			            	    var userItemElement = $('#' + userItem.id);
			            	    if (userItemElement.length) {
			            	        setTimeout(() => {
			            	        	userItemElement.click();
									}, 40);
			            	        
			            	        
			            	        
			            	    }
			            	});
			                
			            });

			        })();
			    }
			}
			fetchAll();
	});
	
	
	</script>
</body>
</html>