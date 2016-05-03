package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;


//This Class is used to implement the many to many relationship 
//for the Book and User classes. It is used in the Virtual Library.
@Entity
public class BookUser extends Model{
	
	/*This constructor is needed for the 'addtoShelfAll()' method in Books controller.
	  the primary key ID should be built out of the two foreign keys.*/
	public BookUser(Book bk, User usr, boolean b, boolean c, boolean d) {
		
		String uniqueId = bk.bookID.toString().concat(Integer.toString((usr.email).hashCode()/1000));
		this.id = Integer.valueOf(uniqueId); 
		this.book = bk;   
		this.user = usr;
		this.finished = b;
		this.reading = c;
		this.toRead = d;
		
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer id;
	
	@OneToOne
	@JoinColumn(name="book_id", referencedColumnName="book_id")
	public Book book;
	
	@OneToOne
	@JoinColumn(name="user_email", referencedColumnName="email")
	public User user;
	
	public boolean finished;
	public boolean reading;
	public boolean toRead;
	
	
	public static Finder<Integer,BookUser> find = new Finder<Integer,BookUser>
														(Integer.class,BookUser.class);
}
