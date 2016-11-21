package models;

import java.util.*;

import javax.persistence.*;

import play.data.format.Formats;
import play.db.ebean.Model;


@Entity
public class Library extends Model{
	
	@Id
	public Integer libraryId;
	public String name;
	@Formats.DateTime(pattern = "dd.mm-yyyy")
	public Date established;
	public String Address;
	public String churchName;
	@ManyToMany(cascade = CascadeType.ALL)
	public List<BookEntry> bookEntries;
	
	
	
	public static Finder<Integer,Library> find = new Finder<Integer,Library>(Integer.class,Library.class);
																	

}
