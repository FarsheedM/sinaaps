package models;

import javax.persistence.*;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class BookTranslation extends Model{
	
	@Id
	public Integer id;
	@ManyToOne
	@JoinColumn(name="book_id",referencedColumnName="book_id")
	public Book book;
	public String language;
	public String title;
	public String summary;
	@ManyToOne
	@JoinColumn(name="author_id",referencedColumnName="author_id")
	public Author author;
	public String publication;
	public String format;
	

	public static Finder<Integer,BookTranslation> find = new Finder<Integer,BookTranslation>
												(Integer.class,BookTranslation.class);
	
	
	public BookTranslation(Integer id, Book book, String language, String title, 
								String summary, Author author, String publication, String format){
		
		this.id = id;
		this.book = book;
		this.language = language;
		this.title = title;
		this.summary = summary;
		this.author = author;
		this.publication = publication;
		this.format = format;
	}
}
