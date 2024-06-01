package challenge.literalura.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "Autores")
public class Autor {
    private String name;
    private Integer birth_year;
    private Integer death_year;
    @OneToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private List<Livro> livros;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonCreator
    public Autor(@JsonProperty("name") String name,
                 @JsonProperty("birth_year") Integer birth_year,
                 @JsonProperty("death_year") Integer death_year) {
        this.name = String.join(" ", Arrays.stream(name.split(", ")).toList().reversed());
        this.birth_year = birth_year;
        this.death_year = death_year;
    }

    protected Autor() {
    }

    public String getName() {
        return name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    @Override
    public String toString() {
        return String.format("----AUTOR----\nnome: %s\nnasceu em: %d\nmorreu em: %d",
                name,
                birth_year,
                death_year);
    }
}
