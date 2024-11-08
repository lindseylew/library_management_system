package com.lindseylew.librarymanagement;

import java.sql.SQLException;

import com.lindseylew.librarymanagement.login.LoginService;

public class LibraryManagementSystem {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println("************* Welcome to the Library Manager *************");
		
		LoginService loginService = new LoginService(); 
		loginService.doLogin();
	}
}
