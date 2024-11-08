package com.lindseylew.librarymanagement.login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.lindseylew.librarymanagement.database.LoginDao;
import com.lindseylew.librarymanagement.service.BookService;
import com.lindseylew.librarymanagement.database.LibraryManagementDatabase;
import com.lindseylew.librarymanagement.service.StudentService;

public class LoginService {
	Scanner sc = new Scanner(System.in);

	public void doLogin() throws ClassNotFoundException, SQLException {
		System.out.println("Username: ");
		String userName = sc.nextLine();
		
		System.out.println("Password: ");
		String password = sc.nextLine();
		
		try (Connection conn = LibraryManagementDatabase.getConnection()) {
			LoginDao loginDao = new LoginDao();
			String userType = loginDao.doLogin(conn,userName, password);
			
			if (userType == null) {
				System.out.println("Invalid user");
				return;
			}
			
			System.out.println("Login successful");
			
			if (userType.equals("admin") ) {
				displayAdminMenu(conn);
			} else {
				//display student menu
			}
		}
	}
	
	public void displayAdminMenu(Connection conn) throws SQLException {
		int choice;
		BookService bookService = new BookService();
		StudentService studentService = new StudentService();
		
		do {
		System.out.println("******************************");
		System.out.println("1. Search for a Book");
		System.out.println("2. Add a new Book");
		System.out.println("3. Upgrade Book Quantity");
		System.out.println("4. Show All Books");
		System.out.println("5. Register Student");
		System.out.println("6. Show all Students");
		System.out.println("7. Exit");
		System.out.println("******************************");
		
		System.out.println("Enter choice number");
		
		choice = sc.nextInt();
		
		switch(choice) {
		case 1:
			searchBook(conn);
			break;
		case 2:
			bookService.addBook(conn);
			break;
		case 3:
			bookService.updateBookQty(conn);
			break;
		case 4:
			bookService.getAllBooks(conn);
			break;
		case 5:
			studentService.addStudent(conn);
			break;
		case 6:
			studentService.getAllStudents(conn);
			break;
		case 7:
			System.out.println("Thank you for using the Library Management System.");
			System.exit(0);
			break;
			default:
				System.out.println("Invalid choice. Please select a vaild choice number.");
		}
		
		} while (choice != 7);
	}
	
	public void displayStudentMenu(Connection conn) throws SQLException {
		int choice;
		BookService bookService = new BookService();
		
		do {
			System.out.println("========================================");
			System.out.println(" 1. Search a Book.");
			System.out.println(" 2. Check out Book.");
			System.out.println(" 3. Check in Book.");
			System.out.println(" 4. Exit From Application.");
			System.out.println("========================================");
			
			System.out.println("Enter choice Number:");
			
			choice = sc.nextInt();
			
			switch (choice) {
			case 1:
				searchBook(conn);
				break;
			case 2:
				bookService.checkOutBook(conn);
				break;
			case 3:
				bookService.checkInBook(conn);
				break;
			case 4:
				System.out.println("Thank you for using the Library Management System");
				System.exit(0);
				break;
			default:
				System.out.println("Please select a vaild option");
			}
		} while (choice != 4);
	}
	
	private void searchBook(Connection conn) throws SQLException {
		
		BookService bookService = new BookService();
		System.out.println("1. Search by Serial Number");
		System.out.println("2. Search by Author");
		
		System.out.println("Enter choice number ");
		
		int choice = sc.nextInt();
		
		switch(choice) {
		case 1:
			bookService.searchBySerialNum(conn);
			break;
		case 2:
			bookService.searchByAuthorName(conn);
			break;
		}
		
	}
}
