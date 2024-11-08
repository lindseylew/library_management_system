package com.lindseylew.librarymanagement.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.lindseylew.librarymanagement.database.BookDao;
import com.lindseylew.librarymanagement.database.StudentDao;
import com.lindseylew.librarymanagement.dto.Book;

public class StudentService {
	Scanner sc = new Scanner(System.in);
	
	public void searchBySerialNum(Connection conn) throws SQLException {
		int serialNum = sc.nextInt();
		
		BookDao dao = new BookDao();
		Book book = dao.getBookBySerialNum(conn, serialNum);
		
		if (book != null) {
			System.out.println("=== Book Details ===");
			System.out.println("Serial Number: " + book.getSerialNum() + " Book Name: " +
			 book.getBookName() + " Author Name: " + book.getAuthorName());
		} else {
			System.out.println("Book with Serial Number: " + serialNum + "not found");
		}
	}
	
	public void addStudent(Connection conn) throws SQLException {
		System.out.println("Enter Student Name: ");
		String studentName = sc.nextLine();
		
		System.out.println("Enter registrarion Number:");
		String regNum = sc.nextLine();
		
		StudentDao dao = new StudentDao();
		boolean doesStudentExist = dao.getStudentByRegNum(conn, regNum);
		
		if (doesStudentExist) {
			System.out.println("Student exists in database.");
			return;
		}
		
		dao.saveStudent(conn, studentName, regNum);
	}
	
	public void getAllStudents(Connection conn) throws SQLException {
		StudentDao dao = new StudentDao();
		dao.getAllStudents(conn);
	}
}
