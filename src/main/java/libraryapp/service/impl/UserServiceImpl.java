package libraryapp.service.impl;

import libraryapp.dto.BookDTO;
import libraryapp.dto.UserDTO;
import libraryapp.entity.Book;
import libraryapp.entity.User;
import libraryapp.repository.BookRepository;
import libraryapp.repository.UserRepository;
import libraryapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookServiceImpl bookServiceImpl;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BookRepository bookRepository, BookServiceImpl bookServiceImpl) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bookServiceImpl = bookServiceImpl;
        this.bookRepository = bookRepository;
    }

    public void addUser(UserDTO userDTO) {
        userRepository.save(modelMapper.map(userDTO, User.class));
    }

    @Override
    public List<User> readAllUsers() {
        return userRepository.findAllUsersOrderByIdAsc();
    }

/*    @Override
    public void borrowBook(Long userId, Long bookId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        if (!bookRepository.existsById(bookId)) {
            throw new RuntimeException("Book not found");
        }
        userRepository.addBorrowedBookById(userId, bookId);
    }

    @Override
    public void returnBook(Long userId, Long bookId) {
        if (!userRepository.existsById(bookId)) {
            throw new RuntimeException("User not found");
        }
        if (!bookRepository.existsById(bookId)) {
            throw new RuntimeException("Book not found");
        }
        userRepository.removeBorrowedBookById(userId, bookId);
    }*/

    @Override
    public void borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getBorrowedBy() != null) {
            throw new RuntimeException("Book is already borrowed");
        }
        book.setBorrowedBy(user);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getBorrowedBy() != null) {
            book.setBorrowedBy(null);
            bookRepository.save(book);
        }
    }

    @Override
    public void updateUserPasswordById(Long userId, String password) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User does not exist");
        }
        userRepository.updateUserPasswordById(userId, password);
    }

    @Override
    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User does not exist");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public User userLogin(String username, String password) {
        return modelMapper.map(userRepository.findUserByUsernameAndPassword(username, password), User.class);
    }

    @Override
    public User readUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User does not exist");
        }
        return userRepository.getUserIdById(userId);
    }

/*    @Override
    public void addBookToUser(Long userId, BookDTO bookDTO) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User does not exist");
        }
        Book bookToAdd = modelMapper.map(bookDTO, Book.class);
        userRepository.addBorrowedBookById(userId, bookToAdd.getId());
    }*/

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return bookServiceImpl.searchByTitle(title);
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        return bookServiceImpl.searchByAuthor(author);
    }

    @Override
    public List<Book> searchBooksByCategory(String category) {
        return bookServiceImpl.searchByCategory(category);
    }

    @Override
    public List<Book> readAllBooksByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User does not exist");
        }

        List<Book> borrowedBooks = userRepository.findAllBorrowedBooksByUserId(userId);

        if (!borrowedBooks.isEmpty()) {
            return borrowedBooks;
        } else {
            return bookServiceImpl.getAvailableBooks();
        }
    }

}

