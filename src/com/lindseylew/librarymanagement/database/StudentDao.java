package com.lindseylew.librarymanagement.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lindseylew.librarymanagement.dto.Book;
import com.lindseylew.librarymanagement.dto.BookingDetails;

public class StudentDao {

	public boolean getStudentByRegNum(Connection conn, String regNum) throws SQLException {
		String query = "select * from students where reg_num = ?";
		
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, regNum);
			
			try(ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	public int getStudentByRegNum_(Connection conn, String regNum) throws SQLException {
		String query = "select * from students where reg_num = ?";

		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, regNum);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		
		return 0;
	}
	
	public void saveStudent(Connection conn, String studentName, String regNum) throws SQLException {
		String query = "insert into students (std_name, reg_num) values(?, ?)";
		
		try(PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1,  studentName);
			ps.setString(2,  regNum);
			
			int rows = ps.executeUpdate();
			
			if (rows > 0) {
				System.out.println("Student added successfully");
			} else {
				System.out.println("There was an error adding student");
			}
		}
	}
	
	public void getAllStudents(Connection conn) throws SQLException {
		String query = "select * from students";
		
		System.out.println("+------------+--------------------+---------------");
		System.out.println("| Id         | Student Name       | Reg No        |");
		System.out.println("+------------+--------------------+---------------+");
		
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					System.out.printf("| %-10s | %-18s | %-13s | \n", rs.getInt(1), rs.getString(2), rs.getString(3));
					System.out.println("+------------+--------------------+---------------+");
				}
			}
			
		}
	}
	
	public void updateBookQty(Connection conn, Book book) throws SQLException {
		String query = "update books set qty = ? where sr_no = ?";
				
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, book.getBookQty());
			ps.setInt(2,  book.getSerialNum());
			
			int rows = ps.executeUpdate();
			
			if (rows > 0) {
				System.out.println("Book updated sucessfully");
			} else {
				System.out.println("Failed to update book");
			}
		}
	}
	
	public void saveBookingDetails(Connection conn, int stdId, int bookId, int qty) throws SQLException {
		String query = "insert into booking_details(std_id, book_id, qty) values(?, ?, ?)";
		
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, stdId);
			ps.setInt(2, bookId);
			ps.setInt(3, qty);
			
			int rows = ps.executeUpdate();
			
			if (rows > 0) {
				System.out.println("Booking details added");
			} else {
				System.out.println("Failed to add booking details");
			}
		}
	}
	
	public List<BookingDetails> getBookDetailsId(Connection conn, int stdId) throws SQLException {
		String query = "select * from booking_details bd" + "inner join books b on b.id = bd.book_id" + "where bd,std_id = ?";
		
		List<BookingDetails> bookingDetails = new ArrayList<>();
		
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, stdId);
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				BookingDetails bookingDetail = new BookingDetails();
				bookingDetail.setAuthorName(resultSet.getString("author_name"));
				bookingDetail.setBookId(resultSet.getInt("book_id"));
				bookingDetail.setBookName(resultSet.getString("name"));
				bookingDetail.setQty(resultSet.getInt("qty"));
				bookingDetail.setStdId (resultSet.getInt("std_id"));
				bookingDetail.setSerialNum(resultSet.getInt("sr_no"));
				bookingDetail.setId(resultSet.getInt("id"));
				
				bookingDetails.add(bookingDetail);
			}
		}
		
		return bookingDetails;
	}
	
	public void deleteBookingDetails(Connection conn, int id) throws SQLException {
		String query = "delete from booking_details where id = ?";
		
		try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		}
	}
}
