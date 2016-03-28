
package com.services;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.models.UserModel;

/**
 * This class contains rest services and action functions for web applications 
 * version 1.0
 * @author Mohamed samir ,Hadeer Tarek , Nesma mahmoud , Nada Nashaat , Fatma Abdelaty
 * since 17/3/2016
 */



@Path("/")
public class Services
{
	

	/**
	 * Signup rest service, this service will be called to add new user 
	 * @param name 
	 *            provide user name 
	 * @param email  
	 *           provide user email
	 * @param pass   
	 *          provide user password
	 * @return  status json
	 */
	
	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */

	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name, @FormParam("email") String email,
			@FormParam("pass") String pass) 
	{
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}

	/**
	 * login rest service ,this service called to make user to interact with the system
	 * @param email  
	 *         provided user email
	 * @param pass  
	 *         provided user passward
	 * @return  user in  json format
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email, @FormParam("pass") String pass)
	{
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	
	/**
	 * UpdatePosition service ,this service called to make User can update 
	 * his/her position in the system to be his/her current position.
	 * @param id   
	 *         provide user id
	 * @param lat  
	 *        provided Latitude of current user's position
	 * @param lon  
	 *        provide Longitude of current user's position
	 * @return  status json
	 */

	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id, @FormParam("lat") String lat,
			@FormParam("long") String lon) 
	{
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat),
				Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	// unfollow user
	/**
	 * unfollowUser rest service ,this service is called to make User can
	 *  un-follow another user in the system
	 *   
	 * @param followerID    
	 *               provide follower id user  
	 * @param followedID    
	 *               provided followed id user 
	 * @return  status json 
	 */
	@POST
	@Path("/unfollowUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String unfollowUser(@FormParam("id_1")Integer followerID , @FormParam("id_2") Integer followedID )
	{
		Boolean status = UserModel.unfollowUser(followerID, followedID);
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	
	//make follow 
	/**
	 * FollowUser rest service ,this service is called to make the  User 
	 * can follow another user in the system
	 * 
	 * @param id1   
	 *         provided first user id 
	 * @param id2   
	 *           provided second user id 
	 * @return   status json 
	 */
	@POST
	@Path("/followUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String followUser(@FormParam("id1") String id1,@FormParam("id2") String id2) 
	{
		Boolean status = UserModel.updateUserFollower(Integer.parseInt(id1),Integer.parseInt(id2));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	

//////////follow
	
/**
 * getFollower rest service , by calling this service User
 *  can list all his/her current followers in the system
 *  
 * @param id   
 *         provided user id 
 * @return status json
 */
    @POST
	@Path("/getFollower")
	@Produces(MediaType.TEXT_PLAIN)
	public String getFollower(@FormParam("id") Integer id)
    {
		ArrayList <Integer> followID = UserModel.getFollower(id);
		System.out.println("followIDs >>> " + followID.get(0));
		JSONObject json = new JSONObject();
		json.put("id", followID);
		return json.toJSONString();
	}
	

/**
 * GetLastPosition rest service  ,by calling this sercvice User
 *  can see the last updated position of any user in the following list
 * @param id  
 *          provided  user id
 * @return  status json 
 */
	@POST
	@Path("/GetLastPosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetLastPosition(@FormParam("id") String id)
    {
		Double[] position = UserModel.getUserPosition(Integer.parseInt(id));
		UserModel user = new UserModel();
		JSONObject json = new JSONObject();
		//json.put("id",user.getId());
		json.put("lat", position[0]);
		json.put("long", position[1]);
		
		return json.toJSONString();
	}


	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
	
}
