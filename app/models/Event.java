package models;

import java.util.Date;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class Event extends Model{
	
	@Id
	@Required
	public Integer eventID;
	@Required
	public String title;
	public String eventsDate;
	public String location;
	public String speakers;
	public String speakerImg1;
	public String speakerImg2;
	public String speakerImg3;
	public String image;
	public String hostedBy;
	public String description;
	public String price;
	@Required
	public String language;

	public static Finder<Integer,Event> find = new Finder<>(Event.class);
}
