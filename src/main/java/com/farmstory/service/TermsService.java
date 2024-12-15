package com.farmstory.service;

import com.farmstory.dto.TermsDTO;
import com.farmstory.entity.Terms;
import com.farmstory.repository.Termsrepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TermsService {

    private final Termsrepository termsrepository;

    public TermsDTO selectTerms(){
        Optional<Terms> term = termsrepository.findById(1);
        if(term.isPresent()){
            TermsDTO termDTO = term.get().toDTO();
            return termDTO;
        }
        return null;
    }


}
