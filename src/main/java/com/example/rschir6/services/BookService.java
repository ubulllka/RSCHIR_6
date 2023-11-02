package com.example.rschir6.services;

import com.example.rschir6.models.Book;
import com.example.rschir6.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getOne(int id) {
        return bookRepository.findById(id).get();
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public String update(int id, Book newBook){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()){
            return "Data not update.";
        }
        newBook.setId(id);
        bookRepository.save(newBook);
        return "Data update.";
    }

    public String delete(int id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()){
            return "Data not found";
        }
        bookRepository.delete(optionalBook.get());
        return "Data delete.";
    }
}
