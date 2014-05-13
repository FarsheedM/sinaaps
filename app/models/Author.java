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
	public String email;

	
	public static Finder<Integer, Author> find = new Finder<Integer,Author>(
			Integer.class, Author.class
	    );
	
	public Author(int authorID, String email){
		this.authorID = authorID;
		this.email = email;
		
	}

}
