package com.tpe.service;

import com.tpe.domain.Owner;
import com.tpe.dto.OwnerDTO;
import com.tpe.dto.SaveOwnerDTO;
import com.tpe.exceptions.ConflictException;
import com.tpe.exceptions.ResourceNotFoundException;
import com.tpe.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    //2-b
    public List<OwnerDTO> getAllOwners() {
        return ownerRepository.findAll().
                stream().map(OwnerDTO::new).toList();
        //liste boşsa exception fırlatılabilir. Bu durumda bir degiskene set edilmelidir.
    }




    public void createOwner(SaveOwnerDTO ownerDTO) {

        //email unique olmalı?
        boolean exist=ownerRepository.existsByEmail(ownerDTO.getEmail());
        if (exist){
            throw new ConflictException("Bu email zaten kullanılıyor!!!");
        }


        //dto-->entity --dtodaki verileri owner a yükledik
        Owner owner=new Owner();
        owner.setName(ownerDTO.getName());
        owner.setLastname(ownerDTO.getLastname());
        owner.setPhone(owner.getPhone());
        owner.setEmail(ownerDTO.getEmail());

        ownerRepository.save(owner);

    }


    //3-a:delete, update vs.
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Üye bulunamadı. ID: "+id));
    }

    //3-b:JPQL
    public OwnerDTO getOwnerDtoById(Long id) {
        return ownerRepository.findDtoById(id).
                orElseThrow(()->new ResourceNotFoundException("Üye bulunamadı. ID: "+id));
    }




}