package com.tpe.dto;

import com.tpe.domain.Book;
import com.tpe.domain.Owner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OwnerDTO {

    private String name;

    private String lastname;

    private String phone;

    private String email;

    private LocalDateTime registrationDate;

    private List<Book> bookList;

    //owner->owner DTO

    public OwnerDTO(Owner owner) {
        this.name = owner.getName();
        this.lastname = owner.getLastname();
        this.phone = owner.getPhone();
        this.email = owner.getEmail();
        this.registrationDate = owner.getRegistrationDate();
        this.bookList = owner.getBookList();
    }
}