package models;


/*this interface is used as the abstract observer/listener from which concrete listeners
 *(e.g. BlogComment, BookReview, Relationship, ..) are inherited.*/
public interface DeleteUserListener {
	public void deleteUser(User userToBeDeleted);
}
