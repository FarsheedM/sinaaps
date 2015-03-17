package models;

import java.util.Date;


import javax.persistence.*;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import javax.persistence.JoinColumn;
@Entity
public class BlogComment extends Model{
	
	@Id
	//@SequenceGenerator(name="seq", sequenceName="seq")     
	//(strategy=GenerationType.SEQUENCE, generator="seq")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long commentID;
	@OneToOne
	@JoinColumn(name="post_id", referencedColumnName="post_id")
	public BlogPost post;
	@ManyToOne
	@JoinColumn(name="user_email", referencedColumnName="email")
	public User user;
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date published;
	@Required
	public String comment;
	public int likes;
	
	
	
	public static Finder<Long, BlogComment> find = new Finder<Long, BlogComment>(Long.class, BlogComment.class);

}

