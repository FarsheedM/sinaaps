package controllers;

import java.util.List;

import models.Book;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Books extends Controller{
	
	public static Result display(String lang){
		
		//for the 1st version a list of newly published books will be passed to books.scala.html
		// but in the next versions, a list of featured books should be pass, maybe a change
		//in the Book model should be necessary by adding a a boolean, e.g. "featured".
		List<Book> bookList = Book.find.where().order().desc("published").findList();
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(books.render(User.find.byId(session().get("email")),bookList));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(books.render(guest,bookList));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.books.render(User.find.byId(session().get("email")),bookList));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.books.render(guest,bookList));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}
	

	public static Result showBookProfile(String lang, Integer bookId){
		
		//book obj contains the general information about the book which is 
		//the same in different translations, e.g. ISBN, No. of Pages.
		Book book = Book.find.where().eq("bookID", bookId).findUnique();
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				//to update in english version!!
				return ok(views.html.farsiEdition.bookProfile.render(User.find.byId(session().get("email")),book));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.bookProfile.render(guest,book));
			}	
		}else if(lang.equals("farsi")){
			
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.bookProfile.render(User.find.byId(session().get("email")),book));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.bookProfile.render(guest,book));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}

}
