package models;

import java.util.Date;

import javax.persistence.*;

import play.data.format.Formats;
import play.db.ebean.Model;

@Entity
public class Activity extends Model{
	/*This class serves as the bulk of all the activities that all users perform*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	@OneToOne
	@JoinColumn(name="user_email", referencedColumnName="email")
	public User actor;
	// e.g. make-friend, read, add, start, finish, like, rate, post
	public String verb;
	// e.g. blog ,book, comment, review, image, note, process, task, video, audio, person, role
	public String objectType;
	public Long objectId;
	public String objectUrl;
	//OPTIONAL: It indicates the context in which the object placed, e.g. blog, bookprofile.
	public String sourceType;
	public String sourceUrl;
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date published;

	
	
	public static play.db.ebean.Model.Finder<Long, Activity> find = new Finder<Long, Activity>(Long.class, Activity.class);
																		
	
}
