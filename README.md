
# Java Virtual Pet Quiz

The Java Virtual Pet Quiz is an interactive  Java-based quiz game designed to engage users in a fun and educational way. Featuring a graphical user interface built with Swing, this application allows players to answer a series of questions across multiple chapters. Users can personalize their experience by logging in with a username, and the game retrieves questions from a MySQL database, ensuring a dynamic and varied gameplay experience.

Target Users:
- Students looking to reinforce their knowledge in various subjects through quizzes.
- Educators who want to create a gamified learning environment.
- Java developers interested in exploring GUI development, database connectivity and game design principles.
- Casual gamers seeking a simple yet engaging way to test their knowledge.


## Prerequisites
1. Software Requirements
- IntelliJ IDEA 
- Java Development Kit (JDK) version 8 or higher
- MySQL Server (version 5.7 or higher)
- MySQL Workbench for database management 

2. Files
- The project zip file containing .java files and additional resources (such as images).
- A text file (users.txt) for reading usernames. Make sure to place this in the project directory.


## Set Up Instructions
**Step 1: Extract the Project Zip File**
- Download and extract the project zip file to a location on your computer.

**Step 2: Open the Project in IntelliJ IDEA**

- Open IntelliJ IDEA
- Go to File > Open...
- Navigate to the extracted project folder and select it. Click OK.
(OR COPY AND PASTE THE CODE PROVIDED IN THE ZIP FILE INTO YOUR IntelliJ IDEA one at a time)


**Step 3: Configure the SDK**
- If IntelliJ prompts you to set up an SDK:
    *  Go to File > Project Structure > SDKs
    * Click Add SDK > JDK and choose your instaled JDK path.
- Apply changes and close the setting windowns.





## Database Set Up
**Step 1: Install MySQL**
- Download and install MySQL Server and Workbench if not already installed. 

** Important Note on MySQL Setup

When setting up MySQL database, you will need to create a user account and set a password. Please make sure to remember this password, as it will be required later to connect the Java application to the MySQL database. If you forget the password, you may need to reset it, which can complicate the setup process.

**Step 2: Create the Database**
- Open MySQL Workbench and connect to your MySQL server.
- Run the following SQL script to create the table 

```
CREATE TABLE users (
    username VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    chapter1_score INT,
    chapter2_score INT,
    chapter3_score INT,
    chapter4_score INT,
    chapter5_score INT
);
```
Insert sample user data to get started (Or you can made up one your own)
```
INSERT INTO users (username, password, chapter1_score, chapter2_score, chapter3_score, chapter4_score, chapter5_score)
VALUES
('ABC', 'ABC', 10, 10, 10, 10, 9),
('banana', '12345', 8, 0, 0, 0, 0),
('blue', 'blue', 10, 0, 0, 0, 0),
('bob', '123456', 0, 0, 0, 0, 0),
('BOO', 'BOO', 9, 0, 0, 0, 0),
('BROO', 'NO', 0, 0, 0, 0, 0),
('BROWN', 'lulu', 0, 0, 0, 0, 0),
('BYE', 'BYE', 2, 0, 0, 0, 0),
('CAT', 'BYE', 0, 0, 0, 0, 0),
('CHOCOLATE', 'SWEET', 0, 0, 0, 0, 0),
('COOKED', 'COOKED', 10, 10, 10, 0, 0),
('Cookie', 'a665a4592...', 0, 0, 0, 0, 0);
```
## Setting Up JDBC (Java Database Connectivity)

To enable the application to connect to the MySQL database, you'll need to download the MySQL JDBC driver and set it up as a library in your project.

**Step 1: Download the MySQL JDBC Driver**
- Visit the official MySQL Connector/J download page.
- Download the latest stable version of the MySQL Connector/J for your platform.
- Extract the downloaded file, and locate the .jar file (e.g., mysql-connector-j-9.1.0.jar).

**Step 2: Create the lib Folder**
- In your project's root directory, create a new folder named lib.
- Copy and paste the .jar file (e.g., mysql-connector-j-9.1.0.jar) into the lib folder.

**Step 3: Add the JDBC Driver to IntelliJ IDEA**
- Open IntelliJ IDEA.
- Go to File > Project Structure > Libraries.
- Click the + icon and choose Java.
- Navigate to the lib folder in your project, select the .jar file, and click OK.
- Apply the changes and close the settings window.

## Handling Images
**Step 1:** Create a new folder named "Images" in your project's root directory.

**Step 2:** Download or Copy all the images files provided in the project zip file into the "Images" folder.**

**Step 3:** In your Java code, change the image paths to match the location of the images in the "images" folder. The path may be different depending on where you store the images on your device.








## Creating users.txt file
Create a text file named users.txt in the root directory of your project.

The application will read usernames from this file to allow users to log in and track progress.


## Running Application + Project Structure 
Once you've completed the steps above, you're ready to run the Java Virtual Pet Quiz! 

Below is the project structure to help you run the code

```
Quizz/
│
├── .idea/                             
│
├── lib/                              
│   └── mysql-connector-j-9.1.0.jar   
│
├── out/                               
├── src/                               
│   ├── images/                       
│   ├── chapter1.java                 
│   ├── chapter2.java                 
│   ├── chapter3.java                 
│   ├── chapter4.java                 
│   ├── chapter5.java                 
│   ├── congrats1.java                
│   ├── congrats2.java                
│   ├── congrats3.java                
│   ├── congrats4.java                
│   ├── congrats5.java                        
│   ├── GameOver1.java                
│   ├── GameOver2.java                
│   ├── GameOver3.java                
│   ├── GameOver4.java                
│   ├── GameOver5.java                
│   ├── GameStart1.java               
│   ├── GameStart2.java                
│   ├── GameStart3.java               
│   ├── GameStart4.java               
│   ├── GameStart5.java              
│   ├── HomePage.java     
│   ├── JDBC.java     
│   ├── login.java
│   ├── signup.java                  
│   └── users.txt
│ 
│
└──                       

```
