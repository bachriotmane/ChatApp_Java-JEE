

		$(document).ready(function() {
			
			
			
	        function fetchByUserName(){
	        	var keySearch = $('#anmanyay-search-box').val();
	        	$.ajax({
			        type: "POST",
			        url: "Chat",
			        data: { action: "getUsersByUserName",key : keySearch},
			        dataType: "json",
			        success: function(users) {
			            display(users);
			        }
			    });
	        	}
	        
	        
			$('#namanyay-search-btn').click(function (){
				document.getElementById('users-container').innerHTML = ""
				fetchByUserName()
			});
			
			
			
			
			function fetchAll(){
		    $.ajax({
		        type: "POST",
		        url: "Chat",
		        data: { action: "getUsers"},
		        dataType: "json",
		        success: function(users) {
		            display(users);
		        }
		        
		    });
		    ;
		    }
			
			function display(users) {
				var usersContainer = document.createElement('div');
	        	usersContainer.id = "users-container";
	        	
	        	
	        	//var chatMessagesContainer = $('#chat-messages');

			    function clickOnUserItem(userId) {
			        var userItemElement = $('#user-item-' + userId);
			        if (userItemElement.length > 0) {
			            userItemElement.click();
			        }
			    }
	        	
			    for (var i = 0; i < users.length; i++) {
					var curId = i;
			        (function() {
							
			        	
			        	console.log(users[i])
			            var userItem = document.createElement('div');
			            userItem.className = 'user-item';
			            userItem.id = 'user-item-' + users[i].idUser;

			            var profileImage = document.createElement('img');
			            profileImage.className = 'profile-image';
			            profileImage.src = "images/profile.png";
			            
			            var online = document.createElement('img');
			            if(users[i].status == 'ONLINE'){
							online.src = "images/online.png"
							online.width = "20"
						}else {
							online.src = "images/offline.png"
							online.width = "20"
						}
						
			            userItem.appendChild(profileImage);
			            
			            var userDetails = document.createElement('div');
			            userDetails.className = 'user-details';

			            var userName = document.createElement('div');
			            userName.className = 'user-name';
			            var currentUserName = users[i].userName;
			            userName.innerHTML = users[i].userName;

			            userDetails.appendChild(userName);

			            var lastMessage = document.createElement('div');
			            lastMessage.className = 'last-message';

			            userDetails.appendChild(lastMessage);

			            userItem.appendChild(userDetails);
			            usersContainer.append(userItem)

			            var dateMessage = document.createElement('div');
			            dateMessage.className = 'date';
			            userItem.appendChild(dateMessage);
			            userItem.appendChild(online);
			            usersContainer.append(userItem);
			            $("#user-list").append(usersContainer);

			            $("#" + userItem.id).click(function() {
			                console.log(userItem.id + " Clicked");
			                
			                
			                var idS = userItem.id.split('-')[2];
			                
			                
			                var navbar = document.querySelector('#navbar');
			                navbar.innerHTML = ""
			                
			                var userItemN = document.createElement('div');
			                userItemN.className = 'user-item';
			                
			                var userName = document.createElement('div');
			                userName.className = 'user-name';
			                //var userId = parseInt(idS)-1;
			                userName.innerHTML = currentUserName;
			                
			                var profileImage = document.createElement('img');
			                profileImage.className = 'profile-image';
			                profileImage.src = "images/profile.png";
			                
			                
			                userItemN.append(profileImage);
			                userItemN.append(userName);
			                navbar.append(userItemN);
			                
			                
			                $.ajax({
			                    type: "POST",
			                    url: ("Chat"),
			                    data: { action: "getConvs", id: parseInt(idS) },
			                    dataType: "json",
			                    success: function(convs) {
									
			                        var chatMessages = document.getElementById('chat-messages');
			                        chatMessages.innerHTML = ""
			                        for(var i =0;i<convs.length;i++){
										var idR = convs[i].receiver.idUser;
			                        	if(convs[i].sender.userName == window.currentUserName){
			                            	
			                            	var message = document.createElement('div');
			                            	message.className = 'message sender'
			                            	
			                            	var dateMessage = document.createElement('div');
			                            	dateMessage.innerHTML = (new Date(convs[i].dateEnvoie)).getHours() + ':' + (new Date(convs[i].dateEnvoie)).getMinutes()
			                            	
			                            	var paragraph = document.createElement('p');
			                            	paragraph.innerHTML = convs[i].messageText;
			                            	
			                            	message.append(paragraph);
			                            	message.append(dateMessage);
			                            	chatMessages.append(message);
			                            	console.log(idS)
			                        	}else if(convs[i].receiver.idUser === idR){
											
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
			                        //clickOnUserItem(idS);
			                    }
			                    
			                });

			                var chatMessagesContainer = $('#chat-messages');
			                
			                
			                setTimeout(() => {
			                	chatMessagesContainer.scrollTop(chatMessagesContainer.prop('scrollHeight'));
							}, 80);
			                $('#send-btn').off('click').on('click', function() {
			                	if(window.userType === 'BANNED'){
									alert('You can not send Messages you are banned');
									return;
								}
			                	var messageText = $('#message-input').val(); 

			            	    var currentDate = new Date();
			            	    var localDate = new Date(currentDate.getTime() - currentDate.getTimezoneOffset() * 60000);
			            	    var formattedDate = localDate.toISOString();
			            	    
			            	    messageText ? $.ajax({
			            	        type: "POST",
			            	        url: "Chat",
			            	        data: { action: "sendMessage", messageText: messageText, dateEnvoie: formattedDate, sender: window.currentUserName , receiver : idS },
			            	        success: function(response) {},
			            	        error: function(error) {
			            	            console.error("Erreur lors de l'envoi du message : " + error);
			            	        }
			            	    }) : console.log("Empty String");
							
			            	    $('#message-input').val(''); 
			            	    var userItemElement = $('#' + userItem.id);
			            	    if (userItemElement) {
			            	        setTimeout(() => {
			            	        	userItemElement.click();
									}, 50);
			            	    }
			            	    //clickOnUserItem(idS);
			            	});
			                
			            });

			        })();
			    }
			}
			
			 $('#users-btn').click(function(){
		     var usersContainer = document.getElementById('users-container');
		     if (usersContainer) {
		         usersContainer.innerHTML = "";
		         usersContainer.id = "";
		     }
		     fetchAll(); 
		 });
	});