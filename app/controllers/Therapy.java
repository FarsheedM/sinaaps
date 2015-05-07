package controllers;

import controllers.About.FeedBack;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import models.*;

public class Therapy extends Controller{
	
	public static Result display(String lang){
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(therapy.render(User.find.byId(session().get("email"))));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(therapy.render(guest));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.therapy.render(User.find.byId(session().get("email"))));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.therapy.render(guest));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}

}
