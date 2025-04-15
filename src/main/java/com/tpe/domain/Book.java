package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "t_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotBlank(message = "Kitap ismi bosluk olamaz!!!")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Yazar ismi bosluk olamaz!!!")
    @Size(min =2, max= 50, message = "Yazar ismi en az 2 en fazla 50 karakter olabilir!")
    @Column(nullable = false)
    private String author;


    @NotBlank(message = "Yayin yilini giriniz!!!")
    @Column(nullable = false)
    private String publicationYear;


    @ManyToOne//fk ekler
    @JsonIgnore
    private Owner owner;



}
