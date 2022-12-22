package com.harish.Inventory;

public class UserModel {

	int userId;
	boolean isAdmin;
	String userName;
	String userPhone;
	String userPassword;

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public UserModel(int userId, boolean isAdmin, String userName, String userPhone, String userPassword) {
		super();
		this.userId = userId;
		this.isAdmin = isAdmin;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userPassword = userPassword;
	}
	
	
	
}
