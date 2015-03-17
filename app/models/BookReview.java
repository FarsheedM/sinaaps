package models;

import java.util.Date;

import javax.persistence.*;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.JoinColumn;

@Entity
public class BookReview extends Model{
	
	@Id
	@GeneratedValue
	public long reviewID;
	@OneToOne
	@JoinColumn(name="book_id", referencedColumnName="book_id")
	public Book book;
	@ManyToOne
	@JoinColumn(name="user_email", referencedColumnName="email")
	public User user;
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date published;
	@Required
	public String review;
	public int rating;
	
	
	
	public static Finder<Long, BookReview> find = new Finder<Long, BookReview>(Long.class, BookReview.class);
}
