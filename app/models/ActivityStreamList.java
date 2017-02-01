package models;

import javax.persistence.*;


import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.*;


@Entity
public class ActivityStreamList extends Model{

	/*This class serves as the activity stream list(newsfeed) of the single user.
	 *by querying this class (DB), the specific users' newsfeed will be retrieved.
	 *given a specific user, it gives an array of activity Ids which include the user's 
	 *own activities plus the activities of his/her friends. */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer id;
	@OneToOne
	@JoinColumn(name="user_email", referencedColumnName="email")
	public User user;
	@OneToOne
	@JoinColumn(name="activity_id", referencedColumnName="id")
	public Activity activity;
	
	
	public static Finder<Integer,ActivityStreamList> find = new Finder<>
																	(ActivityStreamList.class);
	
	
    
    /**
     * Return a page of Blogs
     *
     * @param page Page to display
     * @param pageSize Number of computers per page
     * @param sortBy Computer property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
/*	public static com.avaje.ebean.PagedList<ActivityStreamList> page(int page, int pageSize, String sortBy,
													String order, String filter,User user){
		return 
				find.where()
				.eq("user", user)
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
	}*/


	public static PagedList<ActivityStreamList> page(int page, int pageSize, String sortBy, String order, String filter,User user) {
		return
				find.where()
						.eq("user", user)
						.orderBy(sortBy + " " + order)
						.fetch("activity")
						.findPagedList(page, pageSize);
	}



}
