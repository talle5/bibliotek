package challenge.literalura.demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Livros")
@JsonIgnoreProperties(ignoreUnknown = true,value = {"id"})
public class Livro{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor authors;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> languages;
    private Boolean copyright;
    public Integer downloads;

    protected Livro(){}

    @JsonCreator
    public Livro(@JsonProperty("title") String title,
                 @JsonProperty("authors") Autor[] authors,
                 @JsonProperty("languages") List<String> languages,
                 @JsonProperty("copyright") Boolean copyright,
                 @JsonProperty("download_count") Integer downloads) {
        this.title = title;
        this.authors = (authors.length > 0) ? authors[0] : null;
        this.languages = languages;
        this.copyright = copyright;
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return String.format("---- LIVRO ----\nTitulo: %s\nAutor: %s\nIdiomas: %s\nDownloads: %d",
                title,
//                StringUtils.collectionToDelimitedString(authors, " e "),
                (authors == null || authors.getName().equals("Anonymous")) ? "Desconhecido" : authors.getName(),
                String.join(", ", languages),
                downloads);
    }

    public String getTitle() {
        return title;
    }

    public Autor getAuthors() {
        return authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public Integer getDownloads() {
        return downloads;
    }

}

