package mk.ukim.finki.emt.lab.service;

import mk.ukim.finki.emt.lab.model.Country;
import mk.ukim.finki.emt.lab.model.Author;
import mk.ukim.finki.emt.lab.model.dto.AuthorDto;

import java.util.List;

public interface AuthorService {


    List<Author> listAll();

    Author findById(Long id);

    Author create(String name, String surname, Long id);

    Author create(AuthorDto author);

    Author edit(Long id, AuthorDto author);

    void delete(Long id);
}
