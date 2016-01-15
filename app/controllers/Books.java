package controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.joda.time.DateTime;

import akka.util.Collections;
import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Books extends Controller{
	
	public static Result display(String lang){
		
		//for the 1st version a list of newly published books will be passed to books.scala.html
		// but in the next versions, a list of featured books should be pass, maybe a change
		//in the Book model should be necessary by adding a a boolean, e.g. "featured".
		List<Book> bookList = Book.find.where().order().desc("published").findList();
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				return ok(books.render(User.find.byId(session().get("email")),bookList));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(books.render(guest,bookList));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.books.render(User.find.byId(session().get("email")),bookList));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.books.render(guest,bookList));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}
	

	public static Result showBookProfile(String lang, Integer bookId){
		
		//book obj contains the general information about the book which is 
		//the same in different translations, e.g. ISBN, No. of Pages.
		Book book = Book.find.where().eq("bookID", bookId).findUnique();
		
		List<Book> listOfBooksWithsameTopic = new ArrayList<Book>();
		try {
			listOfBooksWithsameTopic = getBooksWithSameTopicAsBook(book);
		} catch (Exception e) {
			// TODO: handle exception
		}
		

		
		List<BookReview> reviewList = BookReview.find.where().eq("book_id", bookId).findList();
			
		//This piece of code prepares the 'listOfBooksOfTheAuthor', in other words the list of books by a 
		//specified author. this is populated in the rightPanel under the books by the same author.
		Author author = BookAuthor.getAuthorList(bookId).get(0);
		List<BookAuthor> booksOfTheAuthor = BookAuthor.find.where().eq("author", author).findList();
		List<Book> listOfBooksOfTheAuthor = new ArrayList<Book>();
		Book bk;
		for(BookAuthor i : booksOfTheAuthor){
			bk = Book.find.byId(i.book.bookID);
			listOfBooksOfTheAuthor.add(bk);
		}
		
		
		if(lang.equals("english")){
			if(session().containsKey("email"))
				//to update in English version!!
				return ok(views.html.farsiEdition.bookProfile.render(
							User.find.byId(session().get("email")),book, Form.form(BookReview.class),
							reviewList,listOfBooksOfTheAuthor,listOfBooksWithsameTopic));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.bookProfile.render(guest,book, Form.form(BookReview.class),
						reviewList,listOfBooksOfTheAuthor,listOfBooksWithsameTopic));
			}	
		}else if(lang.equals("farsi")){
			
			if(session().containsKey("email"))
				return ok(views.html.farsiEdition.bookProfile.render(
							User.find.byId(session().get("email")),book, Form.form(BookReview.class),
							reviewList,listOfBooksOfTheAuthor,listOfBooksWithsameTopic));
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.bookProfile.render(guest,book, Form.form(BookReview.class),
						reviewList,listOfBooksOfTheAuthor,listOfBooksWithsameTopic));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}
	
	//this method is called when the user post the new review(in bookProfile view)
	//It adds the review to the BookReview model/Table
	public static Result postReview(String language,int bookId){
		
		Form<BookReview> reviewForm = Form.form(BookReview.class).bindFromRequest();
		
		if(reviewForm.hasErrors()){
			return badRequest("error in reviewForm !!");
		}
		
		reviewForm.get().book= Book.find.byId(bookId);
		reviewForm.get().user= User.find.byId(session().get("email"));
		reviewForm.get().published = DateTime.now().toDate();
		//commentForm.get().likes = 0 ;
		


		//reload the page just for now.
		// the AJAX call should be used here to add the new comment
		reviewForm.get().save();
		return redirect(routes.Books.showBookProfile(language,bookId));
	}
	
	public static Result deleteReview(long reviewID,String lang, int bookId){
		BookReview.find.ref(reviewID).delete();
		return redirect(routes.Books.showBookProfile(lang,bookId));
	}

	
	public static Result showByTopic(String lang, Integer topicId){
		
		// topicBooks holds a list of books with the same topic as topicIds
		List<Book> topicBooks = new ArrayList<Book>();
		List<TopicBook> tpbk = TopicBook.find.where().eq("topic_id", topicId).findList();
		Book bk;
		for(TopicBook i : tpbk){
			bk = Book.find.byId(i.book.bookID);
			topicBooks.add(bk);
		}
		
		Topic topic = Topic.find.byId(topicId);
		
		List<Book> newBooks = new ArrayList<Book>(topicBooks);
		java.util.Collections.sort(newBooks, new PublishedCompare());
		//newBooks = newBooks.subList(0, 6);   DEVELOPEMENT::there are no 6 item in the list right now!!
		
		
		
		//List<Book> newBooks = Book.find.where().order().desc("published").findList();
		//newBooks = newBooks.subList(0, 6);
		
		
		//----- The code below should be corrected as above ------//
		
		
		//books which are recommended by farsiReads
		List<Book> recommendedBooks = new ArrayList<Book>(topicBooks);
		java.util.Collections.sort(recommendedBooks, new FRRecommendCompare());
		//recommendedBooks = recommendedBooks.subList(0, 6);  DEVELOPEMENT::there are no 6 item in the list right now!!
		
		//books recommended by users
		List<Book> popularBooks = new ArrayList<Book>(topicBooks);
		java.util.Collections.sort(popularBooks, new UsersRecommendCompare());
		//popularBooks = popularBooks.subList(0, 6);   DEVELOPEMENT::there are no 6 item in the list right now!!
		
		
		//here is to changed in the English version!!
		if(lang.equals("english")){
			if(session().containsKey("email")){
				User usr = User.find.byId(session().get("email"));
				return ok(views.html.farsiEdition.topic.render(usr,topic,newBooks,
																recommendedBooks,popularBooks));}
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.topic.render(guest,topic,newBooks,
						recommendedBooks,popularBooks));
			}	
		}else if(lang.equals("farsi")){
			if(session().containsKey("email")){
				User usr = User.find.byId(session().get("email"));
				return ok(views.html.farsiEdition.topic.render(usr,topic,newBooks,
						recommendedBooks,popularBooks));}
			else{
				User guest = new User("Guest","dummyEmail","dummyPassword");
				return ok(views.html.farsiEdition.topic.render(guest,topic,newBooks,
						recommendedBooks,popularBooks));
			}
		}
		else{
			//if neither english nor farsi is selected
			return badRequest("ERROR : The entered Language is not supported! PLease choose either Farsi or English");
		}
	}
	
	//defining Comparator classes in order to be used in sort functions in showByTopic()
	public static class PublishedCompare implements Comparator<Book>{
		public int compare(Book a, Book b){
			return a.published.compareTo(b.published);
		}
	}
	public static class FRRecommendCompare implements Comparator<Book>{
		public int compare(Book a, Book b){
			return Integer.compare(a.farsireadsRating, b.farsireadsRating);
		}
	}
	public static class UsersRecommendCompare implements Comparator<Book>{
		public int compare(Book a, Book b){
			return Integer.compare(a.userRating, b.userRating);
		}
	}


	/*This method is used in 'bookProfile.scala.html'. Giving the bookId as a parameter, it
	 *returns a list of books with the same topic. It finds the books topic first, then queries 
	 *the Book DB for the books with that specific topic. */
	public static List<Book> getBooksWithSameTopicAsBook(Book book){
		
		Integer topicId = TopicBook.find.where().eq("book", book).findList().get(0).topic.topicID;
		// topicBooks holds a list of books with the same topic as topicId
		List<Book> topicBooks = new ArrayList<Book>();
		List<TopicBook> tpbk = TopicBook.find.where().eq("topic_id", topicId).findList();
		Book bk;
		for(TopicBook i : tpbk){
			bk = Book.find.byId(i.book.bookID);
			topicBooks.add(bk);
		}
		return topicBooks;
	} 
	
}
