package controllers;

import java.util.List;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

import controllers.About.FeedBack;
import models.Book;
import models.User;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Services extends Controller{
	
	public static Result display(String lang){
		
		//here 'newBooks' should be the some of our translated books to be displayed
		List<Book> newBooks = Book.find.where().order().desc("published").findList();
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(services.render(User.find.byId(session().get("email")),Form.form(FeedBack.class),newBooks));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(services.render(guest,Form.form(FeedBack.class),newBooks));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.services.render(User.find.byId(session().get("email")),Form.form(FeedBack.class),newBooks));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.services.render(guest,Form.form(FeedBack.class),newBooks));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}

	public static Result sendFeedBack(String lang){
		
		User usr;
		User guest = new User("Guest","dummyEmail","dummyPassword");
		if(session().containsKey("email"))
			usr = User.find.byId(session().get("email"));
		else
			usr = guest;
		
		try{
			Form<FeedBack> feedBackForm = Form.form(FeedBack.class).bindFromRequest();
			
	    	if(feedBackForm.hasErrors()){
	    		if(lang.equals("farsi"))
	    			return badRequest(views.html.farsiEdition.alert.render(usr,"پیام شما ارسال نشد! لطفا دوباره پیام خود را بفرستید."));
	    		else
	    			return badRequest(views.html.alert.render(usr,"Your message has not been sent! Please Try again!"));
			} else {
				
			    MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			    mail.setSubject(feedBackForm.get().subject);
			    mail.addRecipient("farsireads@gmail.com");
			    mail.addFrom(feedBackForm.get().email);
			    mail.send("A text only message", "<html><body><p>sent by <b>"+feedBackForm.get().name+
			    		"</b><br>"+feedBackForm.get().message+"</p></body></html>" );
				
			}


		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
		}
		if(lang.equals("farsi"))
			return ok(views.html.farsiEdition.alert.render(usr,"سپاس، پیام شما با موفقیت فرستاده شد. بزودی پیگیری خواهد شد."));
		else
			return ok(views.html.alert.render(usr,"Thanks!  Your message sent successfully. We will reply soon."));
	
	}
	
	public static class FeedBack{
		
		@Required
		public String name;
		@Required
		public String email;
		public String subject;
		@Required
		public String message;
		
	}
	
}
