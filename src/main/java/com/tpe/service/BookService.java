package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.dto.BookDTO;
import com.tpe.exceptions.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service //burada gelen requestler ile ilgili logic islemler yapilacak
@RequiredArgsConstructor //final olan fieldler icin otomatik contructor olusturur
public class BookService {

    private final BookRepository bookRepository;


    //1-b
    public void saveBook(BookDTO bookDTO) {
        //bookDTO-->entity
        Book book=new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublicationYear(bookDTO.getPublicationYear());
        //owner-->null
        bookRepository.save(book);
    }



    //2-b
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }



    //3-b
    public Book getBookById(Long id){
        Book book=bookRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Book not found by id: "+id));

        return book;
    }


    //3-c
    public BookDTO getBookDTOById(Long id) {
        //alternatif: repodan doğrudan JPQL ile DTO objesi döndürebiliriz.
        //book->bookDTO
        Book book=getBookById(id);
        return new BookDTO(book);

    }


    //4-b
    public void deleteBookById(Long id) {
        //id ile book var mı, yoksa custom exception fırlatalım
        getBookById(id);
        bookRepository.deleteById(id);
    }


    //6-b

    public List<BookDTO> filterBooksByTitle(String title) {
        List<Book> books=bookRepository.findByTitle(title);
        if (books.isEmpty()){
            throw new ResourceNotFoundException("Bu isimde bir kitap bulunamadı!!! Kitap adı : "+title);
        }
        return books.stream().map(book -> new BookDTO(book)).collect(Collectors.toList());
    }

    //DTO classlar service katmanina kadar kullanilir,



    //7-b
    public Page<Book> getBooksByPagination(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    //8-b
    public Book updateBookById(Long id, BookDTO bookDTO) {
        Book foundBook=getBookById(id);
        foundBook.setTitle(bookDTO.getTitle());
        foundBook.setAuthor(bookDTO.getAuthor());
        foundBook.setPublicationYear(bookDTO.getPublicationYear());
        bookRepository.save(foundBook);//merge: update .. set
        return foundBook;

    }


    //9-b
    public List<BookDTO> getBooksByAuthor(String author) {

        List<Book> books=bookRepository.filterBooksByAuthor(author);
        return books.stream().map(book -> new BookDTO(book)).collect(Collectors.toList());
    }



}
