package models;

import javax.persistence.*;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class BookAuthor extends Model{
	
	@ManyToMany
	@JoinColumn(name="book_id", referencedColumnName="book_id")
	Book book;
	@ManyToMany
	@JoinColumn(name="author_id", referencedColumnName="author_id")
	Author author;
	
	public static Finder<Book,BookAuthor> find = new Finder<Book,BookAuthor>
														(Book.class,BookAuthor.class);
	
	public BookAuthor(Book book, Author author){
		this.book = book;
		this.author = author;
	}
}
