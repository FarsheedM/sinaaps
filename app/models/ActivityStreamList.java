package models;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity
public class ActivityStreamList extends Model{

	/*This class serves as the activity stream list(newsfeed) of the single user.
	 *by querying this class (DB), the specific users' newsfeed will be retrieved.
	 *given a specific user, it gives an array of activity Ids which include the user's 
	 *own activities plus the activities of his/her friends. */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Required
	public Integer id;
	@OneToOne
	@JoinColumn(name="user_email", referencedColumnName="email")
	public User user;
	@OneToOne
	@JoinColumn(name="id", referencedColumnName="id")
	public Activity activity;
	
	
	public static Finder<Integer,ActivityStreamList> find = new Finder<Integer,ActivityStreamList>
																	(Integer.class,ActivityStreamList.class);
	
}
