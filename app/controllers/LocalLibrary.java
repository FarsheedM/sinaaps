package controllers;

import play.data.Form;
import play.mvc.*;
import play.mvc.Controller.*;
import views.html.signUp;
import models.*;

import java.util.*;

public class LocalLibrary extends Controller{
	
	/**
	 * @param libraryId
	 * @return
	 */
	public static Result display(Integer libraryId){
		User usr = User.find.byId(session().get("email"));
		Library lib = Library.find.byId(libraryId);
		//getAllBookEntriesIn(lib);
		List<BookEntry> test = new ArrayList<BookEntry>();
		BookEntry b =new BookEntry();
		try {
			b = BookEntry.find.byId("ss4");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return badRequest(views.html.farsiEdition.alert.render(usr,"error in line 22"));
		}
		test.add(b);
		//return ok(views.html.farsiEdition.localLibrary.render(usr,test));
		return ok(views.html.farsiEdition.localLibrary.render(usr,getAllBookEntriesIn(lib)));
	}
	
	public static List<BookEntry> getAllBookEntriesIn(Library lib){
		return lib.bookEntries;
	}
	
	/*It adds a new book entry in the specified library, in other words adds a new book in the lib.*/
	public static void addNewBookEntry(Library lib,BookEntry newEntry){
		lib.bookEntries.add(newEntry);
		lib.saveManyToManyAssociations("bookEntries");
		lib.update();
	}
	
	/*It updates the selected Entry with the new info. It serves in the book keeping. */
	public static Result updateBookEntry(){
		User usr = User.find.byId(session().get("email"));
		Form<BookEntry> bookEntryForm = Form.form(BookEntry.class).bindFromRequest();
		if(bookEntryForm.hasErrors()){
			return badRequest(views.html.farsiEdition.alert.render(usr,"error in \'updateBookEntry()\' "));
		}
		else{
			bookEntryForm.get().save();
			return ok(views.html.farsiEdition.alert.render(usr,"update success"));
		}
	}

}
