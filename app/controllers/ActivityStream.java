package controllers;

import java.util.*;

import org.joda.time.DateTime;

import models.*;
import play.mvc.Controller;

public class ActivityStream extends Controller{

	/*It retrieves all the activities of the given user/actor */
	public static List<Activity> getActivityStreamList(User usr){
		
		List<ActivityStreamList> streamList = ActivityStreamList.find.where().eq("user", usr).findList();
		List<Activity> activityList = new ArrayList<Activity>();
		Activity act;
		for(ActivityStreamList actStream : streamList){
			act = Activity.find.byId(actStream.activity.id);
			activityList.add(act);
		}
		return activityList;
	}
	
	/*add a new activity to the Activity DB. This method is overloaded. The second method skips the 
	 *optional parameters: sourceType and sourceUrl. */
	public static void addNewActivity(User actor, String verb, String objType, String objUrl,
										String srcType, String srcUrl){
		Activity newAct = new Activity();
		newAct.actor = actor;
		newAct.objectType = objType;
		newAct.objectUrl = objUrl;
		newAct.sourceType=srcType;
		newAct.sourceUrl=srcUrl;
		newAct.published = DateTime.now().toDate();
		
		List<Activity> actList = Activity.find.all();
		actList.add(newAct);
		
		/*by adding a new activity in the activity DB, the Activity Stream List - Newsfeed - of
		 *each user will be updated by publishing/adding the new activity into the ActivityStreamList.*/
		publishActivity(newAct.id,actor);
		
	}
	public static void addNewActivity(User actor, String verb, String objType, String objUrl){
		Activity newAct = new Activity();
		newAct.actor = actor;
		newAct.objectType = objType;
		newAct.objectUrl = objUrl;
		newAct.sourceType=null;
		newAct.sourceUrl=null;
		newAct.published = DateTime.now().toDate();
		
		List<Activity> actList = Activity.find.all();
		actList.add(newAct);
		
		/*by adding a new activity in the activity DB, the Activity Stream List - NewsFeed - of
		 *each user will be updated by publishing/adding the new activity into the ActivityStreamList. */
		publishActivity(newAct.id,actor);
		
	}
	
	/*add a new activity to a specific user's newsfeed, in other words,
	 *add a new record to the ActivityStreamList. this new activity should be added to 1.the actor's own 
	 *activity stream list and 2.the actor's friends' activity stream lists. */
	public static void publishActivity(Long activityId, User actor){
		
		Activity act = Activity.find.byId(activityId);
		/*the list of the 'ActivityStreamList', to hold all the ActivityStream and then addAll to the DB 
		 *in one step.*/
		List<ActivityStreamList> newActivityStreamList= new ArrayList<ActivityStreamList>();
		
		//list the friends of the actor to publish the new activity in their newsfeed
		List<User> friends = controllers.Relationship.friendList(actor);
		
		//list of all friends + the actor him self. their activity Stream List will be updated
		List<User> personsToHaveNewsFeedUpdated = new ArrayList<User>();
		personsToHaveNewsFeedUpdated.addAll(friends);
		personsToHaveNewsFeedUpdated.add(actor);
		
		
		for(User usr : personsToHaveNewsFeedUpdated){
			ActivityStreamList newActStream = new ActivityStreamList();
			newActStream.user = usr;
			newActStream.activity = act;
			newActivityStreamList.add(newActStream);
		}
		
		List<ActivityStreamList> pa = ActivityStreamList.find.all();
		pa.addAll(newActivityStreamList);
		
	}
	
	
	
}
