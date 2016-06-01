package controllers;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import com.avaje.ebean.Expr;

import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class Relationship extends Controller{
	
	public static Result friendRequest(String user1email,String user2email){
		
		User user1 = User.find.byId(user1email);
		User user2 = User.find.byId(user2email);
		
		if(checkFriendship(user1,user2)){
			//user1 and user2 are already friends
			return ok(views.html.farsiEdition.alert.render(user1,"شما هم اکنون دوست هستید"));
		}
		else
		{
			//user1 and user2 are not friends
			
			if(isBlocked(user2,user1)){
				//checks if user2 has already blocked user1 to prevent him from further friendRequest
				return ok(views.html.farsiEdition.alert.render(user1, "متاسفانه "+ user2.fName + "شما را بلوکه کرده اند. شما نمیتوانید ایشان را به لیست دوستان خود بیفزایید . "));
			}
			else{
				List<models.Relationship> relList = models.Relationship.find.all();
				try {
					models.Relationship rel = new models.Relationship(user1,user2,0,user1);
					rel.save();
					relList.add(rel);
				} catch (PersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return ok(views.html.farsiEdition.alert.render(user1,"!درخواست دوستی فرستاده نشد"));
				}
				
				return ok(views.html.farsiEdition.alert.render(user1,"درخواست دوستی فرستاده شد"));
			}
			
		}
		//List<models.Relationship> relList = models.Relationship.find.all();


	}
	/*The user has the choice to confirm or to decline the request by deleting it.
	 *To confirm: the 'status' in the Relationship will be changed to 1.*/
	public static Result acceptFriendRequest(String lang,String pendingUserEmail){
		
		User pendingUser = User.find.byId(pendingUserEmail);
		if(lang.equals("english")){
			//to be implemented
			if(session().containsKey("email")){
				User usr = User.find.byId(session().get("email"));
				return redirect(routes.Settings.showFriends(lang,usr.email));
			}
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.alert.render(guest,"You should log in first in order to be able to add friends."));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email")){
				User usr = User.find.byId(session().get("email"));
				//Here is the actual code to change the 'status' to accepted friend
				models.Relationship rel;
				try{
					rel = models.Relationship.find.where()
							.disjunction()
								.conjunction()
									.eq("user1", usr)
									.eq("user2", pendingUser)
								.endJunction()
								.conjunction()
									.eq("user1", pendingUser)
									.eq("user2", usr)
								.endJunction()
							.endJunction()
							.eq("status", 0).not(Expr.eq("actionuser", usr)).findUnique();
							
				} catch(NullPointerException e) {
					e.printStackTrace();
					return ok(views.html.farsiEdition.alert.render(usr,"ببخشید؛ مشکل فنی‌ در در افزودن به لیست دوستان شما وجود دارد. لطفا بعدا دوباره امتحان کنید."));
				}
				if(rel == null)
					return redirect(routes.Settings.showFriends(lang,usr.email));
				else{
					rel.status = 1;
					rel.update();
					return redirect(routes.Settings.showFriends(lang,usr.email));
				}
			}
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.alert.render(guest,"برای افزودن به لیست دوستان خود، شما میبایست به سیستم وارد شوید."));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
		
	}
	
	public static Result declineFriendRequest(String lang,String pendingUserEmail){
		
		User pendingUser = User.find.byId(pendingUserEmail);
		if(lang.equals("english")){
			//to be implemented
			if(session().containsKey("email")){
				User usr = User.find.byId(session().get("email"));
				return redirect(routes.Settings.showFriends(lang,usr.email));
			}
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.alert.render(guest,"You should log in first in order to be able to add friends."));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email")){
				User usr = User.find.byId(session().get("email"));
				//Here is the actual code to change the 'status' to accepted friend
				models.Relationship rel;
				try{
					rel = models.Relationship.find.where()
							.disjunction()
								.conjunction()
									.eq("user1", usr)
									.eq("user2", pendingUser)
								.endJunction()
								.conjunction()
									.eq("user1", pendingUser)
									.eq("user2", usr)
								.endJunction()
							.endJunction()
							.eq("status", 0).not(Expr.eq("actionuser", usr)).findUnique();
							
				} catch(NullPointerException e) {
					e.printStackTrace();
					return ok(views.html.farsiEdition.alert.render(usr,"ببخشید؛ مشکل فنی‌ در در افزودن به لیست دوستان شما وجود دارد. لطفا بعدا دوباره امتحان کنید."));
				}
				if(rel == null)
					return redirect(routes.Settings.showFriends(lang,usr.email));
				else{
					rel.status = 2;
					rel.update();
					return redirect(routes.Settings.showFriends(lang,usr.email));
				}
			}
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.alert.render(guest,"برای افزودن به لیست دوستان خود، شما میبایست به سیستم وارد شوید."));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}
	
	
	public static boolean checkFriendship(User user1,User user2){
		
		List<models.Relationship> rel;
		try {
			rel = models.Relationship.find.where().disjunction()
			        .conjunction()
			            .eq("user1", user1)
			            .eq("user2", user2)
			        .endJunction()
			        .conjunction()
			            .eq("user1", user2)
			            .eq("user2", user1)
			        .endJunction()
			    .endJunction()
			    .where().eq("status", 1).findList();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if(rel.isEmpty()){
			return false;
		}
		else
			return true;
	}
	
	//this function checks if 'user' has already blocked the 'blockedUser'
	public static boolean isBlocked(User user,User blockedUser){
		
		models.Relationship rel;
		try {
			rel = models.Relationship.find.where()
				.disjunction()
					.conjunction()
						.eq("user1", user).eq("user2", blockedUser)
						.eq("status", 3)
						.eq("actionuser", user)
					.endJunction()
					.conjunction()
						.eq("user2", user).eq("user1", blockedUser)
						.eq("status", 3)
						.eq("actionuser", user)
					.endJunction()
				.endJunction()
				.findUnique();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if(rel != null){
			//the user has blocked the blockedUser
			return true;
		}
		else
			return false;
				
	}
	
	public static List<User> friendList(User usr){
		
		List<models.Relationship> relList;
		try {
			relList = models.Relationship.find.where()
				.disjunction()//or
					.conjunction()//and
						.eq("user1", usr)
						.eq("status", 1)
					.endJunction()
					.conjunction()
						.eq("user2", usr)
						.eq("status", 1)
					.endJunction()
				.endJunction()
				.findList();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		List<User> userList = new ArrayList<models.User>();
		for(models.Relationship re : relList){
			if(re.user1.equals(usr))
				userList.add(re.user2);
			else
				userList.add(re.user1);
		}
			
		return userList;
	}
	//List of Friends in Common
	public static List<User> friendsInCommon(User usr1,User usr2){
		List<User> user1Friends = friendList(usr1);
		List<User> user2Friends = friendList(usr2);
		List<User> commonFriends = intersection(user1Friends,user2Friends);
		return commonFriends;
	}
	//List of pending friend Requests (the logged User could decide to confirm or reject them)
	public static List<User> friendsPending(User usr){
		List<models.Relationship> relList;
		try{
			relList = models.Relationship.find.where()
					.disjunction()
						
							.eq("user1", usr)
							.eq("user2", usr)
						
					.endJunction()
					.eq("status", 0).not(Expr.eq("actionuser", usr)).findList();
					
		} catch(NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		List<User> friendsPendingList = new ArrayList<models.User>();
		for(models.Relationship re : relList){
			if(re.user1.equals(usr))
				friendsPendingList.add(re.user2);
			else
				friendsPendingList.add(re.user1);
		}
		return friendsPendingList;
	}
	
	
	
	//auxiliary function to calculate the intersection of two lists
	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
}
