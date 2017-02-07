package models;

import javax.persistence.*;
import com.avaje.ebean.Model;

import java.util.List;

@Entity
public class LocalLibraryAdmin extends Model{
	
	@Id
	public Integer adminId;
	@OneToOne
	@JoinColumn(name="admin_email",referencedColumnName="email")
	public User admin;
	@OneToOne
	@JoinColumn(name="library_id",referencedColumnName="library_id")
	public Library library;
	
	
	public static Finder<Integer,LocalLibraryAdmin> find = new Finder<>
																	(LocalLibraryAdmin.class);


	public static boolean isAdmin(User user){

		if(LocalLibraryAdmin.find.findRowCount() == 0){
			//There is no Admin registered
			return false;
		} else if( LocalLibraryAdmin.find.where().eq("admin",user).findList().size() == 0){
			return false;
		} else {
			return true;
		}

	}

}
