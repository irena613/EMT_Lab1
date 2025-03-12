package mk.ukim.finki.emt.lab.web;


import mk.ukim.finki.emt.lab.model.Country;
import mk.ukim.finki.emt.lab.model.dto.CountryDto;
import mk.ukim.finki.emt.lab.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;

    }

    @GetMapping()
    public List<Country> findAll() {
        return countryService.listAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Country> findById(@PathVariable Long id) {
        Country country = countryService.findById(id);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }

    @GetMapping("/create")
    public ResponseEntity<Country> create(@RequestBody CountryDto countryDto) {
        Country country = countryService.create(countryDto);
        return ResponseEntity.ok(country);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Country> edit(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        Country country = countryService.edit(id, countryDto);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
