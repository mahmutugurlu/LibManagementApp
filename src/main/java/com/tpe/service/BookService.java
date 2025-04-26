package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.domain.Owner;
import com.tpe.dto.BookDTO;
import com.tpe.dto.OwnerDTO;
import com.tpe.exceptions.ConflictException;
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
    private final OwnerService ownerService;


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
    public Page<BookDTO> getBooksByPagination(Pageable pageable) {
        return bookRepository.findAll(pageable).map(book -> new BookDTO(book));
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


    //ödev2-b
    public List<BookDTO> getBooksByAuthorContain(String author) {

        List<Book> books=bookRepository.findAllByAuthorContaining(author) ;
        return getBookDTOList(books);
    }

    private static List<BookDTO> getBookDTOList(List<Book> books) {
        return books.stream().map(book -> new BookDTO(book)).collect(Collectors.toList()); //sürekli kullanacagimiz kod bloku icin bu methotu yazdik
    }



    //10-b
    public void addBookToOwner(Long bookId, Long ownerId) {
        Book foundBook=getBookById(bookId);//owner:1-null V
                                          //      2-başka bir üye X
                                            //    3-aynı üye:kendisinde X
        Owner foundOwner=ownerService.getOwnerById(ownerId);

        //belirtilen kitap daha önce ownera verilmiş mi?
        if (foundOwner.getBookList().contains(foundBook)){
            throw new ConflictException("Bu kitap üyenin listesinde zaten var!");
        }else if(foundBook.getOwner()!=null){
            throw new ConflictException("Bu kitap başka bir üyededir!");
        }else{
            //kitap üyeye vermek için aktif
            foundBook.setOwner(foundOwner);
            //owner.getBookList().add(foundBook);-->mappedBy
            bookRepository.save(foundBook);
        }

    }


    //kitap hangi üyede
    public OwnerDTO showOwner(Long bookId) {

        Book foundBook=getBookById(bookId);
        Owner owner=foundBook.getOwner();//null,owner
        if (owner!=null){
            return new OwnerDTO(owner);
        }else {
            throw new ResourceNotFoundException("Bu kitap bir üyede değildir!!!");
        }
    }
}
