package com.lindseylew.librarymanagement.service;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lindseylew.librarymanagement.database.BookDao;
import com.lindseylew.librarymanagement.database.StudentDao;
import com.lindseylew.librarymanagement.dto.Book;
import com.lindseylew.librarymanagement.dto.BookingDetails;

public class BookService {
	Scanner sc = new Scanner(System.in);

	public void searchBySerialNum(Connection conn) throws SQLException {
		System.out.println("Enter Serial Number: ");
		int srNum = sc.nextInt();
		
		BookDao dao = new BookDao();
		Book book = dao.getBookBySerialNum(conn, srNum);
		
		if (book != null) {
			System.out.println("***Book Details***");
			System.out.println("Serial Number: " + book.getSerialNum() + " Book Name: " + book.getBookName() + " Author Name: " + book.getAuthorName());
		} else {
			System.out.println("Book with serial number, " + srNum + " ,not found.");
		}
	}
	
	public void searchByAuthorName(Connection conn) throws SQLException {
		System.out.println("Enter Author Name: ");
		
		String authorName = sc.nextLine();
		
		BookDao dao = new BookDao();
		Book book = dao.getBookByAuthorName(conn,authorName);
		
		if (book != null) {
			System.out.println("***Book Details***");
			System.out.println("Serial Number: " + book.getSerialNum() + " Book Name: " + book.getBookName() + " Author Name: " + book.getAuthorName());
		} else {
			System.out.println("Book with author name, " + authorName + " , not found.");
		}
	}
	
	public void addBook(Connection conn) throws SQLException {
		System.out.println("Enter book serial number:");
		int serialNum = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Enter Book Name:");
		String bookName = sc.nextLine();
		
		System.out.println("Enter Author Name:");
		String authorName = sc.nextLine();
		
		System.out.println("Enter quantity:");
		int qty = sc.nextInt();
		
		BookDao dao = new BookDao();
		Book book = dao.getBookBySerialNumOrAuthor(conn, authorName, serialNum);
		
		if (book != null) {
			System.out.println("Book already exists.");
			return;
		}
		Book input = new Book();
		input.setAuthorName(authorName);
		input.setBookName(bookName);
		input.setBookQty(qty);
		input.setSerialNum(serialNum);
		
		dao.saveBook(conn, input);
	}
	
	public void getAllBooks(Connection conn) throws SQLException{
		BookDao dao = new BookDao();
		List<Book> books = dao.getAllBooks(conn);
		
		System.out.println("+------------+--------------------+------------------+------------+");
		System.out.println("| Book Sr No | Name               | Auth Name        | Quantity   |");
		System.out.println("+------------+--------------------+------------------+------------+");
		
		for (Book book : books) {
			
			System.out.printf("| %-10s | %-18s | %-16s | %-10s | \n", book.getSerialNum(), book.getBookName(), book.getAuthorName(), book.getBookQty());
			System.out.println("+------------+--------------------+------------------+------------+");
		}
	}
		
		public void updateBookQty(Connection conn) throws SQLException {
			System.out.println("Enter book serial number:");
			int serialNum = sc.nextInt();
			
			BookDao dao = new BookDao();
			Book book = dao.getBookBySerialNum(conn, serialNum);
			
			if (book == null) {
				System.out.println("Book not avialble");
				return;
			}
			
			System.out.println("Enter number of books to be added:");
			int qty = sc.nextInt();
			
			Book input = new Book();
			input.setBookQty(book.getBookQty() + qty);
			input.setSerialNum(serialNum);
			
			dao.updateBookQty(conn, input);
		}
		
		public void checkOutBook(Connection conn) throws SQLException {
			StudentDao dao = new StudentDao();
			
			System.out.println("Enter Registration Number:");
			String regNum = sc.nextLine();
			
			boolean doesExist = dao.getStudentByRegNum(conn, regNum);
			
			if (!doesExist) {
				System.out.println("Student is not registered");
				return;
			}
			
			getAllBooks(conn);
			
			System.out.println("Enter Serial Number of the Book to be Checked Out:");
			int serialNum = sc.nextInt();
			
			BookDao bookDao = new BookDao();
			Book book = bookDao.getBookBySerialNum(conn, serialNum);
			
			if (book == null) {
				System.out.println("Book is not available");
				return;
			}
			
			book.setBookQty(book.getBookQty() - 1);
			
			int id = dao.getStudentByRegNum_(conn, regNum);
			
			dao.saveBookingDetails(conn, id, book.getId(), 1);
			bookDao.updateBookQty(conn, book);
		}
		
		public void checkInBook(Connection conn) throws SQLException {
			StudentDao dao = new StudentDao();
			
			System.out.println("Enter Registration Number:");
			String regNum = sc.nextLine();
			
			boolean doesExist = dao.getStudentByRegNum(conn, regNum);
			
			if (!doesExist) {
				System.out.println("Student is not registered.");
				return;
			}
			
			int id = dao.getStudentByRegNum_(conn, regNum);
			List<BookingDetails> bookingDetails = dao.getBookDetailsId(conn, id);
			
			bookingDetails.stream().forEach(b -> System.out.println(b.serialNum + "\t\t\t" + b.bookName + "\t\t\t" + b.authorName));
			
			System.out.println("Enter Serial Number of the book to be checked in");
			int serialNum = sc.nextInt();
			
			BookingDetails filterDetails = bookingDetails.stream().filter(b -> b.getSerialNum() == serialNum).findAny().orElse(null);
			
			BookDao bookDao = new BookDao();
			Book book = bookDao.getBookBySerialNum(conn, serialNum);
			book.setBookQty(book.getBookQty() + 1);

			bookDao.updateBookQty(conn, book);
			dao.deleteBookingDetails(conn, filterDetails.getId());
	}

}