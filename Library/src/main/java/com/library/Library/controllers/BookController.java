package com.library.Library.controllers;

import com.library.Library.models.Book;
import com.library.Library.models.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookDao bookDao;

    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping("/books")
    public Iterable<Book> getBooks() {
        System.out.println("Books fetched.");
        return bookDao.findAll();
    }

    @PostMapping("/newBook")
    public HttpStatus postAddBook(@Valid @RequestBody Book book,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
            return HttpStatus.BAD_REQUEST;
        }
        System.out.println("Book added.");
        bookDao.save(book);
        return HttpStatus.OK;
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable("id") int id) {
        System.out.println("Book fetched.");
        return bookDao.searchBookById(id);
    }

    @PutMapping("/editBook/{id}")
    public HttpStatus postEditBook(@PathVariable("id") int id,
                                       @RequestBody Book book) {

        Book current = bookDao.findById(id).get();
        current.setTitle(book.getTitle());
        current.setAuthor(book.getAuthor());
        current.setDate(book.getDate());
        current.setImageUrl(book.getImageUrl());
        bookDao.save(current);
        System.out.println("Book updated.");
        return HttpStatus.OK;
    }

    @DeleteMapping("/deleteBook/{id}")
    public HttpStatus deleteBook(@PathVariable("id") int id) {
        bookDao.deleteById(id);
        System.out.println("Book deleted.");
        return HttpStatus.OK;
    }
}
