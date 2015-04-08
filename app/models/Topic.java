package models;


import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Topic extends Model{

	@Id
	@Required
	public Integer topicID;
	public String enName;
	public String faName;
	public String enDescription;
	public String faDescription;
	
	
	public static Finder<Integer,Topic> find = new Finder<Integer,Topic>(Integer.class,Topic.class);
	
	public Topic(Integer topicId, String enName, String faName,  String enDescription, String faDescription){
		this.topicID = topicId;
		this.enName = enName;
		this.faName = faName;
		this.enDescription = enDescription;
		this.faDescription = faDescription;
	}

}
