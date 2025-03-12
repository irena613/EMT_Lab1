package mk.ukim.finki.emt.lab.service.impl;

import mk.ukim.finki.emt.lab.model.Author;
import mk.ukim.finki.emt.lab.model.Book;
import mk.ukim.finki.emt.lab.model.enums.Category;
import mk.ukim.finki.emt.lab.model.dto.BookDto;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidBookIdException;
import mk.ukim.finki.emt.lab.repository.BookRepository;
import mk.ukim.finki.emt.lab.service.AuthorService;
import mk.ukim.finki.emt.lab.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public List<Book> listAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(this.bookRepository.findById(id).orElseThrow(InvalidBookIdException::new));
    }

    @Override
    public Book create(String name, Category category, Long id, Long availableCopies) {
        Author author = this.authorService.findById(id);
        Book book = new Book(name, category, author, availableCopies);
        return this.bookRepository.save(book);
    }

    @Override
    public Book edit(Long id, String name, Category category, Long authorId, Long availableCopies) {
        Book book = this.bookRepository.findById(id).orElseThrow(InvalidBookIdException::new);
        book.setName(name);
        book.setCategory(category);
        book.setAuthor(authorService.findById(authorId));
        book.setAvailableCopies(availableCopies);

        return this.bookRepository.save(book);
    }

    @Override
    public Book delete(Long id) {
        return this.bookRepository.findById(id).orElseThrow(InvalidBookIdException::new);
    }

    @Override
    public Optional<Book> isRented(Long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(InvalidBookIdException::new);
        book.setAvailableCopies(book.getAvailableCopies()-1);
        if (book.getAvailableCopies() == 0) {
            return Optional.of(book);
        }
        return Optional.of(this.bookRepository.save(book));
    }

    @Override
    public Optional<Book> create(BookDto book) {
        if (book.getName()!=null && book.getCategory()!=null
        && book.getAuthor()!=null  && book.getAvailableCopies() != null) {
            return Optional.of(this.bookRepository.save(new Book(book.getName(), book.getCategory(), authorService.findById(book.getAuthor()), book.getAvailableCopies())));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Book> edit(Long id, BookDto book) {
        Book existingBook = this.bookRepository.findById(id).orElseThrow(InvalidBookIdException::new);
        if (book.getName()!=null){
            existingBook.setName(book.getName());
        }
        if (book.getCategory()!=null){
            existingBook.setCategory(book.getCategory());
        }
        if (book.getAuthor()!=null){
            existingBook.setAuthor(this.authorService.findById(book.getAuthor()));
        }
        if (book.getAvailableCopies()!=null){
            existingBook.setAvailableCopies(book.getAvailableCopies());
        }
        return Optional.of(bookRepository.save(existingBook));

    }



}
