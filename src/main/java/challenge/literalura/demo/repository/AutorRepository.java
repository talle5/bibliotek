package challenge.literalura.demo.repository;

import challenge.literalura.demo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("select a from Autor a order by a.name")
    List<Autor> findAllSorted();

    @Query("select a from Autor a where a.name ilike %:name%")
    Autor findOneByName(String name);

    @Query("select a from Autor a where a.name ilike %:name% order by a.name")
    List<Autor> findByName(String name);

    @Query("select a from Autor a where :year between a.birth_year and a.death_year order by a.name")
    List<Autor> findByAliveYear(Integer year);
}
