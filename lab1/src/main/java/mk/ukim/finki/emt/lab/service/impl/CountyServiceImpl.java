package mk.ukim.finki.emt.lab.service.impl;

import mk.ukim.finki.emt.lab.model.Country;
import mk.ukim.finki.emt.lab.model.dto.CountryDto;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidCountryIdException;
import mk.ukim.finki.emt.lab.repository.CountryRepository;
import mk.ukim.finki.emt.lab.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountyServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountyServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country findById(Long id) {
        return this.countryRepository.findById(id).orElseThrow(InvalidCountryIdException::new);
    }

    @Override
    public Country create(String name, String continent) {
        Country country = new Country(name, continent);
        return this.countryRepository.save(country);
    }

    @Override
    public List<Country> listAll() {
        return this.countryRepository.findAll();
    }


    @Override
    public Country create(CountryDto country) {
        Country newCountry = new Country(country.getName(), country.getContinent());
        return this.countryRepository.save(newCountry);
    }

    @Override
    public Country edit(Long id, CountryDto country) {
        Country oldCountry = this.findById(id);
        oldCountry.setName(country.getName());
        oldCountry.setContinent(country.getContinent());
        return countryRepository.save(oldCountry);
    }

    @Override
    public void delete(Long id) {
        this.countryRepository.deleteById(id);
    }
}
