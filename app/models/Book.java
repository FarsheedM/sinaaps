package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	public int userRating;
	//this option could be used to mark the suggested books from us
	public int farsireadsRating; 
	
	public static Finder<Integer,Book> find = new Finder<Integer,Book>
															(Integer.class,Book.class);
	
	
	/*This method is used in 'bookProfile.scala.html'. Giving the bookId as a parameter, it
	 *returns a list of books with the same topic. It finds the books topic first, then queries 
	 *the Book DB for the books with that specific topic. */
	public static List<Book> getBooksWithSameTopicAsBook(Book book){
		
		Integer topicId = TopicBook.find.where().eq("book", book).findList().get(0).topic.topicID;
		// topicBooks holds a list of books with the same topic as topicId
		List<Book> topicBooks = new ArrayList<Book>();
		List<TopicBook> tpbk = TopicBook.find.where().eq("topic_id", topicId).findList();
		Book bk;
		for(TopicBook i : tpbk){
			bk = Book.find.byId(i.book.bookID);
			topicBooks.add(bk);
		}
		return topicBooks;
	} 
	
}
