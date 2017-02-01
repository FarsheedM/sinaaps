//#Application global settings to override the playframework's default error pages. For info look at:
//https://github.com/playframework/playframework/tree/2.2.x/documentation/manual/javaGuide/main/global/code/javaguide/global
import models.User;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;
import static play.mvc.Results.*;

public class Global extends GlobalSettings {

    public Promise<Result> onError(RequestHeader request, Throwable t) {
        return Promise.<Result>pure(internalServerError(
            views.html.errorPage.render(t)
        ));
    }
    
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
    	User guest = new User("Guest","dummyEmail","dummyPassword");
        return Promise.<Result>pure(notFound(
            //views.html.alert.render
            views.html.farsiEdition.alert.render(guest,"همچنین آدرسی در دایرکتوری فارسی ریدز موجود نمی‌‌باشد. شما URI اشتباه را وارد کرده اید. لطفا به صفحه اصلی‌ باز گردید.")  
        		));
    }
    
    public Promise<Result> onBadRequest(RequestHeader request, String error) {
        return Promise.<Result>pure(badRequest("BadRequest!"));
    }


}
