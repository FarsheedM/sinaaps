package controllers;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import models.Book;
import models.DeleteUserListener;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.Context;
import views.html.books;
import models.*;

public class Settings extends Controller{
	
	
	public static Result showSettings(String lang){
		
		Form<User> settingsForm = Form.form(User.class).fill(User.find.byId(session().get("email")));
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.settings.render(User.find.byId(session().get("email")),settingsForm));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.settings.render(guest,settingsForm));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email")){
				User usr = User.find.byId(session().get("email"));
				return ok(views.html.farsiEdition.settings.render(usr,settingsForm));
			}
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.settings.render(guest,settingsForm));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}
	

	@SuppressWarnings("unused")
	public static Result saveSettings(String lang){
		
		User usr = User.find.byId(session().get("email"));

		//therefore 'originalPassword' keeps the user's previously set password.
		String originalPassword = usr.password;
		Form<User> settingsForm = Form.form(User.class).bindFromRequest();
		String getPass = settingsForm.get().password;
 
		//it's shown that the password is not changed by the user and eventually 
		//it is set to the previous value
		if(settingsForm.get().password.equals("")){
			settingsForm.get().password = originalPassword;
		}
	
		/*CODE HERE: updates the changes of the User in DB*/
		try {
			settingsForm.get().update();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return badRequest(views.html.farsiEdition.alert.render(usr,"متاسفانه تغییرات شما با موفقیت ثبت نشد.لطفا دوباره امتحان کنید و در صورت مشکل مجدد، با بخش فنی‌ تماس حاصل فرمائید."));
		}
		return ok(views.html.farsiEdition.alert.render(usr,"تغییرات شما با موفقیت ثبت شد!"));
			
	}
	
	
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
	
	
	/* unregister() delete the user from the DB. the older versions of the DB are to keep the deleted user's
	 * info if needed.
	 * Using the Observer Pattern, all the user's comments, reviews, friends and Ratings are to be deleted
	 * from its proper DBs.
	 * this method serves as a subject in our Observer Pattern. the Observer(abstract interface) is called
	 *'' and objects or views are '','' and ''.   */
	public static Result unregister(String lang, String usrEmail){
		
		User userToDeleted = User.find.byId(usrEmail);
		/*the list of listeners in the Observer pattern. by unregister, the user's comments, reviews
		 *and friends will be deleted. these objects should be listed as listeners.*/
		Vector<DeleteUserListener> listeners = new Vector<DeleteUserListener>();
		listeners.addElement(new BlogComment());
		listeners.addElement(new BookReview());
		listeners.addElement(new models.Relationship());
		
		for(Enumeration<DeleteUserListener> e = listeners.elements(); e.hasMoreElements();)
			e.nextElement().deleteUser(userToDeleted);
		
		User.find.ref(usrEmail).delete();
		
    	session().clear();
    	Context.current().flash().clear();
    	return redirect(controllers.controllersFarsi.routes.ApplicationFa.index());
		//return ok(User.find.all().size()+" size");
	}
}
