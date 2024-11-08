package com.lindseylew.librarymanagement.dto;

public class BookingDetails {
	public int id;
	
	public int stdId;
	public int bookId;
	
	public String bookName;
	public String authorName;
	
	public int qty;
	
	public int serialNum;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getStdId() {
		return stdId;
	}
	
	public int getBookId() {
		return bookId;
	}
	
	public void setStdId(int stdId) {
		this.stdId = stdId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public String getBookName() {
		return bookName;
	}
	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getSerialNum() {
		return serialNum;
	}
	
	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
	}
}
