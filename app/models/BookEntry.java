package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.format.Formats;
import javax.persistence.*;
import com.avaje.ebean.Model;
import play.data.validation.Constraints.Required;
import play.data.validation.Constraints.*;
import play.data.validation.ValidationError;

@Entity
public class BookEntry extends Model{


	@Required
	@Id
	public String bookTage;

	@OneToOne
	@JoinColumn(name="book_id" ,referencedColumnName="book_id")
	public Book book;

	@OneToOne
	@JoinColumn(name="borrowed_by",referencedColumnName="email")
	public User borrowedBy;

	@Formats.DateTime(pattern = "yyyy-MM-dd")
	public Date borrowedOn;
	public String cellphone;
	public String address;
	public Boolean deposit;
	@OneToOne
	@JoinColumn(name="library_id",referencedColumnName="library_id")
	public Library library_id;


	
	public static Finder<String,BookEntry> find= new Finder<>(BookEntry.class);

	
	
}
