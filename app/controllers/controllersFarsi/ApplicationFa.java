
//---------------FA edition --------------------------------------
package controllers.controllersFarsi;

import controllers.Application.Login;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.farsiEdition.*;
import models.*;

public class ApplicationFa extends Controller{

    public static Result FaIndex(){
    	return ok(FaIndex.render("فارسی‌ ریدز",Form.form(Login.class)));
    }
}
