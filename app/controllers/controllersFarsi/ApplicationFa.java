
//---------------FA edition --------------------------------------
package controllers.controllersFarsi;

import controllers.routes;
import controllers.Application.Login;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.farsiEdition.*;
import models.*;

public class ApplicationFa extends Controller{

    public static Result index(){
    	
    	//this gets the latest blogpost ordered by "published" date.
    	BlogPost post = BlogPost.find.where().eq("language", "farsi").order().desc("published").findList().get(0);
    	return ok(views.html.farsiEdition.index.render("فارسی‌ ریدز",Form.form(Login.class),post));
    }
    
    public static Result authenticate(){
    	
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    	BlogPost post = BlogPost.find.where().eq("language", "farsi").order().desc("published").findList().get(0);
    	if(loginForm.hasErrors())
    		return badRequest(views.html.farsiEdition.index.render("Unauthorized",loginForm,post));
    	else{
    		//if the form has no error, it redirects to the index while "a session" is created
    		session().clear();
    		session("email", loginForm.get().email);
    		return redirect(routes.Application.loggedIn());
    	}
    }
    
}
