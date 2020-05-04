package myJavaHomeWork;

import java.util.*;

public class Building {
	private double area;
	private String address;
	private int rooms;
	private Vector<String> names;
	
	
	
	public void setArea(double area) {
		this.area= area;
	}
	public double getArea() {
		return this.area;
	}
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setRooms(int roomNum){
	
		this.rooms = roomNum;
	}
	public int getRooms(){
		return this.rooms;
	}
	

}
