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
	@Required
	public String fName;
	public String lName;
	@Required
	public String password;
	//@Formats.DateTime(pattern = "yyyy-mm-dd")
	//public Date birthdate;
	public int day;
	public int month;
	public int year;
	public String address;
	public String photo;
	//false is male , and true female
	@Required
	public boolean gender;
	
	public User(String fName,String lName,String email,
			String password,int d,int m,int y,String address,String photo,boolean gender){
		this.fName= fName;
		this.lName= lName;
		this.email= email;
		this.password= password;
		//this.birthdate= birthdate;
		this.day=d;
		this.month=m;
		this.year=y;
		this.address= address;
		this.photo = photo;
		this.gender=gender;
	}
	//NOTE:this constructor set the address and lastname "unknown" and 
	//birthdate set to the current date of the machine.
	public User(String fName,String email,String password){
		this.fName= fName;
		this.lName= "unknown";
		this.email= email;
		this.password= password;
		//this.birthdate=Calendar.getInstance().getTime();
		this.day=1;
		this.month=1;
		this.year=1900;
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
