package models;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class Author extends Model{
	
	@Id
	@Required
	public Integer authorID;
	public String email;
	public String photo;

	
	public static Finder<Integer, Author> find = new Finder<>(
			Author.class
	    );
	
	public Author(int authorID, String email){
		this.authorID = authorID;
		this.email = email;
		
	}

}
