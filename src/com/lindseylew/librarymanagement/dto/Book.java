package com.lindseylew.librarymanagement.dto;

public class Book {
	private int id;
	private int serialNum;
	private String bookName;
	private String authorName;
	private int bookQty;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getSerialNum() {
		return serialNum;
	}
	
	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
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
	
	public int getBookQty() {
		return bookQty;
	}
	
	public void setBookQty(int bookQty) {
		this.bookQty = bookQty;
	}

}
