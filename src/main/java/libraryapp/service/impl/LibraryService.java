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

        if (book.getTitle().trim().isEmpty() || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        book.setId(nextId++);
        books.add(book);
        return book;
    }

    public Book removeBook(int bookId) {
        Book deletedBook = null;

        if (bookId <= 0 || bookId > books.size()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < books.size(); i++) {
            if (Objects.equals(books.get(i).getId(), bookId)) {
                deletedBook = books.get(i);
                books.remove(i);
                break;
            } else {
                throw new IllegalArgumentException();
            }
        }
        return deletedBook;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book updateBook(int bookId, Book book) {

        if (book.getTitle().trim().isEmpty() || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        Book updatedBook = null;
        for (int i = 0; i < books.size(); i++) {
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

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No book found with title: " + title));
    }

    @Override
    public Book searchByAuthor(String author) {

        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No book found with author: " + author));
    }

    @Override
    public Book searchById(int bookId) {

        if (bookId <= 0) {
            throw new IllegalArgumentException("Invalid book id");
        }

        return books.stream()
                .filter(book -> book.getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No book found with id: " + bookId));

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
