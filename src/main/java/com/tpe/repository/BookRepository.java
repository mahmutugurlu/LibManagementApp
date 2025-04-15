package com.tpe.repository;

import com.tpe.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //bu klassda database ile ilgili islemlerin yapilacagini belirtir, kullanmak zorunlu degil
public interface BookRepository extends JpaRepository<Book,Long> {
}
