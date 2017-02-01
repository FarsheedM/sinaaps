package models;

import java.util.Date;
import java.util.List;




//import org.apache.openjpa.persistence.jdbc.*;
import javax.persistence.*;

import com.avaje.ebean.PagedList;
import org.joda.time.DateTime;

import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;
import play.data.format.*;

@SuppressWarnings("serial")
@Entity
public class BlogPost extends Model{
	
	@Id
	@Required
	public Integer postID;
	public String title;
	public String content;
	public String image;
	
	@OneToOne
	@JoinColumn(name="author_id", referencedColumnName="author_id")
	public Author author;
	public String topic;
	public String language;
	@Formats.DateTime(pattern="dd-MM-yyyy")
	public Date published;
	public String aux_img1;
	public String aux_img2;
	
	public static Finder<Integer,BlogPost> find = new Finder<>(BlogPost.class);
	
	public BlogPost(int postID,String title,String content,String image, 
						Author author,String topic,String language,String aux_img1,String aux_img2){
		
		this.postID = postID;
		this.title = title;
		this.content = content;
		this.image = image;
		this.author = author;
		this.topic = topic;
		this.language = language;
		this.published= DateTime.now().toDate();
		this.aux_img1 = aux_img1;
		this.aux_img2 = aux_img2;
	}

	
	
    
    /**
     * Return a page of Blogs
     *
     * @param page Page to display
     * @param pageSize Number of computers per page
     * @param sortBy Computer property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
/*    public static com.avaje.ebean.PagedList<BlogPost> page(int page, int pageSize, String sortBy, String order, String filter,String lang) {
        return 
            find.where()
                .ilike("title", "%" + filter + "%")
                .eq("language", lang)
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }*/

	public static PagedList<BlogPost> page(int page, int pageSize, String sortBy, String order, String filter, User user) {
		return
				find.where()
						.eq("user", user)
						.orderBy(sortBy + " " + order)
						.fetch("published")
						.findPagedList(page, pageSize);
	}

    

}
