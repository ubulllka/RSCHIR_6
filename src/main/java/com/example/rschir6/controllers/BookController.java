package com.example.rschir6.controllers;

import com.example.rschir6.models.Book;
import com.example.rschir6.services.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Book getOne(@PathVariable int id) {
        return bookService.getOne(id);
    }

    @PostMapping()
    @ResponseBody
    public String add(@RequestBody Book book){
        bookService.save(book);
        return "Data save.";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable int id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        return bookService.delete(id);
    }
}
