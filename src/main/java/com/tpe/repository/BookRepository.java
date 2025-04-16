package com.tpe.repository;

import com.tpe.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //bu klassda database ile ilgili islemlerin yapilacagini belirtir, kullanmak zorunlu degil
public interface BookRepository extends JpaRepository<Book,Long> {


    //6-c
    List<Book> findByTitle(String title);//Spring tarafından implemente edilir.


    //9-c
    @Query("SELECT b FROM Book b WHERE b.author=:yazar")
    List<Book> filterBooksByAuthor(@Param("yazar") String author);


}
