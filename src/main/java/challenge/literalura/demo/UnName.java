package challenge.literalura.demo;

import challenge.literalura.demo.repository.AutorRepository;
import challenge.literalura.demo.repository.LivroRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class UnName {
    LivroRepository bookRepo;
    AutorRepository athorRepo;


    UnName(LivroRepository bookRepo, AutorRepository athorRepo) {
        this.bookRepo = bookRepo;
        this.athorRepo = athorRepo;
    }

    public List<Livro> searchBook(String name) {
        var url = ApiRequest.genUri("search=" + name.replace(" ", "%20"));
//        Comparator<Livro> a = Comparator.comparing(Livro::getTitle);
        try {
            var mapper = new ObjectMapper();
            var jsonArray = mapper.readTree(url.toURL()).get("results");
            List<Livro> list = mapper.readValue(jsonArray.traverse(), new TypeReference<>() {
            });
            bookRepo.saveAll(list);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Livro> searchAuthor(String name) {
        return bookRepo.findByAuthorName(name);
    }

    public List<Livro> listAlive(Integer year) {
        return bookRepo.findByAliveYear(year);
    }

    public List<Livro> listLanguage(String lang) {
        return bookRepo.findByLanguage(lang);
    }

    public List<Livro> listAllByAthor(String name) {
        var autor = athorRepo.findByName(name);
        return autor.isPresent() ? autor.get().getLivros() : List.of();
    }
}
