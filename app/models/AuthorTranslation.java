package models;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class AuthorTranslation extends Model{

	@Id
	@Required
	public Integer id;
	@OneToOne
	@JoinColumn(name="author_id", referencedColumnName="author_id")
	public Author author;
	public String language; 
	public String fName;
	public String lName;
	public String description;
	public String born;
	public String gender;
	
	public static Finder<Integer,AuthorTranslation> find = new Finder<Integer,AuthorTranslation>
																(Integer.class,AuthorTranslation.class);
	
	public AuthorTranslation(Integer id, Author author,String language, String fName,
										String lName, String description,String born,String gender){
		
		this.id = id;
		this.author = author;
		this.language = language;
		this.fName = fName;
		this.lName = lName;
		this.description = description;
		this.born = born;
		this.gender = gender;
	}
	

	//This method is to retrieve the details of a Author by giving 
	//the author_id and the language
	public static AuthorTranslation getAuthor(Integer author_id,String language){
		return AuthorTranslation.find.where().eq("author_id", author_id).
											eq("language", language).findUnique();
	}
}


