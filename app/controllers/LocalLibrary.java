package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.ValidationError;
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
		User usr;
		if(session().containsKey("email")){
			usr = User.find.byId(session().get("email"));
		} else {
			usr = new User("Guest","dummyEmail","dummyPassword");
		}

		Library lib;

		try {
			lib = Library.find.byId(libraryId);
		} catch (Exception e) {
			return badRequest(views.html.farsiEdition.alert.render(usr,"error in finding Libraries"));
		}

		return ok(views.html.farsiEdition.localLibrary.render(usr,lib,Form.form(BookEntry.class)));
	}
	
	/*It adds a new book entry in the specified library, in other words adds a new book in the lib.*/
	public static Result addNewBookEntry(Integer libId){

		User usr = User.find.byId(session().get("email"));
		Library lib = Library.find.byId(libId);
		Form<BookEntry> bookEntryForm = Form.form(BookEntry.class).bindFromRequest();
		/*You can use a DynamicForm if you need to retrieve data from an html form that is not related to a Model.
		  This is a way around solution to get 'Book' and 'User' objects from the Request:
		*/
		DynamicForm df = Form.form().bindFromRequest();

		User borrowed_by = null;
		Book bk = null;
		try {

				borrowed_by = User.find.byId(df.get("borrowed_by"));
				bk = Book.find.byId(Integer.parseInt(df.get("bk")));
				bookEntryForm.get().library_id = lib;
				bookEntryForm.get().borrowedBy = borrowed_by;
				bookEntryForm.get().book = bk;

		} catch (IllegalStateException | NumberFormatException e) {
			e.printStackTrace();
			/*this is the custom made validation which is run after the default validation of the binding.
			* This is needed here because the borrowed_by and bk are bound 'manually' which is not a good practice.*/
			List<ValidationError> errors = new ArrayList<>();
			errors.add(new ValidationError("farError", "This is a custom made error"));
			bookEntryForm.errors().put("farError",errors);
			return badRequest(views.html.farsiEdition.localLibrary.render(usr,lib,bookEntryForm));
		}

		if(bookEntryForm.hasErrors()){

			return badRequest(views.html.farsiEdition.localLibrary.render(usr,lib,bookEntryForm));
			//return badRequest(views.html.farsiEdition.alert.render(usr,"error in \'addNewBookEntry()\' "));
		}
		else{
			//Library l = Library.find.byId();
			try {
				bookEntryForm.get().save();
			} catch (Exception e) {
				e.printStackTrace();
			}
			BookEntry newEntry = bookEntryForm.get();
			lib.bookEntries.add(newEntry);
			//lib.saveManyToManyAssociations("bookEntries");
			lib.refresh();
			return ok(views.html.farsiEdition.localLibrary.render(usr,lib,bookEntryForm));
		}

	}
	
	/*It updates the selected Entry with the new info. It serves in the book keeping. */
	public static Result updateBookEntry(){
		User usr = User.find.byId(session().get("email"));

		Form<BookEntry> bookEntryForm = Form.form(BookEntry.class).bindFromRequest();
		DynamicForm df = Form.form().bindFromRequest();
		//Library lib = Library.find.byId(1);
		Library lib = Library.find.byId(Integer.parseInt(df.get("lib_id")));
		User borrowed_by  = User.find.byId(df.get("borrowed_by"));

		/*if the 'borrowed_by' field becomes empty by the admin, it removes the client and it means that the book
		* is free to be borrowed.*/
		if(df.get("borrowed_by").isEmpty()){
			borrowed_by = null;
		} else {
			//if the 'borrowed_by' is null. d.h. no match in User List, the error message will be printed
			String errorMessage = "";
			if(borrowed_by == null){
				errorMessage = "there is no registered user with the given email address. Please check the email address.";
				return badRequest(views.html.farsiEdition.alert.render(usr,errorMessage));
			}
		}


		bookEntryForm.get().borrowedBy = borrowed_by;
		Book bk = Book.find.byId(Integer.parseInt(df.get("bk")));
		bookEntryForm.get().book = bk;

		if(bookEntryForm.hasErrors()){
			return badRequest(views.html.farsiEdition.alert.render(usr,"error in \'updateBookEntry()\' "));
		}
		else{
			bookEntryForm.get().update();
			//return ok(views.html.farsiEdition.alert.render(usr,"update success"));
			return ok(views.html.farsiEdition.localLibrary.render(usr,lib,Form.form(BookEntry.class)));
		}
	}

	public static Result displayEntryEdit(Integer libId, String bookTag){

		User user = User.find.byId(session().get("email"));
		Library lib = Library.find.byId(libId);
		BookEntry bkEntry = BookEntry.find.byId(bookTag);
		return ok(views.html.farsiEdition.editBookEntry.render(user,lib,bkEntry,Form.form(BookEntry.class)));
	}




}
