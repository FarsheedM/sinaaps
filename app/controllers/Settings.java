package controllers;

import java.util.List;

import models.Book;
import play.mvc.Controller;
import play.mvc.Result;
import models.*;

public class Settings extends Controller{
	
	public static Result showSettings(String lang, String userEmail){
		
		List<Book> bookList = Book.find.all();
		User user = User.find.byId(userEmail);
		return ok(views.html.farsiEdition.settings.render(user,bookList));
	}

}
