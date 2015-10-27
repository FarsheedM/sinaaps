
//---------------FA edition --------------------------------------
package controllers.controllersFarsi;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;

import controllers.routes;
import controllers.Application.Login;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results.*;
import views.html.farsiEdition.*;
import models.*;

import com.fasterxml.jackson.core.JsonParser;

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
import com.cloudinary.*;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.*;

import play.libs.Json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.hibernate.jpa.criteria.path.MapKeyHelpers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class ApplicationFa extends Controller{
	
    public static Result index(){
    	
    	//this gets the latest blogpost ordered by "published" date.
    	BlogPost post = BlogPost.find.where().eq("language", "farsi").order().desc("published").findList().get(0);   
    	List<String> ga = new ArrayList<String>();
    	ga.add("...");
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
        
        
        //new File("./public/images/key.p12.p12"));
        //this piece of code is working locally.
        //in below the code should work in arvixe in production mode
        File analyticsPrivateKeyFile = new File("/home/farsheed/public_html/key.p12.p12");
        
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
        
        String startDate = "2015-01-03";
        String endDate = "2015-08-17";
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
    /**
     * Using AJAX for the thumbnails retrieval from cloudinary needs to be done
     *  using JSONP(padding) format as the source is not in the same origin. 
     *  As cloudinary doesn't support JSONP by wrapping the JSON output with our
     *   named callback function(mysunc({--JSON data--})), the server approach 
     *   should be used instead of relying only on the client code. 
     *   **/
    @SuppressWarnings({ "unchecked", "unused" })
	public static Result getImageUrls(){

    	@SuppressWarnings("unchecked")
		Map<String, Object> config = ObjectUtils.asMap(
    	  "cloud_name", "cloudinary-sinaaps",
    	  "api_key", "187356621496487",
    	  "api_secret", "2NpXZWEoi2dv4-PG4_mJLzzp9Jc");

    	Cloudinary cloudinary = new Cloudinary(config);
    	Api api = cloudinary.api();
    	com.fasterxml.jackson.databind.JsonNode jsonResult = null;
    	Map<String,Object> r = new HashMap<String,Object>();
    	try {
    	    //jsonResult = Json.parse("{\"format\":\"jpeg\"}"); //(JsonNode) api.resources(ObjectUtils.asMap("type", "upload"));
    	    
			r = api.resources(ObjectUtils.asMap("type", "upload"));
    	    
    	    //jsonResult = Json.parse(r.toString());
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  
 /*   	  com.fasterxml.jackson.databind.node.ObjectNode result = Json.newObject();
    	  String name = jsonResult.findPath("format").textValue();*/
    	
    	ArrayList<Object> Fartest =  (ArrayList<Object>) r.get("resources");
		Object see = Fartest.get(3);
		HashMap<String,String> Hm = (HashMap<String, String>) see;
		String publicId= Hm.get("public_id").toString();
		
    	String transformedUrl = cloudinary.url()
    	  .transformation(new Transformation().width(50).height(66).crop("fill"))
    	  .generate(publicId);
    	Hm.put("url", transformedUrl);
    	r.put("resources", Fartest);
    	
    	
    	JSONObject j= new JSONObject(r);
    	
    	String name = j.toString();
    	System.err.println("farsheed");
    	  if(name == null) {
    	    return badRequest("error");
    	  } else {

    	return ok(name);
    	  }
}
    
    
    public static Result renewTheMind(){
    	return ok("Romans12_1");
    }
    
}
