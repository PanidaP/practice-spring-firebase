package com.example.springfirebasedemo.services;

import com.example.springfirebasedemo.configs.FirebaseConfig;
import com.example.springfirebasedemo.models.Book;
import com.example.springfirebasedemo.models.History;
import com.example.springfirebasedemo.models.enumclass.Category;
import com.example.springfirebasedemo.models.enumclass.Status;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class LibraryService {
    @Autowired
    FirebaseConfig firebase;

    Firestore db;

    List<Book> books = new ArrayList<>();

    List<History> histories = new ArrayList<>();

    public LibraryService() throws IOException {
        firebase = new FirebaseConfig();
        this.db = firebase.initializeDB();
    }

    //===================================== Book Repository Part =====================================//

    public void addBook(Book book){
        ApiFuture<DocumentReference> addedDocRef = db.collection("books").add(book);
    }

    public Book updateBook(String code,Book book) throws ExecutionException, InterruptedException {
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = db.collection("books").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (document.getString("code").equals(code)){
                document.getReference().update("name",book.getName());
                document.getReference().update("code",book.getCode());
                document.getReference().update("category",book.getCategory());
                document.getReference().update("status",book.getStatus());
                document.getReference().update("author",book.getAuthor());
                document.getReference().update("recap",book.getRecap());
                return book;
            }
        }
        return null;
    }

    public List< Book > readAllBook() throws ExecutionException, InterruptedException {

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = db.collection("books").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            books.add(document.toObject(Book.class)) ;
        }
        return books;
    }

    public Book readBookByCode(String code) throws ExecutionException, InterruptedException {
        Book book = new Book();
        ApiFuture<QuerySnapshot> future = db.collection("books").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (document.getString("code").equals(code)){
                book = document.toObject(Book.class);
            }
        }
        return book;
    }

    public boolean deleteBook(String code) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection("books").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (document.getString("code").equals(code)){
                document.getReference().delete();
                return true;
            }
        }
        return false;
    }

    //===================================== History Repository Part =====================================//
    public void addHistory(History history){
        ApiFuture<DocumentReference> addedDocRef = db.collection("histories").add(history);
    }

    public History updateHistory(String code,History history) throws ExecutionException, InterruptedException {
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = db.collection("histories").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (document.getString("bookCode").equals(code)){
                document.getReference().update("bookCode",history.getBookCode());
                document.getReference().update("code",history.getCustomerBorrow());
                document.getReference().update("category",history.getDateBorrow());
                document.getReference().update("status",history.getDateReturn());
                document.getReference().update("author",history.getLibrarianAccept());
                document.getReference().update("recap",history.getLibrarianApprove());
                return history;
            }
        }
        return null;
    }

    public List< History > readAllHistory() throws ExecutionException, InterruptedException {
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = db.collection("histories").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            histories.add(document.toObject(History.class)) ;
        }
        return histories;
    }

    public List< History > readHistoryByCode(String code, History history) throws ExecutionException, InterruptedException {
        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = db.collection("histories").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (history.getBookCode().equals(code)){
                histories.add(document.toObject(History.class)) ;
            }
        }
        return histories;
    }

    public boolean deleteHistory(String code) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection("histories").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (document.getString("bookCode").equals(code)){
                document.getReference().delete();
                return true;
            }
        }
        return false;
    }

    //===================================== Library Service Part =====================================//

    public List<Book> searchBook(String field , String value) throws ExecutionException, InterruptedException {
        List<Book> books = new ArrayList<>();
        //asynchronously retrieve multiple documents
        ApiFuture<QuerySnapshot> future = db.collection("books").whereEqualTo(field, value).get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (DocumentSnapshot document : documents) {
            books.add(document.toObject(Book.class)) ;
        }
        return books;
    }

    public List<Book> sortBook(String field) throws ExecutionException, InterruptedException {
        List<Book> books = new ArrayList<>();
        CollectionReference colRef = db.collection("books");
        Query query = colRef.orderBy(field);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            books.add(document.toObject(Book.class)) ;
        }
        return books;
    }

    public void addBookToRepo(Book book) throws ExecutionException, InterruptedException {
        String code = generateCode(book.getCategory());
        book.setCode(code);
        book.setStatus(String.valueOf(Status.available));
        addBook(book);
    }

    public String generateCode(String code) throws ExecutionException, InterruptedException {
        String categoryCode = Category.valueOf(code).getCatCode();
        DecimalFormat decimalFormat = new DecimalFormat("000");

        Integer runNumber = null;

        int size = db.collection("books").get().get().size();
        if (size != 0){
            //asynchronously retrieve all documents
            ApiFuture<QuerySnapshot> future = db.collection("books").get();
            // future.get() blocks on response
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String codeFormDoc = document.get("code").toString().substring(0,2);
                if (categoryCode.equals(codeFormDoc)){
                    if (runNumber == null || runNumber < Integer.parseInt(document.get("code").toString().substring(2))){
                        runNumber = Integer.parseInt(document.get("code").toString().substring(2));
                    } else {
                        if (runNumber == null){
                            runNumber = 0;
                        }
                    }
                }
            }
        } else {
            runNumber = 0;
        }
        runNumber = runNumber+1;
        String codeGen = categoryCode + decimalFormat.format(runNumber);
        return codeGen;
    }
}
