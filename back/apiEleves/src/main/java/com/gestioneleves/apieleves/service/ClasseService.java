package com.gestioneleves.apieleves.service;

import com.gestioneleves.apieleves.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

}
