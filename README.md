# App My Auth 🚀

**App My Auth** is a simple **authentication system** built using **JDBC and JSP**. The project includes **Login and Registration pages**, allowing users to authenticate by connecting to a database.

---

## 📌 **Features**
✅ **Database connection using JDBC**  
✅ **User registration functionality**  
✅ **Login and authentication system**  
✅ **JSP (Java Server Pages) for dynamic web pages**  
✅ **Servlets for handling user requests**  

---

## 🛠 **Technologies Used**
- **Java EE (Servlets, JSP)**
- **JDBC (MySQL connection)**
- **Tomcat Server**
- **HTML, CSS, JSP**
- **MySQL (for user data storage)**

---

## 🔧 **How to Run the Project**
### 1️⃣ **Clone the Repository**
```bash
git clone https://github.com/cyberstalk0210/app-my-auth.git
cd app-my-auth
```
###2️⃣ **Set Up the MySQL Database**
Before running the project, create the following database in MySQL:

## SQL ##

```
CREATE DATABASE auth_db;
USE auth_db;
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
```
### 3️⃣ **Configure Database Connection**
Modify the src/main/resources/database.properties file with your MySQL credentials:

```
db.url=jdbc:mysql://localhost:3306/auth_db
db.user=root
db.password=yourpassword
```
###4️⃣ **Run the Project on Tomcat**

1. Install Tomcat Server
2. Open the project in an IDE (IntelliJ IDEA, Eclipse)
3. Run the project 
