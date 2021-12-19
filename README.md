# COMP3005 Project

**Author:**

*Name:* Jonathan.\
*Student ID:* 101161272

## IntelliJ

I chose IntelliJ as my IDE. Open the folder named "project" in IntelliJ. You can open the module setting by right clicking on the folder name and click on "Open Module Settings", or just by simply pressing F4. \
Set the Project SDK in the project settings. I used Java version 13, but you can choose any version.\
To get JDBC working, navigate to the Modules bar and click on the "+" button on the right side and choose "JARs or directories...", and navigate to the .jar file I've attached. \
View the images below for better visualization.\
\
![image](https://user-images.githubusercontent.com/69212511/146687100-87a5ebc4-01ab-4121-adae-3521011dff7c.png)\
![image](https://user-images.githubusercontent.com/69212511/146686823-3919175f-f083-44c6-8d18-16a7bd4f19c9.png)\
![image](https://user-images.githubusercontent.com/69212511/146686862-a49b24d6-08da-4906-b8d3-38a9a2a09757.png)\


## Connecting to PostGreSQL

In the source code, there is a Java class called Connect where you can change the username and password to connect to your postgre. My default username and password is already there.\
The default port is 5432.

## ER and Schema

Both the ER and the Schema diagrams are located in the diagram folder.

## DDL

The DDL folder contains all the sql statements needed for initialization.\
Run DDL.sql first to initialize the tables.\ 
Then DDLInsertion.sql to insert the dummy values. \
deleteTables.sql is to drop all the tables. This sql statement is provided in case if needed.
