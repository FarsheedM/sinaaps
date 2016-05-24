package models;


import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Relationship extends Model{

	public Relationship(User usr1,User usr2,int status,User actUsr){
		this.user1 = usr1;
		this.user2 = usr2;
		this.status = status;
		this.actionuser = actUsr;
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
	
	
	public static Finder<Integer,Relationship> find = new Finder<Integer,Relationship>(
			Integer.class, Relationship.class
	    );
	

}
