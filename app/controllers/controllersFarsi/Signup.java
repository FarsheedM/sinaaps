/*--------------------Farsi Edition ------------------*/
package controllers.controllersFarsi;

import models.User;
import controllers.routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.signUp;

public class Signup extends Controller{
	
	
	public static Result register(){
		
		return ok(views.html.farsiEdition.signUp.render(Form.form(User.class)));
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
			return badRequest(views.html.farsiEdition.signUp.render(registerForm));
		}
		else{
			/*CODE HERE: add the new User in DB*/
			
			//save() persists the data in Ebean which means handles our DB updating
			registerForm.get().save();
			//flash is simply the cookie facility in Play! the flash content can be check in the redirected page
			flash("success","Dear "+ registerForm.get().fName +"Welcome to FarsiReads!");
			return redirect(controllers.controllersFarsi.routes.ApplicationFa.index());
			
		}
	}

}
