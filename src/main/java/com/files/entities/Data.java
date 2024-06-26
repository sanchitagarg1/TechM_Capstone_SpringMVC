package com.files.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table(name = "TOURDATA")
public class Data implements Serializable
{
	
	
	@Id
    @Column(name = "PHONE")
	long phone;

	 @Column(name = "NAME")
	String name;
	 
	 @Column(name = "EMAIL")
	String email;
	 
	 @Column(name = "PASSWORD")
	String password;
	 
	 @Column(name = "ADDRESS")
	String address;
	 
	 @Column(name = "ROLE")
	String role;
	 
	 @Column(name = "IMAGEFILENAME")
	String imageFileName;
	
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Data [name=" + name + ", email=" + email + ", password=" + password + ", address=" + address
				+ ", phone=" + phone + "]";
	}

}
