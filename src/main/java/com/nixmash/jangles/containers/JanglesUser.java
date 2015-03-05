package com.nixmash.jangles.containers;

/**
 * Created with IntelliJ IDEA.
 * User: daveburke
 * Date: 10/24/13
 * Time: 2:07 PM
 */
public class JanglesUser implements java.io.Serializable {

    public JanglesUser() {
    }

    public JanglesUser(int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int userId;
    public String firstName;
    public String lastName;
    
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


}


