package mk.ukim.finki.emt.lab.repository;

import mk.ukim.finki.emt.lab.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaSpecificationRepository<Book,Long>{
}
