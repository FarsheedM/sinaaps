package controllers;

import models.User;
import play.*;
import play.api.mvc.Session;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.*;
import play.mvc.Http.Context;
import views.html.*;
import views.html.farsiEdition.*;

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
    	return redirect(routes.Application.index());
    }

    public static class Login{
    	@Required
    	public String password;
    	@Required
    	public String email;
    	
    	public String validate(){
    	
    		if(User.authenticate(email, password) == null)
    			return "invalid email or password!!";
    		
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
 


    
    
}
