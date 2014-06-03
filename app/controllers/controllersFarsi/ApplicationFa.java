
//---------------FA edition --------------------------------------
package controllers.controllersFarsi;

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
}
