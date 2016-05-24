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
		
		//TO BE IMPLEMENTED: the books of the called user should be retrieved 
		List<Book> bookList = Book.find.all();
		
		
		//called is the person whose profile is to be seen
		User called = User.find.byId(calledEmail);
		return ok(views.html.farsiEdition.profile.render(user,called,bookList));
	}

	public static Result showFriends(){
		
		
		//user is the person who is logged in
		User user;
		if(session().get("email") == null){
				user = new User("Guest","dummyEmail","dummyPassword");
				badRequest(veiws.alert.render(user,"You should not have been end up here!"))
			}
		else
			{
				user = User.find.byId(session().get("email"));
				List<User> allFriends = Relationship.friendList(user);
				return ok(views.farsiEdition.friends.render(user,allFriends));
			}
		
	}
}
