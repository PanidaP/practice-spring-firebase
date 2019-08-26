package com.example.springfirebasedemo.controllers;

import com.example.springfirebasedemo.models.Book;
import com.example.springfirebasedemo.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book)
            throws ExecutionException, InterruptedException {
        libraryService.addBookToRepo(book);
        return new ResponseEntity<Book>(book, OK);
    }

    @GetMapping
    public ResponseEntity<List< Book >> readBook()
            throws ExecutionException, InterruptedException {
        List< Book > data = libraryService.readAllBook();
        return new ResponseEntity<List< Book >>(data, OK);
    }

    @GetMapping(value = "/{code}")
    public ResponseEntity<Book> readBookByCode(@PathVariable("code") String code)
            throws ExecutionException, InterruptedException {
        Book dataByCode = libraryService.readBookByCode(code);
        if (dataByCode != null){
            return new ResponseEntity<Book>(dataByCode, OK);
        }
        return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/update/{code}")
    @ResponseStatus(OK)
    public ResponseEntity<Book> updateBook(@PathVariable( "code" ) String code, @RequestBody Book book)
            throws ExecutionException, InterruptedException {
        Book bookUpdate = libraryService.updateBook(code,book);
        if (bookUpdate != null){
            return new ResponseEntity<Book>(bookUpdate, OK);
        }
        return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteBook(@PathVariable("code") String code)
            throws ExecutionException, InterruptedException {
        boolean delete = libraryService.deleteBook(code);
        if (delete == true){
            return new ResponseEntity<String>("Just Delete",HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping(value = "/search/field={field}/value={value}")
//    public ResponseEntity<List<Book>> searchBook(@PathVariable("field") String field,@PathVariable("value") String value)
//            throws ExecutionException, InterruptedException {
//        List<Book> bookSearch = libraryService.searchBook(field,value);
//        return new ResponseEntity<List< Book>>(bookSearch, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/sort/field={field}")
//    public ResponseEntity<List<Book>> sortBook(@PathVariable("field") String field)
//            throws ExecutionException, InterruptedException {
//        List<Book> bookSort = libraryService.sortBook(field);
//        return new ResponseEntity<List< Book>>(bookSort, HttpStatus.OK);
//    }
}
