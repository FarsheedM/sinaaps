package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.Formats;
import com.avaje.ebean.Model;


@Entity
public class Library extends Model{

	@Id
	public Integer libraryId;
	public String name;
	@Formats.DateTime(pattern = "dd-mm-yyyy")
	public Date established;
	public String Address;
	public String churchName;


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "library_id")
	public List<BookEntry> bookEntries;
	
	
	
	public static Finder<Integer,Library> find = new Finder<>(Library.class);

}
