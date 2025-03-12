package mk.ukim.finki.emt.lab.service.impl;

import mk.ukim.finki.emt.lab.model.Country;
import mk.ukim.finki.emt.lab.model.dto.AuthorDto;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidAuthorIdException;
import mk.ukim.finki.emt.lab.model.exceptions.InvalidCountryIdException;
import mk.ukim.finki.emt.lab.service.CountryService;
import mk.ukim.finki.emt.lab.service.AuthorService;
import mk.ukim.finki.emt.lab.model.Author;
import mk.ukim.finki.emt.lab.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CountryService countryService;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryService countryService) {
        this.authorRepository = authorRepository;
        this.countryService = countryService;
    }


    @Override
    public Author findById(Long id) {
        return this.authorRepository.findById(id).orElseThrow(InvalidAuthorIdException::new);
    }

    @Override
    public Author create(String name, String surname, Long id) {
        Country country = this.countryService.findById(id);
        Author newAuthor = new Author(name, surname, country);
        return this.authorRepository.save(newAuthor);
    }

    @Override
    public List<Author> listAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Author create(AuthorDto author) {
        Country country = this.countryService.findById(author.getCountry());
        Author newAuthor = new Author(author.getName(), author.getSurname(), country);

        return this.authorRepository.save(newAuthor);
    }

    @Override
    public Author edit(Long id, AuthorDto author) {
        Author oldAuthor = this.findById(id);
        oldAuthor.setName(author.getName());
        oldAuthor.setSurname(author.getSurname());
        oldAuthor.setCountry(this.countryService.findById(author.getCountry()));
        return this.authorRepository.save(oldAuthor);
    }

    @Override
    public void delete(Long id) {
        this.authorRepository.deleteById(id);
    }
}


