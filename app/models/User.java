package models;

import java.util.Calendar;
import java.util.Date;

import play.data.format.*;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

import javax.persistence.*;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class User extends Model{
	
	@Id
	@Required
	public String email;
	public String fName;
	public String lName;
	public String password;
	@Formats.DateTime(pattern = "yyyy-mm-dd")
	public Date birthdate;
	public String address;
	public String photo;
	
	public User(String fName,String lName,String email,
			String password,Date birthdate,String address,String photo){
		this.fName= fName;
		this.lName= lName;
		this.email= email;
		this.password= password;
		this.birthdate= birthdate;
		this.address= address;
		this.photo = photo;
	}
	//NOTE:this constructor set the address and lastname "unknown" and 
	//birthdate set to the current date of the machine.
	public User(String fName,String email,String password){
		this.fName= fName;
		this.lName= "unknown";
		this.email= email;
		this.password= password;
		this.birthdate=Calendar.getInstance().getTime(); 
		this.address= "unknown";
		this.photo = "noPhoto";
	}
	
	public static Finder<String,User> find = new Finder<String,User>(
	        String.class, User.class
	    );
	
	public static User authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
}
