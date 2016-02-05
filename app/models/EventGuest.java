package models;

import javax.persistence.*;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class EventGuest extends Model{
	@Id
	@Required
	@Email
	public String email;
	public String name;
	public String contactNumber; 
	public int numberOfAdults;
	public int numberOfKids;
	public String comment;

	
	public static Finder<String,EventGuest> find = new Finder<String,EventGuest>(
	        String.class, EventGuest.class
	    );
	
}
