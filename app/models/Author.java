package models;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Author extends Model{
	
	@Id
	@Required
	public Integer authorID;
	public String fName;
	public String lName;
	public String email;
	public String description;
	
	public static Finder<Integer, Author> find = new Finder<Integer,Author>(
			Integer.class, Author.class
	    );
	
	public Author(int authorID, String fName,String lName, String email, String description){
		this.authorID = authorID;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.description = description;
		
	}

}
