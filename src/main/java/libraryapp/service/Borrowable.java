package libraryapp.service;

public interface Borrowable {

    void borrowBook(int bookId);
    void returnBook(int bookId);

}
