package controllers;

import java.util.List;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

//This controller handle the requests both for Farsi and English parts.
//The reason is that we use only one BlogPost DB for both languages, 
//and we query the DB to fetch language-specified posts.
public class Blog extends Controller{
	
	//this function gets the language name as a argument and accordingly
	//shows the view. it calls the getBlogPostByLang(lang : String) which
	//queries the BlogPost model(DB) to select only the posts in the 
	//specified language.
	public static Result display(String lang){
		
		if(lang.matches("english")){
			if(session().containsKey("email"))
				return ok(views.html.blog.render(User.find.byId(session().get("email")),getBlogPostByLang(lang)));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.blog.render(guest,getBlogPostByLang(lang)));
			}
		}
		else if(lang.matches("farsi")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.blog.render(User.find.byId(session().get("email")),getBlogPostByLang(lang)));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.blog.render(guest,getBlogPostByLang(lang)));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
		
	}
	
	//this method gets the list of all BlogPost in specified language 
	public static List<BlogPost> getBlogPostByLang(String lang){
		return BlogPost.find.where().eq("language", lang).findList();
	}

	public static String showBlogPostFullContent(int PostID){
		return "";
	}
	
}
