package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Books extends Controller{
	
	public static Result display(){
		
		if(session().containsKey("email"))
			return ok(books.render(User.find.byId(session().get("email"))));
		else{
			User guest = new User("Guest","dummyEmail","dummyPassword");
			return ok(books.render(guest));
			
		}
	}

}
