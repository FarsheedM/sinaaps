
//---------------FA edition --------------------------------------
package controllers.controllersFarsi;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import controllers.routes;
import controllers.Application.Login;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.farsiEdition.*;
import models.*;

/*NOTE: if new external packages should be added:
 *      it should be added first to the "Java build Path" of the project
 *      after that, it should be imported to the java class where it is to used
 *      and lastly and more importantly It should be added in the dependency file
 *      in the project; in case of Play! it should be added to the built.sbt.
 *      to know the groupId and artifactsId of the package, you can use mvn site:
 *      http://mvnrepository.com/
 *       */
import com.google.api.services.analytics.*;
import com.google.api.services.analytics.model.*;
import com.google.api.client.json.jackson2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.analytics.model.GaData;
import com.google.api.services.analytics.model.GaData.*;
import com.google.api.services.analytics.Analytics.Data.Ga.Get;

public class ApplicationFa extends Controller{
	
    public static Result index(){
    	
    	//this gets the latest blogpost ordered by "published" date.
    	BlogPost post = BlogPost.find.where().eq("language", "farsi").order().desc("published").findList().get(0);   
    	List<String> ga = new ArrayList<String>();
    	ga.add("test");
    	if(!getVisitorsCountGA().isEmpty())
    		ga =getVisitorsCountGA();
     	return ok(views.html.farsiEdition.index.render("فارسی‌ ریدز",Form.form(Login.class),post,ga));
    }
    
    public static Result authenticate(){
    	
    	Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
    	BlogPost post = BlogPost.find.where().eq("language", "farsi").order().desc("published").findList().get(0);
    	List<String> ga = new ArrayList<String>();

    	
    	if(loginForm.hasErrors())
    		return badRequest(views.html.farsiEdition.index.render("Unauthorized",loginForm,post,ga));
    	else{
    		//if the form has no error, it redirects to the index while "a session" is created
    		session().clear();
    		session("email", loginForm.get().email);
    		return redirect(routes.Application.loggedIn());
    	}
    }
    //using the ga (Google Analytics API) to get the visitors count
    public static List<String> getVisitorsCountGA(){
    	
    	List<String> res = new ArrayList<String>();
	  	//this metric shows the number of visitors 
	  	final String TABLE_ID = "ga:87926285";
        try{
          Analytics analytics = initializeAnalytics();
          GaData gaData = executeDataQuery(analytics, TABLE_ID);
          res = getDataTable(gaData);
          
        } catch (GoogleJsonResponseException e) {
	        	System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
	            + e.getDetails().getMessage());
	      } catch (Throwable t) {
	    	  t.printStackTrace();
	      	}

         return res;
 
    }
    private static Analytics initializeAnalytics() throws Exception {
        
	  	final String APPLICATION_NAME = "farsiReads";

	  	HttpTransport HTTP_TRANSPORT;
	  	final JsonFactory JSON_FACTORY = new JacksonFactory();
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        //the email mentioned here has to be added to the "User Management" in GA Admin 
        String apiEmail = "900722495917-a4sf0839avupub5n7sbag5ookufgbr68@developer.gserviceaccount.com";
        
         /* In the developer console:
         * create new ClientID ->service account and save the privateKey.p12 somewhere
         */
        
        File analyticsPrivateKeyFile = new File("C:\\Users\\Farian\\Downloads\\key.p12.p12");
        
        GoogleCredential credential = new GoogleCredential.Builder()
        .setTransport(HTTP_TRANSPORT)
        .setJsonFactory(JSON_FACTORY)
        .setServiceAccountId(apiEmail)
        .setServiceAccountScopes(Arrays.asList(AnalyticsScopes.ANALYTICS_READONLY))
        .setServiceAccountPrivateKeyFromP12File(analyticsPrivateKeyFile).build();
        
        // Set up and return Google Analytics service Object
        return new Analytics.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(
            APPLICATION_NAME).build();
    }
    
    private static GaData executeDataQuery(Analytics analyticsService, String tableId) throws IOException {
        
        String startDate = "2014-01-03";
        String endDate = "2014-07-04";
        String mertrics = "ga:users";

        // Use the analytics object build a query
        Get get = analyticsService.data().ga().get(tableId, startDate, endDate, mertrics);
        
        // Run the query
        GaData data = get.execute();
       
        return data;
     }
    private static List<String> getDataTable(GaData gaData) {
        if (gaData.getTotalResults() > 0) {
        	return gaData.getRows().get(0);

          
        } else {
          //System.out.println("No data");
        	return null;
        }
        
      }
    
    
}
