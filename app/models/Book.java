package models;

import java.util.Date;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Book extends Model{
	
	@Id
	@Required
	public Integer bookID;
	public String isbn;
	public int page;
	public Date published;
	public String photo;
	public String dimensions;
	public float weight;
	
	public static Finder<Integer,Book> find = new Finder<Integer,Book>
															(Integer.class,Book.class);
	
	
	public Book(Integer bookID, String isbn, int page, Date published, String photo, 
				String dimensions, float weight){
		
		this.bookID = bookID;
		this.isbn = isbn;
		this.page = page;
		this.published = published;
		this.photo = photo;
		this.dimensions = dimensions;
		this.weight = weight;
		
	}
	
}
