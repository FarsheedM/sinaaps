package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

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
	public int userRating;
	//this option could be used to mark the suggested books from us
	public int farsireadsRating; 
	
	public static Finder<Integer,Book> find = new Finder<>
															(Book.class);
		
}
