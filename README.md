# PetStoreManagement
## Description
PetStoreManagement is an application for managing products, services, and orders for a pet store. The application provides a user-friendly interface to manage and track information related to the store's offerings.

## System Requirements
- Java 11 or higher
- MySQL 5.7 or higher
- JavaFX (necessary for the user interface)
- Maven (for project management and dependencies)

## Installation
- Open your terminal or command prompt.
- Clone the eSavior project repository using Git: git clone https://github.com/thanh1149/PetStoreManagement.git
- Navigate to the project folder.

## Setup database
- Open MySQL Workbench.
- Connect to your MySQL server.
- Create a new database called petstoremanagement.
- Execute the SQL script to create the tables. You can find the SQL script in the database directory of the project.
- Open the SQL script file in MySQL Workbench and execute it to create the necessary tables and relationships.

## Usage
After setting up the database, run the application using your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
Use the application to manage products, services, and orders in your pet store.

### Note
- You should insert some example Category,Status,Role,Staff first.
- Add all necessary dependencies to run.
- Go to LoginService, use the comment method first to login ( can't login first time because bcrypt ) then you can add Staff and use the login method using Bcrypt
