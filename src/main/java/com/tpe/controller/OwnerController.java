package com.tpe.controller;

import com.tpe.dto.OwnerDTO;
import com.tpe.dto.SaveOwnerDTO;
import com.tpe.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;


    //2- Find All Owners
    // http://localhost:8080/owners+GET
    @RequestMapping("/owners") //ortak bir path olmadigi icin @RequestMapping anotasyonunu metod seviyesinde kullandik
    @GetMapping
    public ResponseEntity<List<OwnerDTO>> getAll(){
        List<OwnerDTO> owners  =ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }


    //1- Save an Owner
    // http://localhost:8080/owner/save + JSON + POST

    @RequestMapping("/owner/save")
    @PostMapping
    public ResponseEntity<String> saveOwner(@Valid @RequestBody SaveOwnerDTO ownerDTO){

        ownerService.createOwner(ownerDTO);

        return new ResponseEntity<>("Üye başarılı bir şekilde eklendi.", HttpStatus.CREATED);

    }




    //3- Find an Owner By ID
    // http://localhost:8080/owner/2 +GET

    @RequestMapping("/owner/{id}")
    @GetMapping
    public ResponseEntity<OwnerDTO> getOwnerByID(@PathVariable Long id){
        OwnerDTO ownerDTO=ownerService.getOwnerDtoById(id);

        return ResponseEntity.ok(ownerDTO);
    }















}