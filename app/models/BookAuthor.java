package models;

import java.util.List;

import javax.persistence.*;

import models.*;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class BookAuthor extends Model{
	
	@Id
	public Integer id;
	
	@OneToOne
	@JoinColumn(name="book_id", referencedColumnName="book_id")
	public Book book;

	@OneToOne
	@JoinColumn(name="author_id", referencedColumnName="author_id")
	public Author author;
	
	public static Finder<Integer,BookAuthor> find = new Finder<Integer,BookAuthor>
														(Integer.class,BookAuthor.class);
	
	public BookAuthor(Integer id,Book book, Author author){
		this.id = id;
		this.book=book;
		this.author=author;
	}

}
