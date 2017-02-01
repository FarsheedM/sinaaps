package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.joda.time.DateTime;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;
import play.data.format.*;


//This Class is used to implement the many to many relationship 
//for the Book and User classes. It is used in the Virtual Library.
@Entity
public class BookUser extends Model{
	
	/*This constructor is needed for the 'addtoShelfAll()' method in Books controller.
	  the primary key ID should be built out of the two foreign keys.*/
	public BookUser(Book bk, User usr, boolean b, boolean c, boolean d) {
		
		String uniqueId = Long.toString((usr.email).hashCode()/1000).concat(bk.bookID.toString());
		this.id = Long.valueOf(uniqueId); 
		this.book = bk;   
		this.user = usr;
		//set to the current date of the machine
		this.addedToVl = DateTime.now().toDate();//Calendar.getInstance().getTime();
		this.finished = b;
		this.reading = c;
		this.toRead = d;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	@OneToOne
	@JoinColumn(name="book_id", referencedColumnName="book_id")
	public Book book;
	
	@OneToOne
	@JoinColumn(name="user_email", referencedColumnName="email")
	public User user;
	@Formats.DateTime(pattern = "yyyy-mm-dd")
	public Date addedToVl;
	public boolean finished;
	public boolean reading;
	public boolean toRead;
	//default value is 0, indicates the unrated
	public int bookRating;
	
	
	public static Finder<Long,BookUser> find = new Finder<>
														(BookUser.class);
	
	
	//This method checks the availability of a book given by its ID
	public static boolean bookAlreadyExists(String usr, int bookId){
		
		Book bk = Book.find.byId(bookId);
		User ur = User.find.byId(usr);
		BookUser bu = BookUser.find.where().eq("book", bk).eq("user", ur).findUnique();
		if(bu != null){
			return true;
		}
		else
		    return false;
	}
	//The list of the books of the given user
	public static List<Book> userBookList(User usr){
		
		List<BookUser> buList;
		try {
			buList = BookUser.find.where().eq("user", usr).findList();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			buList = null;
		}
		List<Book> bookLst = new ArrayList<Book>();
		if(buList != null){	
			for(BookUser bu : buList){
				bookLst.add(bu.book);
			}
		}
		
		return bookLst;
	} 
	//to find out the status of the book if available
	public static String readingStatus(User usr,Book bk){
		String status = "";
		BookUser bu;
		try {
			bu = BookUser.find.where().eq("user", usr).eq("book", bk).findUnique();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bu = null;
		}
		if(bu != null){
			if(bu.finished){
				status = "finished";
			} else if(bu.reading){
				status = "reading";
			} else if(bu.toRead){
				status = "toRead";
			} else
				status = "";
		}
		
		return status;
	}
	
}
