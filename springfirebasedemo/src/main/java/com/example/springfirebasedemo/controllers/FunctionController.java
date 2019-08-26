package com.example.springfirebasedemo.controllers;

import com.example.springfirebasedemo.models.Book;
import com.example.springfirebasedemo.models.History;
import com.example.springfirebasedemo.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/function")
public class FunctionController {
    @Autowired
    private LibraryService libraryService;

    @PostMapping("/add")
    public ResponseEntity<History> addBook(@RequestBody History history) {
        libraryService.addHistory(history);
        return new ResponseEntity<History>(history, OK);
    }

    @GetMapping
    public ResponseEntity<List< History >> readBook()
            throws ExecutionException, InterruptedException {
        List< History > data = libraryService.readAllHistory();
        return new ResponseEntity<List< History >>(data, OK);
    }

    @GetMapping(value = "/{history}/{code}")
    public ResponseEntity<List<History>> readBookByCode(@PathVariable("code") String code,@PathVariable("history") History history)
            throws ExecutionException, InterruptedException {
        List<History> dataByCode = libraryService.readHistoryByCode(code,history);
        if (dataByCode != null){
            return new ResponseEntity<List<History>>(dataByCode, OK);
        }
        return new ResponseEntity<List<History>>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/update/{code}")
    @ResponseStatus(OK)
    public ResponseEntity<History> updateBook(@PathVariable( "code" ) String code, @RequestBody History history)
            throws ExecutionException, InterruptedException {
        History updateHistory = libraryService.updateHistory(code,history);
        if (updateHistory != null){
            return new ResponseEntity<History>(updateHistory, OK);
        }
        return new ResponseEntity<History>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteBook(@PathVariable("code") String code)
            throws ExecutionException, InterruptedException {
        boolean delete = libraryService.deleteHistory(code);
        if (delete == true){
            return new ResponseEntity<String>("Just Delete",HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/search/field={field}/value={value}")
    public ResponseEntity< List< Book > > searchBook(@PathVariable("field") String field, @PathVariable("value") String value)
            throws ExecutionException, InterruptedException {
        List<Book> bookSearch = libraryService.searchBook(field,value);
        return new ResponseEntity<List< Book>>(bookSearch, HttpStatus.OK);
    }

    @GetMapping(value = "/sort/field={field}")
    public ResponseEntity<List<Book>> sortBook(@PathVariable("field") String field)
            throws ExecutionException, InterruptedException {
        List<Book> bookSort = libraryService.sortBook(field);
        return new ResponseEntity<List< Book>>(bookSort, HttpStatus.OK);
    }
}
