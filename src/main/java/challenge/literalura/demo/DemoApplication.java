package challenge.literalura.demo;

import challenge.literalura.demo.repository.AutorRepository;
import challenge.literalura.demo.repository.LivroRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    private static final String WELCOME_MESSAGE = """
            \n[1] Perquisar livro pelo titulo
            [2] Listar todos os livros de um autor
            [3] Presquisar autor
            [4] Listar autores vivos em um ano especifico
            [5] Listar livros em um determinado idioma
            [7] Sair
            Escolha uma opção válida:""";
    @Autowired
    private LivroRepository books;
    @Autowired
    private AutorRepository authors;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        var consoleInput = new Scanner(System.in);
        while (true) {
            System.out.println(WELCOME_MESSAGE);
            var choice = consoleInput.nextInt();
            var arg = consoleInput.nextLine();
            List list;
            switch (choice) {
                case 1 -> {
                    list = searchBook(arg);
                    books.saveAll(list);
                }
                case 2 -> list = authors.findOneByName(arg).getLivros();
                case 3 -> list = authors.findByName(arg);
                case 4 -> list = authors.findByAliveYear(Integer.parseInt(arg.strip()));
                case 5 -> list = books.findByLanguage(arg);
                case 6 -> list = books.findAllSorted();
                case 7 -> {
                    return;
                }
                default -> {
                    System.out.println("Digite uma opção valida!");
                    continue;
                }
            }
            if (!list.isEmpty()) {
                list.forEach(System.out::println);
            } else {
                System.out.println("Nada encontrado");
            }
        }
    }

    private List<Livro> searchBook(String name) {
        final String url = "https://gutendex.com/books/search=" + name.replace(" ", "%20");
        try {
            var mapper = new ObjectMapper();
            var jsonArray = mapper.readTree(URI.create(url).toURL()).get("results");
            return mapper.readValue(jsonArray.traverse(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
