# Lost & Found System

A simple campus Lost & Found system using **Java RMI**, **Swing GUI**, and **MySQL**.  
Users can report lost/found items and search for found items via a modern desktop interface.

---

## Features

- Report lost items
- Report found items
- Search for found items
- Modern Java Swing GUI
- Data stored in MySQL database
- Java RMI-based client-server architecture

---

## Folder Structure

```
LostFoundSystem/
├── client/
│   ├── LostAndFoundClient.java
│   └── LostAndFoundGUI.java
├── interfaces/
│   └── LostAndFoundInterface.java
├── model/
│   └── Item.java
├── server/
│   ├── DBConnection.java
│   └── LostAndFoundServer.java
├── lib/
│   └── mysql-connector-j-9.3.0.jar
├── build.bat
├── run_server.bat
├── run_client.bat
```

---

## Database Setup

- **Database name:** `lostfounddb`
- **Tables:** `lost_items`, `found_items`

### SQL to create database and tables:

```sql
CREATE DATABASE lostfounddb;
USE lostfounddb;

CREATE TABLE lost_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    lost_by_name VARCHAR(100),
    phone VARCHAR(15),
    item_name VARCHAR(100),
    place VARCHAR(100),
    date DATE,
    description TEXT
);

CREATE TABLE found_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    found_by_name VARCHAR(100),
    phone VARCHAR(15),
    item_name VARCHAR(100),
    place VARCHAR(100),
    date DATE,
    description TEXT
);
```

---

## Database Connectivity

The database connection settings are in:

```
server/DBConnection.java
```

**Current example:**
```java
public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/lostfounddb",
        "root",         // <- your MySQL username
        "Mysql@123"     // <- your MySQL password
    );
}
```

**If you want to run this project on your own machine:**
- Make sure MySQL is installed and running.
- Create the database and tables as described above.
- Change `"root"` and `"Mysql@123"` in `DBConnection.java` to match your MySQL username and password.
- If your MySQL is not on `localhost` or uses a different port, update the URL accordingly:
  ```
  "jdbc:mysql://<host>:<port>/lostfounddb"
  ```
- Save the file and recompile the project before running.

---

## How to Run

### Using Batch Files (Recommended)

1. **Compile all Java files:**  
   Double-click `build.bat`

2. **Start the RMI server:**  
   Double-click `run_server.bat`  
   (Keep this window open!)

3. **Start the GUI client:**  
   Double-click `run_client.bat`  
   (You can run this multiple times for multiple clients.)

### Or Manually

1. **Compile:**
   ```
  javac -cp .;lib\mysql-connector-j-9.3.0.jar client\*.java interfaces\*.java model\*.java server\*.java

   ```
2. **Run the server:**
   ```
   java -cp .;lib\mysql-connector-j-9.3.0.jar server.LostAndFoundServer
   ```
3. **Run the client:**
   ```
   java -cp .;lib\mysql-connector-j-9.3.0.jar client.LostAndFoundGUI
   ```

### For Unix/Mac Users

Instead of using the `.bat` files, open a terminal in the project folder and run:

1. **Compile:**
   ```
   javac -cp .:lib/mysql-connector-j-9.3.0.jar */*.java
   ```
2. **Run the server:**
   ```
   java -cp .:lib/mysql-connector-j-9.3.0.jar server.LostAndFoundServer
   ```
3. **Run the client:**
   ```
   java -cp .:lib/mysql-connector-j-9.3.0.jar client.LostAndFoundGUI
   ```

> **Note:**  
> - The provided `.bat` files are for Windows users only.  
> - Unix/Mac users should use the manual commands shown below.
> - Both options are supported!

---

## Usage

- **Report Lost/Found:**  
  Click the respective button and fill out the form.

- **Search Item:**  
  Click "Search Item" and enter the item name or keyword.

---

## Troubleshooting

- **ClassNotFoundException:**  
  Make sure you compile from the root folder and use the correct classpath.

- **Cannot connect to MySQL:**  
  Check your DB credentials in `DBConnection.java` and that MySQL is running.

- **RMI connection error:**  
  Ensure the server is running before starting the client.

- **Batch file closes instantly:**  
  Open a command prompt, run the `.bat` file, and read the error message.

---
