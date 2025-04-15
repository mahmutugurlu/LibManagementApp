package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.dto.BookDTO;
import com.tpe.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController //rest api üretir --> return : JSON (@ResponseBody istekleri JSON formatina cevirir)
//@Controller- dynamic app --> return : ModelAndView, String
@RequestMapping("/books") // ortak bir pad varsa class seviyesinde kullanilabilir,
@RequiredArgsConstructor //final olan fieldler icin otomatik contructor olusturur
public class BookController {

    private final BookService bookService;

    //CREATE
    //1- Save a Book
    // http://localhost:8080/books + POST + body(JSON)
    /*

    "title":"Martin Eden",
    "author":"Jack London",
    "publicationYear":"1970"

     */


    @PostMapping
    public ResponseEntity<String> createBook(@Valid @RequestBody BookDTO bookDTO){

        bookService.saveBook(bookDTO);

        return new ResponseEntity<>("Kitap başarıyla eklendi.", HttpStatus.CREATED);

    }


    //2- Get All Books
    // http://localhost:8080/books + GET
    @GetMapping
    public ResponseEntity<List<Book>> allBooks(){
        List<Book> bookList=bookService.getAllBooks();

        return ResponseEntity.ok(bookList);
    }

    //Ödev: Postman ile 10 tane kitap ekleme


    //3- Get a Book by its ID
    // http://localhost:8080/books/2 + GET
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") Long id){

        BookDTO bookDTO=bookService.getBookDTOById(id);

        return ResponseEntity.ok(bookDTO);//200

    }


    //4- Delete a Book by its ID
    // http://localhost:8080/books/2 + DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){

        bookService.deleteBookById(id);

        return ResponseEntity.ok("Kitap silme işlemi başarılı...");

    }

    //5- Get a Book by its ID with RequestParam
    // http://localhost:8080/books/q?id=2

    //5- Get a Book by its ID with RequestParam
    // http://localhost:8080/books/q?id=2
    @GetMapping("/q")
    public ResponseEntity<BookDTO> getBookByQueryParam(@RequestParam("id") Long id){

        BookDTO bookDTO=bookService.getBookDTOById(id);

        return ResponseEntity.ok(bookDTO);
    }



}
