package controllers;

import java.util.List;

import models.*;
import play.data.Form;
import play.mvc.*;

public class Event extends Controller{

	public static Result displayEvent(String lang){
		
		User usr = new User("Guest","dummyEmail","dummyPassword");
		
		//NOTE: THIS Section is to be populated with english EVENT.scala.html version!!
		if(lang.equals("english")){
			if(session().containsKey("email")){
				usr= User.find.byId(session().get("email"));
				return ok(views.html.farsiEdition.event.render(Form.form(EventGuest.class),usr,getEventsByLang(lang)));
			}
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.event.render(Form.form(EventGuest.class),guest,getEventsByLang(lang)));
			}
			
		}
		else if(lang.equals("farsi")){
			if(session().containsKey("email")){
				usr= User.find.byId(session().get("email"));
				return ok(views.html.farsiEdition.event.render(Form.form(EventGuest.class),usr,getEventsByLang(lang)));
			}
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.event.render(Form.form(EventGuest.class),guest,getEventsByLang(lang)));
			}
		}
		else{
			//if neither english nor farsi is selected
			return ok(views.html.farsiEdition.alert.render(usr,"خطا: مطالب وبسایت به زبان وارد شده در دسترس نیست! لطفا زبان فارسی و یا انگلیسی‌ را انتخاب کنید."));
					
		}
		
	}
	
	//this method gets the list of all Events in specified language 
	public static List<models.Event> getEventsByLang(String lang){
		return models.Event.find.where().eq("language", lang).order().desc("eventsDate").findList();
	}
	
	public static Result register(){
		
		User usr = new User("Guest","dummyEmail","dummyPassword");
		if(session().containsKey("email")){
			usr= User.find.byId(session().get("email"));
		}
		
		Form<EventGuest> registerForm = Form.form(EventGuest.class).bindFromRequest();
		
		//check and reject multiple registration with a same email address
		if(EventGuest.find.byId(registerForm.field("email").value()) != null){
			//MessageBox is not working in my host: arvixe even deploying with the parameter: "-Djava.awt.headless=true"
			//MessageBox.infoBox("با این آدرس ایمیل قبلا ثبت نام شده است. لطفا از ایمیل خود استفاده کنید.", "اشکال در ثبت نام");
			return badRequest(views.html.farsiEdition.event.render(Form.form(EventGuest.class),usr,getEventsByLang("farsi")));
		}
		else if(registerForm.hasErrors()){
			//MessageBox.infoBox("آدرس ایمیل خود را اشتباه وارد کرده اید. دوباره تلاش کنید!", "اشکال در ثبت نام");
			return badRequest(views.html.farsiEdition.event.render(Form.form(EventGuest.class),usr,getEventsByLang("farsi")));
		}
		else{
			/*CODE HERE: add the new User in DB*/
			
			//save() persists the data in Ebean which means handles our DB updating
			registerForm.get().save();
			//flash is simply the cookie facility in Play! the flash content can be check in the redirected page
			flash("success","Dear "+ registerForm.get().name +"You are successfully registered for the Event!");
			//MessageBox is not working in my host: arvixe even deploying with the parameter: "-Djava.awt.headless=true"
			//MessageBox.infoBox("شما در این رویداد با موفقیت ثبت نام کردید", "گزارش ثبت نام");
			//return redirect(routes.EventEvent.displayEvent("farsi"));
			
			//the 'detailedTitle' is the name of the conference attendant, if it's given.
			String detailedTitle = "";
			if(registerForm.get().name != null && registerForm.get().name != ""){
				detailedTitle = "سرکار خانم/ جناب آقای " + registerForm.get().name.toString();
			}
			return ok(views.html.farsiEdition.alert.render(usr,detailedTitle + "<h3>ثبت‌نام شما در این کنفرانس با موفقیت انجام شد.</h3><p>لطفا از قبل برای سفر خود برنامه ریزی کنید و وسایل مورد نیاز خود را به همراه داشته باشید.</p><p>هزینه شرکت در کنفرانس، پیش از شروع برنامه دریافت خواهد شد.از دیدار با شما خوشحال خواهیم شد. برای این گردهمایی لطفا در دعا‌ باشید.</p>"));
			
		}
	}
	
}
