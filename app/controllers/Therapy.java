package controllers;

import org.joda.time.DateTime;

import com.typesafe.plugin.MailerAPI;
import com.typesafe.plugin.MailerPlugin;

import controllers.About.FeedBack;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import models.*;

public class Therapy extends Controller{
	
	public static Result display(String lang){
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(therapy.render(User.find.byId(session().get("email")),Form.form(Feedback.class)));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(therapy.render(guest,Form.form(Feedback.class)));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.therapy.render(User.find.byId(session().get("email")),Form.form(Feedback.class)));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.therapy.render(guest,Form.form(Feedback.class)));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}

	
	//this method is called when the user post the new feedback(in therapy view)
	//It sends the feedback to the Admin
	public static Result postFeedback(String language){
		
		User usr;
		User guest = new User("Guest","dummyEmail","dummyPassword");
		if(session().containsKey("email"))
			usr = User.find.byId(session().get("email"));
		else
			usr = guest;
		
		try{
			Form<Feedback> feedBackForm = Form.form(Feedback.class).bindFromRequest();
			
	    	if(feedBackForm.hasErrors()){
	    		if(language.equals("farsi"))
	    			return badRequest(views.html.farsiEdition.alert.render(usr,"متاسفانه پیام شما ارسال نشد! لطفا دوباره پیام خود را بفرستید."));
	    		else
	    			return badRequest(views.html.alert.render(usr,"Your message has not been sent! Please Try again!"));
			} else {
				
			    MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			    mail.setSubject(feedBackForm.get().subject);
			    mail.addRecipient("farsireads@gmail.com");
			    mail.addFrom(usr.email);
			    mail.send("A text only message", "<html><body><p>sent by <b>"+usr.lName+"-"+usr.fName+
			    		"</b><br>"+feedBackForm.get().feedback+"</p></body></html>" );
			}
		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
		}
		if(language.equals("farsi"))
			return ok(views.html.farsiEdition.alert.render(usr,"سپاس، ارزیابی شما با موفقیت فرستاده شد. <br>پیام شما پس از بررسی‌، برای استفاده دیگران منتشر میشود."));
		else
			return ok(views.html.alert.render(usr,"Thanks!  Your evaluation sent successfully. We will reply soon."));

	}

	public static class Feedback{
		
		public String name;
		public String email;
		@Required
		public String subject;
		@Required
		public String feedback;
		
	}

}
