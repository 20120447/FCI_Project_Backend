package com.models;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

//import com.mysql.jdbc.Statement;

/**
 * this class contains actions of store ,get and update data in
 *  data store of each service 
 *
 */

public class UserModel 
{

	
	private String name;
	private String email;
	private String pass;
	private Integer id;
	private Double lat;
	private Double lon;
	
	public String getPass(){
		return pass;
	}
	
	public void setPass(String pass){
		this.pass = pass;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	/**
	 *  Add new user function used to store data of new user in data store 
	 * @param name   provided user name 
	 * @param email  provided user email
	 * @param pass   provided user passward
	 * @return   user object 
	 */
	public static UserModel addNewUser(String name, String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into users (`name`,`email`,`password`) VALUES  (?,?,?)";
			// System.out.println(sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, pass);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = email;
				user.pass = pass;
				user.name = name;
				user.lat = 0.0;
				user.lon = 0.0;
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *  login function , this function used to get data of the current user from store data 
	 *  and check if data that entered from user is correct  or not .
	 * @param email  provided  user email 
	 * @param pass    provided user passward
	 * @return      user object
	 */
	
	public static UserModel login(String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `email` = ? and `password` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *updateUserPosition function ,this function used to update position  of user in data store .
	 * @param id    provided  user id 
	 * @param lat   provided Latitude of current user's position
	 * @param lon   provided Longitude of current user's position
	 * @return      boolean object if update is done then return true else return false
	 */
	public static boolean updateUserPosition(Integer id, Double lat, Double lon) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Update users set `lat` = ? , `long` = ? where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * UpdateUserFollowerUser ,this function to store data of new user
	 *  that followed by current user
	 *  
	 * @param id1   provided first user id who want to follow another user
	 * @param id2   provided second user id who will be followed
	 * @return    will return true if find the id of user that want to follow and update the data
	 *  return false if not find this user
	 */
	
	public static boolean updateUserFollower(Integer id1,Integer id2) 
	{
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into follow (`followerID`,`followedID`) VALUES  (?,?)";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id1);
			stmt.setInt(2, id2);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * unfollowUser , this function used to delete and  update data of followers of the user 
	 * @param id_1   provided follower id user
	 * @param id_2   provided followed id user who will be unfollowed 
	 * @return       boolean object true  if find the ids of the both users and update data 
	 * if not find the id of one of them then return false 
	 */
	public static Boolean unfollowUser(Integer ID_1  , Integer ID_2) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			
			
			String sql= " delete FROM follow WHERE  `followerID` = ? and `followedID` = ? ";
			
			PreparedStatement stmt;
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ID_1);
			stmt.setInt(2, ID_2);
			
			stmt.executeUpdate();
			return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
		
		
	}

	/**
	 * getFollower ,this function used to get all data of the followers of current user from data store.

	 * @param id   provided user id 
	 * @return   list of ids of all current followers 
	 */


	public static ArrayList getFollower(Integer id)
	{
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from follow where `followerID` = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			//System.out.println(">>>>> stmt>>>> "+ stmt.toString());
			ResultSet rs = stmt.executeQuery();
			ArrayList <Integer> ids = new ArrayList<Integer>();
			//UserModel user = new UserModel();
			int i=0;
			while(rs.next()){
				ids.add(rs.getInt(2));
				i++;
			}
			return ids;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	

	/**
	 * getUserPosition ,this function used to get last updated position 
	 * of any user in the following list
	 * by select Latitude and Longitude of current user's position
	 * @param id   provided user id 
	 * @return     Latitude of current user's position
	 *   Longitude of current user's position
	 */


	public static Double[] getUserPosition(Integer id)
	{
		Double [] position = new Double[2];
		try {

			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select `lat`,`long` from users where `id` =?";
			//UserModel user = new UserModel();
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				
				position[0] = rs.getDouble("lat");
				position[1] = rs.getDouble("long");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return position;
	}


}
