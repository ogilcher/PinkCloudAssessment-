# PinkCloudAssessment-
Assessment for PinkCloud submission 

How to run the Inventory Management System:
  Following the Directory: InventoryManagementSystem/src/Driver.java
  Run the Driver
  To begin, you must copy/paste an exact directory to the Inventory.txt file when running on an IDE, or use the provided directory when running locally
  Next, you must press Load for the Application to read from the txt values

  To add items: You must provide an itemID (Must be all integers), a name (Must be a string), a quantity (Must be all integers), and a price (Must be formatted as: 2.75, or some other case. NOT $2.75)
  Follow the directions given through named buttons within the GUI in order to delete, display, and enter values.
  In order to Update entries, you must provide a valid Item ID and a new quantity.

  To Finish, press Save, and allow the Application to override the existing Inventory text file.

How to run the Error Handling and Logging System:
  Following the Directory: ErrorHandlingAndLogging/src/Main
  Run the Driver

  Note: You MUST add Log4j as a dependency to the Gradle Build, or however you will build the project.

How to run the Database Interaction System:
  Following the Directory: DatabaseInteraction/src/DatabaseExample
  Run the Driver

  Note: You MUST give the program a MySQL database. I have given a quick snippet below that you can use.

  CREATE TABLE users (
      user_id INT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(50) NOT NULL,
      email VARCHAR(100) NOT NULL
  );
  
  CREATE TABLE books (
      book_id INT AUTO_INCREMENT PRIMARY KEY,
      title VARCHAR(100) NOT NULL,
      author VARCHAR(100) NOT NULL
  );
  
  CREATE TABLE user_books (
      user_id INT,
      book_id INT,
      FOREIGN KEY (user_id) REFERENCES users(user_id),
      FOREIGN KEY (book_id) REFERENCES books(book_id),
      PRIMARY KEY (user_id, book_id)
  );

**With both the Database Interaction System and Error Handling and Logging System I did not have the local resources to be able to make the use seamless. So there is some input required**
