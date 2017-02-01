package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import models.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

//This Class is used to implement the many to many relationship 
//for the Book and Author classes.
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
	
	public static Finder<Integer,BookAuthor> find = new Finder<>
														(BookAuthor.class);
	
	public BookAuthor(Integer id,Book book, Author author){
		this.id = id;
		this.book=book;
		this.author=author;
	}
	
	//This method is needed to somehow retrieve a list of author(s) of the
	//given bookId. It will be called in e.g. bookProfile view.
	public static List<Author> getAuthorList(Integer bookId){
		List<BookAuthor> bA=  BookAuthor.find.where().eq("book_id", bookId).findList();
		List<Author> authors = new ArrayList<Author>();
		//Author aut = bA.get(0).author;
		for(BookAuthor a : bA){
			authors.add(a.author);
		}
		return authors;
	}
	
}
