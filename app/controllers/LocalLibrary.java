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
		Library lib;

		try {
			lib = Library.find.byId(libraryId);
		} catch (Exception e) {
			return badRequest(views.html.farsiEdition.alert.render(usr,"error in finding Libraries"));
		}

		return ok(views.html.farsiEdition.localLibrary.render(usr,lib));
	}
	
	/*It adds a new book entry in the specified library, in other words adds a new book in the lib.*/
	public static void addNewBookEntry(Library lib,BookEntry newEntry){
		lib.bookEntries.add(newEntry);
		//lib.saveManyToManyAssociations("bookEntries");
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
