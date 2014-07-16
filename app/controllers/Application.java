package controllers;

import com.typesafe.plugin.*;


import models.*;
import play.*;
import play.api.mvc.Session;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.*;
import play.mvc.Http.Context;
import views.html.*;
import views.html.farsiEdition.*;

import javax.persistence.*;
public class Application extends Controller {

	
	public static Result index() {
    	
    	return ok(views.html.index.render("FarsiReads",Form.form(Login.class)));
    }
	
    @Security.Authenticated(Secured.class)
	public static Result loggedIn(){
		return ok(loggedIn.render(User.find.byId(request().username())));
	}
    
    public static Result logout(){
    	
    	session().clear();
    	Context.current().flash().clear();
    	return redirect(controllers.controllersFarsi.routes.ApplicationFa.index());
    }

    public static class Login{
    	@Required
    	public String password;
    	@Required
    	public String email;
    	
    	public String validate(){
    	
    		if(User.authenticate(email, password) == null)
    			return " * ایمیل یا پسورد خود را اشتباه وارد کردید";
    		
    		return null;
    	}
    }
    public static Result authenticate(){
    	
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    	if(loginForm.hasErrors())
    		return badRequest(views.html.index.render("Unauthorized",loginForm));
    	else{
    		//if the form has no error, it redirects to the index while "a session" is created
    		session().clear();
    		session("email", loginForm.get().email);
    		return redirect(routes.Application.loggedIn());
    	}
    }
    
    /*--------------------------Debugging section---------------------*/
    
    //this main helper function was used to catch an error in 
    //hibernate configuration process. the CLASS LocationAwareLogger
    //was loaded wrongly from wrong place. by using the function below
    //i could find out from where this class is loaded. i removed the 
    //package from which the class was wrongly trying to load, restart the
    // hibernate and it found the right package 
    public static void main(String[] args){
    ClassLoader loader = javax.persistence.JoinColumn.class.getClassLoader();
    System.out.println(loader.getResource("javax/persistence/JoinColumn.class"));
    }
    
    
}
