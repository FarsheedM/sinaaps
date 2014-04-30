package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Blog extends Controller{
	
	public static Result display(){
		
		if(session().containsKey("email"))
			return ok(blog.render(User.find.byId(session().get("email"))));
		else{
			User guest = new User("Guest","dummyEmail","dummyPassword");
			return ok(blog.render(guest));
			
		}
	}

}
