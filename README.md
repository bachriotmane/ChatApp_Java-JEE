# ChatApp JEE

ChatApp JEE is a Java EE-based chat application offering a secure communication platform with various features, including group creation, user management by an administrator, and secure password storage in the database.

## Interfaces

### 1. Login/Register Page

![Login/Register](images/login.png)
![Login/Register](images/register.png)

- Allows users to log in or register.
- Passwords are securely stored in the database.

### 2. Home Interface

![Home](images/home.png)
![Group Conversations](images/converstions.png)

- Displays the list of users and groups.
- Users can create groups for communication.
- If the user is an administrator, they can view the complete list of users.
- The administrator can ban or assign administrator status to system users.

### 3. Create Groups

![Create Groups](images/createGrp.png)

- Allows users to create a new group with a user-friendly interface.

### 4. User List (Admin)

![User List (Admin)](images/list-users-admins.png)

- Accessible only by the administrator.
- Displays the complete list of users with options to ban or grant administrator status.

## Installation Guide

### Prerequisites

- Java Development Kit (JDK)
- Java EE Application Server (e.g., Apache Tomcat)
- Database (e.g., MySQL)
- Eclipse IDE (or another Java EE development environment)

### Installation Steps

1. Clone the GitHub repository: `git clone https://github.com/your-username/ChatApp-JEE.git`
2. Import the project into your Java EE IDE.
3. Configure the database and application server.
4. Run the application on the server.

## Author

[Your Name]

## License

This project is licensed under the [MIT License](LICENSE).

---
