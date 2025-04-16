package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.dto.BookDTO;
import com.tpe.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    //6- Get a Book by its Title with RequestParam
    //http://localhost:8080/books/search?title=Atomic Habits

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> filterBooksByTitle(@RequestParam("title") String title){

        List<BookDTO> books=bookService.filterBooksByTitle(title);

        return ResponseEntity.ok(books);

    }


    //Get Books by its Title and PublicationYear
    //http://localhost:8080/books/SuçveCeza/1940 + GET
    //http://localhost:8080/books/filter?title=SuçveCeza&publication=1940 + GET
    //repository:JPArepositorynin metodlarını türeterek (title,year)



    //7- Get Books With Page
    // http://localhost:8080/books/sayfa?page=1
    //                              &size=2
    //                              &sort=publicationYear
    //                              &direction=ASC/DESC + GET

    @GetMapping("/sayfa")
    public ResponseEntity<Page<Book>> getAllBooksByPage(@RequestParam(value = "page",defaultValue = "1") int pageNo,
                                                        @RequestParam(value = "size",defaultValue = "2") int size,
                                                        @RequestParam("sort") String prop,//hangi propertye,fielde göre siralayacagini belirtiriz, yazar mi, yil mi, property adini yazarak yapariz
                                                        @RequestParam("direction")Sort.Direction direction)
    {
        Pageable pageable= PageRequest.of(pageNo-1,size,Sort.by(direction,prop));
        Page<Book> bookPage=bookService.getBooksByPagination(pageable);
        return ResponseEntity.ok(bookPage);

    }


    //8- Update a Book With Using DTO
    // http://localhost:8080/books/update/2

    @PatchMapping("/update/{id}")
    public ResponseEntity<Map<String,String>> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO){

        Book updatedbook =bookService.updateBookById(id,bookDTO);

        Map<String,String> response=new HashMap<>();
        response.put("Kitap başarıyla güncellendi", updatedbook.getTitle());

        return new ResponseEntity<>(response,HttpStatus.CREATED);//201

    }
    // 2 den fazla mesaj döndürecegimiz icin map kullandik, update, basari ve degistirlen kitap adini mesaj olarak
    // döndürecegiz



        //9- Get a Book By Its Author Using JPQL
        // http://localhost:8080/books/a?author=AB

    @GetMapping("/author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestParam("author") String author){

        List<BookDTO> books=bookService.getBooksByAuthor(author);

        return ResponseEntity.ok(books);

    }





    //ÖDEV2- Get Books such that Its Author contains word
    // http://localhost:8080/books/contain?author=as + GET




}
