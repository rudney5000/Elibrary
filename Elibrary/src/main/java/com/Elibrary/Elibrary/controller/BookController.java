package com.Elibrary.Elibrary.controller;

import com.Elibrary.Elibrary.entity.Book;
import com.Elibrary.Elibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/list-books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("{id}")

    public Book getBook(@PathVariable int id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("THis doesn't exist"));
    }

    @PostMapping
    public Book save(@RequestBody Book book){
        Optional<Book> bookisThere = bookRepository.findBookByName(book.getName());
        if(bookisThere.isPresent()){
            throw new RuntimeException("This book already exist");
        }
        return bookRepository.save(book);

    }

    @PutMapping("/{id}")
    public Book update(@RequestBody Book book, @PathVariable int id){
        Optional<Book> book1 = bookRepository.findById(id);
        if(book1.isPresent()){
            book1.get().setAuthor(book.getAuthor());
            book1.get().setName(book.getName());
            book1.get().setDescription(book.getDescription());
            return bookRepository.save(book1.get());
        }else {
            throw new RuntimeException("You can't edit this book because it doesn't exist in our database");
        }

    }

    @DeleteMapping ("/{id}")
    public String delete(@PathVariable int id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            bookRepository.delete(book.get());
            return "The book has been deleted";
        }else {
            throw new RuntimeException("You can't delete this book because it doesn't exist in our database");
        }

    }
}
