package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.dto.BookDTO;
import com.tpe.exceptions.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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





}
