
	
$(document).ready(function(){
	
	function fetchAll(){
		    $.ajax({
		        type: "POST",
		        url: "Groupchat",
		        data: { action: "getGroups"},
		        dataType: "json",
		        success: function(groups) {
		            if(document.getElementById('users-container')){
						document.getElementById('users-container').innerHTML = "";
					}
		            display(groups);
		        }
		        
		    });
		    }
			
			function display(users) {
				var usersContainer = document.createElement('div');
	        	usersContainer.id = "users-container";
	        	
	        	var chatMessagesContainer = $('#chat-messages');

			    function clickOnGroupItem(groupId) {
			        var groupItemElement = $('#user-item-' + groupId);
			        if (groupItemElement.length > 0) {
			            groupItemElement.click();
			        }
			    }
			    
			    for (var i = 0; i < users.length; i++) {
			        (function() {
			            var userItem = document.createElement('div');
			            userItem.className = 'user-item';
			            userItem.id = 'user-item-' + users[i].idGroup;

			            var profileImage = document.createElement('img');
			            profileImage.className = 'profile-image';
			            profileImage.src = "images/profile.png";

			            userItem.appendChild(profileImage);

			            var userDetails = document.createElement('div');
			            userDetails.className = 'user-details';

			            var userName = document.createElement('div');
			            userName.className = 'user-name';
			            userName.innerHTML = users[i].grouName;

			            userDetails.appendChild(userName);

			            var lastMessage = document.createElement('div');
			            lastMessage.className = 'last-message';

			            userDetails.appendChild(lastMessage);

			            userItem.appendChild(userDetails);
			            usersContainer.append(userItem)

			            var dateMessage = document.createElement('div');
			            dateMessage.className = 'date';
			            userItem.appendChild(dateMessage);
			            usersContainer.append(userItem);
			            $("#user-list").append(usersContainer);
			            var myGroupName = users[i].grouName;
			            $("#" + userItem.id).click(function() {
			                console.log(userItem.id + " Clicked");
			                
			                var idS = userItem.id.split('-')[2];
			                
			                var navbar = document.querySelector('#navbar');
			                navbar.innerHTML = ""
			                
			                var userItemN = document.createElement('div');
			                userItemN.className = 'user-item';
			                
			                var userName = document.createElement('div');
			                userName.className = 'user-name';
			                var userId = parseInt(idS)-1;
			                userName.innerHTML = myGroupName;
			                
			                
			                
			                var profileImage = document.createElement('img');
			                profileImage.className = 'profile-image';
			                profileImage.src = "images/profile.png";
			                
			                var addUserBtn = document.createElement('button');
			                addUserBtn.textContent = "Users"
			                addUserBtn.id = "add-user-btn"
			                
			                userItemN.append(profileImage);
			                userItemN.append(userName);
			                userItemN.append(addUserBtn);
			                navbar.append(userItemN);
			                
			                
			                
			                $.ajax({
			                    type: "POST",
			                    url: ("Groupchat"),
			                    data: { action: "getConvs", id: parseInt(idS) },
			                    dataType: "json",
			                    success: function(convs) {
			                        var chatMessages = document.getElementById('chat-messages');
			                        chatMessages.innerHTML = ""
			                        for(var i =0;i<convs.length;i++){
			                        	if(convs[i].sender.userName === window.currentUserName){
			                            	var message = document.createElement('div');
			                            	message.className = 'message sender'
			                            	
			                            	var dateMessage = document.createElement('div');
			                            	dateMessage.innerHTML = (new Date(convs[i].dateEnvoie)).getHours() + ':' + (new Date(convs[i].dateEnvoie)).getMinutes()
			                            	dateMessage.id = "date-send"
			                            	var paragraph = document.createElement('p');
			                            	paragraph.innerHTML = convs[i].messageText;
			                            	
			                            	message.append(paragraph);
			                            	message.append(dateMessage);
			                            	chatMessages.append(message);
			                        	}else {
			                            	var message = document.createElement('div');
			                            	message.className = 'message receiver'
			                            	
			                            	var senderName = document.createElement('div');
			                            	senderName.innerHTML = convs[i].sender.userName;
			                            	
			                            	var dateMessage = document.createElement('div');
			                            	dateMessage.innerHTML = (new Date(convs[i].dateEnvoie)).getHours() + ':' + (new Date(convs[i].dateEnvoie)).getMinutes()
			                            	
			                            	var paragraph = document.createElement('p');
			                            	paragraph.innerHTML = convs[i].messageText;
			                            	senderName.id = "sender-name"
			                            	dateMessage.id = "date-receive"
			                            	message.append(senderName);
			                            	message.append(paragraph);
			                            	message.append(dateMessage)
			                            	chatMessages.append(message);
			                        	}
			                        }
			                        //clickOnGroupItem(idS);
			                    }
			                    
			                });
			                
			                var modal = document.getElementById("myModal");
							var span = document.getElementsByClassName("close")[0];
							
							
							$('#add-user-btn').click(function(){
								var form = document.createElement("form");
								form.method = "post";
								form.action = "http://localhost:8080/ChatAppTP/Groupchat?action=memebresList&id=" + idS;
								document.body.appendChild(form);
								form.submit();
							})
							
							span.onclick = function() {
		  						modal.style.display = "none";
							}
							
			                
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
			            	        url: "Groupchat",
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
			            	    //clickOnGroupItem(idS);
			            	});
			                
			            });

			        })();
			    }
			}
	
	
	$('#groups-btn').click(function(){
			if(document.getElementById('users-container')){
				console.log("group-btn clicked")
				document.getElementById('users-container').innerHTML = "";
				document.getElementById('users-container').id= "";
			}
			fetchAll();
		})
		
		$('#createBtn').click(function(){
			if(window.userType === 'BANNED'){
				alert('You are banned you can not create a new group');
				//openWarningDialog('You are banned you can not create a new group')
				return;
			}
			document.getElementById("dialog").classList.add("active");
            document.querySelector(".dialog-box").classList.add("active");
            console.log("Add group")
            $('#creategr').click(function(){
				console.log("Want to create a group");
				var groupName = $('#groupNameInput').val();
				var groupDesc = $('#groupDescInput').val();
				
				groupName && groupDesc ? $.ajax({
		        type: "POST",
		        url: "Groupchat",
		        data: { action: "createNewGroup", groupName : groupName, groupDesc: groupDesc, groupAdmin : window.currentUserName,},
		        dataType: "json",
		        success: function(response) {
		            
		        }
		        
		    }) : console.log("Empty group fileds");
		    
				document.getElementById("dialog").classList.remove("active");
            	document.querySelector(".dialog-box").classList.remove("active");
				
			})
		})	
		
		$('#closeBtn').click(function(){
			document.getElementById("dialog").classList.remove("active");
            document.querySelector(".dialog-box").classList.remove("active");
		})		
});


		
		