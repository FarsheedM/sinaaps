package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Books extends Controller{
	
	public static Result display(String lang){
		
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(books.render(User.find.byId(session().get("email"))));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(books.render(guest));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.books.render(User.find.byId(session().get("email"))));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.books.render(guest));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}
	


}
