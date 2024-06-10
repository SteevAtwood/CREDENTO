package com.example.application.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Country;
import com.example.application.repository.CountryRepository;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public List<Country> getAllCounties() {
        return countryRepository.findAll();
    }

}
