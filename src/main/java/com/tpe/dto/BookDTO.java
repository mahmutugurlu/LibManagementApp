package com.tpe.dto;

import com.tpe.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {


    @NotBlank(message = "Kitap ismi boşluk olamaz!!!")
    private String title;

    @NotBlank(message = "Yazar ismi boşluk olamaz!!!")
    @Size(min = 2,max = 50,message = "Yazar ismi en az 2 en fazla 50 karakter olabilir!")
    private String author;

    @NotBlank(message = "Lütfen geçerli bir yayın yılı giriniz!!!")
    private String publicationYear;


    //Book entity-->BookDTO
    public BookDTO(Book book) {
        this.title=book.getTitle();
        this.author=book.getAuthor();
        this.publicationYear=book.getPublicationYear();
    }


}
