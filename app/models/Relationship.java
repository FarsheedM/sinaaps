package models;


import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class Relationship extends Model implements DeleteUserListener{


	public Relationship(User usr1,User usr2,int status,User actUsr){
		this.user1 = usr1;
		this.user2 = usr2;
		this.status = status;
		this.actionuser = actUsr;
	}
	
	public Relationship() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer relationshipId;
	@Required
	@OneToOne
	@JoinColumn(name="user1_email", referencedColumnName="email")
	public User user1;
	@Required
	@OneToOne
	@JoinColumn(name="user2_email", referencedColumnName="email")
	public User user2;
	/* Status Codes:
	 * 	0 	Pending
		1 	Accepted
		2 	Declined
		3 	Blocked
	 * */
	public int status;
	//'actionUser' represents the id of the user who has performed the most recent status field update
	@Required
	@OneToOne
	@JoinColumn(name="actionuser_email", referencedColumnName="email")
	public User actionuser;
	
	
	public static Finder<Integer,Relationship> find = new Finder<>(Relationship.class);
	
	/*implementation of the abstract interface 'DeleteUserListener' which is used as our Observer. Inheriting
	 *from this Interface makes the 'Relationship' an Object of the Observer Pattern and therefore this
	 *object will be updated using 'deleteUser' method, every time a user deleted in the Settings.unregister().
	 *It basically deletes the book reviews of the deleted user. 
	 **/ 
	public void deleteUser(User userToBeDeleted){

		List<Relationship> friendsToRemoved = //Relationship.find.where().eq("user1", userToBeDeleted).findList();
											models.Relationship.find.where()
											.disjunction()
												.conjunction()
													.eq("user1", userToBeDeleted)
												.endJunction()
												.conjunction()
													.eq("user2", userToBeDeleted)
												.endJunction()
											.endJunction().findList();
		
		for(Relationship cmt : friendsToRemoved)
			Relationship.find.ref(cmt.relationshipId).delete();
	}
}
