package com.tpe.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SaveOwnerDTO {



    @NotBlank(message = "Geçerli bir isim giriniz!!!")
    private String name;

    @NotBlank(message = "Geçerli bir isim giriniz!!!")
    private String lastname;

    @Pattern(regexp = "0\\s\\d{3}\\s\\d{3}\\s\\d{2}\\s\\d{2}",
            message = "Telefon numarası 0 XXX XXX XX XX şeklinde olmalıdır!!!")
    private String phone;

    @Email(message = "Geçerli bir email giriniz!!!")
    private String email;










}
