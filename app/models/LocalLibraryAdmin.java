package models;
import play.db.ebean.*;
import javax.persistence.*;

@Entity
public class LocalLibraryAdmin extends Model{
	
	@Id
	public Integer adminId;
	@OneToMany
	@JoinColumn(name="admin",referencedColumnName="email")
	public User admin;
	@JoinColumn(name="library",referencedColumnName="library_id")
	public Library library;
	
	
	public static Finder<Integer,LocalLibraryAdmin> find = new Finder<Integer,LocalLibraryAdmin>
																	(Integer.class,LocalLibraryAdmin.class);

}
