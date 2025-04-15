package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;


    @NotBlank(message = "Geçerli bir isim giriniz!!!")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Geçerli bir isim giriniz!!!")
    @Column(nullable = false)
    private String lastname;

    @Pattern(regexp = "0\\s\\d{3}\\s\\d{3}\\s\\d{2}\\s\\d{2}", //@Pattern anotasyonu fielden belirli bir kalipda yazilmasini saglar
            message = "Telefon numarası 0 XXX XXX XX XX şeklinde olmalıdır!!!")
    private String phone;


    @Email(message = "Geçerli bir email giriniz!!!")
    @Column(nullable = false,unique = true)
    private String email;


    @Setter(AccessLevel.NONE)
    private LocalDateTime registrationDate;

    @PrePersist
    public void setRegistrationDate(){
        this.registrationDate=LocalDateTime.now();
    }



    @OneToMany(mappedBy ="owner")
    private List<Book> bookList=new ArrayList<>();




}
