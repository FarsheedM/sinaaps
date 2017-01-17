package models;
import java.util.Date;
import play.data.format.Formats;
import play.db.ebean.*;
import javax.persistence.*;

@Entity
public class BookEntry extends Model{
	

	@Id
	public String bookTage;
	@OneToOne
	@JoinColumn(name="book_id" ,referencedColumnName="book_id")
	public Book book;
	@OneToOne
	@JoinColumn(name="borrowed_by",referencedColumnName="email")
	public User borrowedBy;
	@Formats.DateTime(pattern = "dd.mm-yyyy")
	public Date borrowedOn;
	public String cellphone;
	public String address;
	public Boolean deposit;
	@ManyToOne
	@JoinColumn(name="library_id",referencedColumnName="library_id")
	public Library library_id;


	
	public static Finder<String,BookEntry> find= new Finder<String,BookEntry>
																	(String.class,BookEntry.class);
	
	
}
