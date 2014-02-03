package com.nixmash.jangles.api;

import com.nixmash.jangles.business.*;
import com.nixmash.jangles.containers.*;

public class JangleWebApi {

	public String getHello(String _user)
	{
		JangleUser _jangleUser = JangleUsers.GetJangleUser(1);
		return  _jangleUser.FirstName + " from the database says Hello to " + _user;
	}
}
