package controllers;

import java.util.Date;

import play.mvc.*;
import play.data.Form;
import views.html.*;
import views.html.helper.form;
import models.*;

public class Signup extends Controller {
	
	public static Result register(){
		
		return ok(views.html.signUp.render(Form.form(User.class)));
	}
	
	//authenticate() method should be implemented 
	//It is used in the form in the signUp view
	public static Result authenticate(){
		Form<User> registerForm = Form.form(User.class).bindFromRequest();
		
		//check repeated email
		if(!registerForm.field("email").valueOr("").equals(registerForm.field("repeatEmail").value())){
			registerForm.reject("repeatEmail","emails don't match");
		}
		
		if(registerForm.hasErrors()){
			return badRequest(signUp.render(registerForm));
		}
		else{
			/*CODE HERE: add the new User in DB*/
			
			//save() persists the data in Ebean which means handles our DB updating
			registerForm.get().save();
			//flash is simply the cookie facility in Play! the flash content can be check in the redirected page
			flash("success","Dear "+ registerForm.get().fName +"Welcome to FarsiReads!");
			return redirect(routes.Application.index());
		}
	}
	
}
