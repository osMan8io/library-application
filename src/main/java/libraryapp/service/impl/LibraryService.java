package libraryapp.service.impl;

import jakarta.annotation.PostConstruct;
import libraryapp.entity.Book;
import libraryapp.enums.Category;
import libraryapp.service.Searchable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LibraryService implements Searchable {


    public List<Book> books;
    private int nextId = 1;

    public LibraryService(List<Book> books) {
        this.books = books;
    }

    @PostConstruct
    public void initData() {
        books = new ArrayList<>();
        books.add(new Book(1,"Sicher","Heuber",Category.EDUCATION));
        books.add(new Book(2,"spider-man","Marvell", Category.COMIC));
        books.add(new Book(3,"Romeo & Juliet","Youth",Category.ROMAN));
        books.add(new Book(4,"The Duned City","Okya",Category.ROMAN));
        books.add(new Book(5,"ABC English","Oxford",Category.EDUCATION));
        books.add(new Book(6,"Spring Boot","Spring",Category.EDUCATION));
        books.add(new Book(7,"Gone","Hunter",Category.FICTION));
        nextId = books.stream().mapToInt(Book::getId).max().orElse(0) +1; // Dynamic
    }

    public Book addBook(Book book) {

        if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Book title or author cannot be empty");
        }

        book.setId(nextId++);
        books.add(book);
        return book;
    }

    public Book removeBook(int bookId) {
        Book deletedBook = null;

        for (int i = 0; i < books.size(); i++) {
            if (Objects.equals(books.get(i).getId(), bookId)) {
                deletedBook = books.get(i);
                books.remove(i);
                break;
            }
        }
        return deletedBook;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book updateBook(int bookId, Book book) {

        if (book.getTitle().isEmpty() || book.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Book title or author cannot be empty");
        }

        if (!book.getCategory().equals(Category.values()[book.getCategory().ordinal()])) {
            throw new IllegalArgumentException("Book category does not match please choose from category" + book.getCategory());
        }

        Book updatedBook = null;
        for (int i = 0; i < books.size(); i++) {
            /*if (books.get(i).getId() == bookId) {
                updatedTitle = books.get(i).getTitle();
                updatedId = books.get(i).getId();
                updatedAuthor = books.get(i).getAuthor();
                updatedCategory = books.get(i).getCategory();
                break;*/
            if (books.get(i).getId() == bookId) {
                books.get(i).setTitle(book.getTitle());
                books.get(i).setAuthor(book.getAuthor());
                books.get(i).setCategory(book.getCategory());
                updatedBook = books.get(i);
                }
            }

        return updatedBook;
    }

    @Override
    public Book searchByTitle(String title) {
        Book foundBook = null;

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().trim().equalsIgnoreCase(title)) {
                foundBook = books.get(i);
            }
        }

        return foundBook;
    }

    @Override
    public Book searchByAuthor(String author) {

        Book foundBook = null;

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getAuthor().trim().equalsIgnoreCase(author)) {
                foundBook = books.get(i);
            }
        }

        return foundBook;
    }

    @Override
    public Book searchById(int bookId) {

        Book foundBook = null;
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == bookId) {
                foundBook = books.get(i);
            }
        }
        return foundBook;
    }

    @Override
    public List<Book> searchByCategory(String category) {
        List<Book> searchedBooks = new ArrayList<>();

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getCategory().equals(Category.valueOf(category.toUpperCase()))) {
                searchedBooks.add(books.get(i));
            }
        }

        return searchedBooks;
    }
}
