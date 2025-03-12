package mk.ukim.finki.emt.lab.service;


import mk.ukim.finki.emt.lab.model.Country;
import mk.ukim.finki.emt.lab.model.dto.CountryDto;

import java.util.List;

public interface CountryService {
    List<Country> listAll();

    Country findById(Long id);

    Country create(String name , String continent);

    Country create(CountryDto country);

    Country edit(Long id, CountryDto country);

    void delete(Long id);
}
