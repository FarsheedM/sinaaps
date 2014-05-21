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
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(views.html.blog.render(User.find.byId(session().get("email")),getBlogPostByLang(lang)));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.blog.render(guest,getBlogPostByLang(lang)));
			}
		}
		else if(lang.equals("farsi")){
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

	public static Result showBlogPostFullContent(String language,int postId){
		BlogPost post = BlogPost.find.where().eq("postID", postId).eq("language",language).findUnique();
		//String content = post.content;
		User user;
		if(session().containsKey("email")){
			user = User.find.byId(session().get("email"));
		}else {
			user = new User("Guest","dummyEmail","dummyPassword");
		}
		
		if(post != null){
			if(language.equals("english")){
				return ok(views.html.postContent.render(post,user));
			}
			else if(language.equals("farsi")){
				//return ok(views.html.farsiEdition.///blog.render(new User("Guest","dummyEmail","dummyPassword"),getBlogPostByLang(language)));
				return ok(views.html.farsiEdition.postContent.render(post,user));
			}
			else{
				return badRequest("Content ERROR : The entered Language is not supported! PLease choose either Farsi or English");
			}
		}
		else{ 
			//if there is no post with the specified 'PostId' and 'language'
			return badRequest("Post Obj error : language and postId does't match!!");
			}
	}
	
	
	public static Result postComment(String language,int postId){
		return ok();
	}
	
}
