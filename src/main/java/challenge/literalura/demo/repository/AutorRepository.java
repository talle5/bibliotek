package challenge.literalura.demo.repository;

import challenge.literalura.demo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("select a from Autor a where a.name ilike %:name%")
    Optional<Autor> findByName(String name);
}
