package controllers;

import java.util.List;

import models.Book;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import models.*;

public class Settings extends Controller{
	
	public static Result showProfile(String lang, String calledEmail){
		
		//user is the person who is logged in
		User user;
		if(session().get("email") == null){
			user = new User("Guest","dummyEmail","dummyPassword");
		}
		else
		{
			user = User.find.byId(session().get("email"));
		}
		
		//called is the person whose profile is to be seen
		User called = User.find.byId(calledEmail);
		
		//the books of the called user will be retrieved 
		List<Book> bookList = BookUser.userBookList(called);
		

		return ok(views.html.farsiEdition.profile.render(user,called,bookList));
	}

	public static Result showFriends(String lang,String calledUserEmail){
		
		//The logged user
		User user = User.find.byId(session().get("email"));
		//called is the person whose friends are called
		User called = User.find.byId(calledUserEmail);
		if(called == null  || called.email == ""){
				return badRequest(views.html.farsiEdition.alert.render(user,"Problem by the friends of the called user!"));
			}
		else
			{
				List<User> allFriends = Relationship.friendList(called);
				return ok(views.html.farsiEdition.friends.render(user,allFriends,called));
			}
		
	}
}
