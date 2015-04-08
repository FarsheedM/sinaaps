package models;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class TopicBook extends Model{

	@Id
	public Integer id;
	
	@OneToOne
	@JoinColumn(name="book_id", referencedColumnName="book_id")
	public Book book;

	@OneToOne
	@JoinColumn(name="topic_id", referencedColumnName="topic_id")
	public Topic topic;
	
	public static Finder<Integer,TopicBook> find = new Finder<Integer,TopicBook>
														(Integer.class,TopicBook.class);
	
}
