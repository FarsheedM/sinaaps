package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.JoinColumn;

@Entity
public class BookReview extends Model implements DeleteUserListener{
	
	@Id
	@GeneratedValue
	public Long reviewID;
	@OneToOne
	@JoinColumn(name="book_id", referencedColumnName="book_id")
	public Book book;
	@ManyToOne
	@JoinColumn(name="user_email", referencedColumnName="email")
	public User user;
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date published;
	@Required
	public String review;
	public int rating;
	
	
	
	public static Finder<Long, BookReview> find = new Finder<Long, BookReview>(Long.class, BookReview.class);
	/*implementation of the abstract interface 'DeleteUserListener' which is used as our Observer. Inheriting
	 *from this Interface makes the 'BookReview' an Object of the Observer Pattern and therefore this
	 *object will be updated using 'deleteUser' method, every time a user deleted in the Settings.unregister().
	 *It basically deletes the book reviews of the deleted user. 
	 **/ 
	public void deleteUser(User userToBeDeleted){

		List<BookReview> reviewsToRemoved = BookReview.find.where().eq("user", userToBeDeleted).findList();

		for(BookReview cmt : reviewsToRemoved)
			BookReview.find.ref(cmt.reviewID).delete();
	}
	
}
