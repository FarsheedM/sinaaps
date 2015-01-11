package controllers;

import java.util.*;

import org.joda.time.DateTime;

import models.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;
import play.db.jpa.*;



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

	public static Result showBlogPostFullContent(String language,Integer postId){
		BlogPost post = BlogPost.find.where().eq("postID", postId).eq("language",language).findUnique();

		User user;
		if(session().containsKey("email")){
			user = User.find.byId(session().get("email"));
		}else {
			user = new User("Guest","dummyEmail","dummyPassword");
		}
		//comments is a list of all the comments related to the post, where they should appear
		List<BlogComment> comments = BlogComment.find.where().eq("post.postID", postId).findList();
		
		if(post != null){
			if(language.equals("english")){
				return ok(views.html.postContent.render(post,user,Form.form(BlogComment.class),comments));
			}
			else if(language.equals("farsi")){
				//return ok(views.html.farsiEdition.///blog.render(new User("Guest","dummyEmail","dummyPassword"),getBlogPostByLang(language)));
				return ok(views.html.farsiEdition.postContent.render(post,user,Form.form(BlogComment.class),comments));
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
	
	//this method is called when the user post the new comment(in postContent view)
	//It adds the comment to the BlogComment model/Table
	public static Result postComment(String language,int postId){
		
		Form<BlogComment> commentForm = Form.form(BlogComment.class).bindFromRequest();
		//JPA.em().find(BlogComment.class, postId);

		/*//commentForm.get().commentID = 5L;*/

		if(commentForm.hasErrors()){
			return badRequest("error in commentForm !!");
		}
		
		commentForm.get().post= BlogPost.find.byId(postId);
		commentForm.get().user= User.find.byId(session().get("email"));
		commentForm.get().published = DateTime.now().toDate();
		//commentForm.get().likes = 0 ;
		


		//reload the page just for now.
		// the AJAX call should be used here to add the new comment
		commentForm.get().save();
		return redirect(routes.Blog.showBlogPostFullContent(language, postId));
		
	}

	public static Result deletePost(Long commentId,String language,Integer postId){
		
		BlogComment.find.ref(commentId).delete();
		return redirect(routes.Blog.showBlogPostFullContent(language, postId));
	}
	
}
