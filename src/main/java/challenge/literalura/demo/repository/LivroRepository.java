package challenge.literalura.demo.repository;

import challenge.literalura.demo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro,Long> {
    @Query("select l from Livro l order by l.title")
    List<Livro> findAllSorted();

    @Query("select l from Livro l where :lang member l.languages order by l.title")
    List<Livro> findByLanguage(String lang);
}
