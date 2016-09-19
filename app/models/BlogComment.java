package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import scala.collection.generic.BitOperations.Int;
import play.db.ebean.Model.Finder;

import javax.persistence.JoinColumn;

import com.avaje.ebean.Expr;



@Entity
public class BlogComment extends Model implements DeleteUserListener{
	
	@Id
	//@SequenceGenerator(name="seq", sequenceName="seq")     
	//(strategy=GenerationType.SEQUENCE, generator="seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long commentID;
	@OneToOne
	@JoinColumn(name="post_id", referencedColumnName="post_id")
	public BlogPost post;
	@ManyToOne
	@JoinColumn(name="user_email", referencedColumnName="email")
	public User user;
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date published;
	@Required
	public String comment;
	public int rating;
	
	
	
	public static Finder<Long, BlogComment> find = new Finder<Long, BlogComment>(Long.class, BlogComment.class);
	/*implementation of the abstract interface 'DeleteUserListener' which is used as our Observer. Inheriting
	 *from this Interface makes the 'BlogComment' an Object of the Observer Pattern and therefore this
	 *object will be updated using 'deleteUser' method, every time a user deleted in the Settings.unregister().
	 *It basically deletes the comments of the deleted user. 
	 **/ 
	public void deleteUser(User userToBeDeleted){

		List<BlogComment> commentsToRemoved = BlogComment.find.where().eq("user", userToBeDeleted).findList();

		for(BlogComment cmt : commentsToRemoved)
			BlogComment.find.ref(cmt.commentID).delete();
	}
	
	
	
	
	//This method checks if the given user has already rated the blogPost. It is used in postContent.scala.html 
	//to decide if the rating stars would be displayed or not.
	public static boolean isRatedBy(User usr,BlogPost post){
		BlogComment bc = BlogComment.find.where().eq("user", usr).eq("post", post).
							not(Expr.eq("rating", 0)).findUnique();
		if(bc != null)
			return true;
		else
			return false;
	}
	//for a given blog post, this method returns 0-5, which is average of users' ratings
	public static int getPostRating(BlogPost post){
		//the sum of all the ratings divided by unique users will be the average of the rating
		List<BlogComment> bcList = BlogComment.find.where().eq("post", post).findList();
		int ratingsTotal=0;
		List<User> uniqueUserList = new ArrayList<User>();
		for(BlogComment bc : bcList){
			ratingsTotal += bc.rating;
			//if the user did not rate the post, he is excluded in overall rating
			if(!uniqueUserList.contains(bc.user) && isRatedBy(bc.user, post))
					uniqueUserList.add(bc.user);
		}
		int avgRating = 0;
		if(uniqueUserList.size() != 0)
			avgRating = Math.round(ratingsTotal/uniqueUserList.size());
		
		return avgRating;
	}

}

