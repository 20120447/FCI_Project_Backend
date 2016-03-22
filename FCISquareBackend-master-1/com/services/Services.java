
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



@Path("/")
public class Services
{
	
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
