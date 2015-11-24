package controllers;

import org.h2.engine.Session;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

import controllers.Application.Login;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import models.*;
import play.mvc.Http.Context;

public class About extends Controller{
	
	public static Result display(String lang){
		
		
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(about.render(User.find.byId(session().get("email")),Form.form(FeedBack.class)));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(about.render(guest,Form.form(FeedBack.class)));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.about.render(User.find.byId(session().get("email")),Form.form(FeedBack.class)));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.about.render(guest,Form.form(FeedBack.class)));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	
	}
	//NOTE: the alert view is used but the if else should be implemented along with
	//the english version of alert view!
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
	
	public static Result displayTeam(String lang){
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(about.render(User.find.byId(session().get("email")),Form.form(FeedBack.class)));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(about.render(guest,Form.form(FeedBack.class)));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.team.render(User.find.byId(session().get("email")),Form.form(FeedBack.class)));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.team.render(guest,Form.form(FeedBack.class)));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}
	
}
