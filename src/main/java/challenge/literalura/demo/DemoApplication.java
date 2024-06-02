package challenge.literalura.demo;

import challenge.literalura.demo.repository.AutorRepository;
import challenge.literalura.demo.repository.LivroRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.net.URI;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    private static final String MENU = """
            \nEscolha uma opção válida:
            [1] Perquisar livro pelo titulo
            [2] Presquisar autor
            [3] Listar autores vivos em um ano especifico
            [4] Listar livros em um determinado idioma
            [5] Listar todos os livros de um autor
            [6] Listar todos os livros
            [7] Listar todos os autores
            [8] Sair""";
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
        List list;
        String arg;
        while (true) {
            System.out.println(MENU);
            var choice = consoleInput.nextInt();
            switch (choice) {
                case 1 -> System.out.println("Digite o titulo do livro:");
                case 2, 5, 3 -> System.out.println("Digite o nome do autor:");
                case 4 -> System.out.println("Digite o idioma desejado:");
            }
            do {
                arg = consoleInput.nextLine();
            }
            while (arg.isEmpty() && (choice > 0 && choice < 6));
            try {
                switch (choice) {
                    case 1 -> {
                        list = searchBook(arg);
                        books.saveAll(list);
                    }
                    case 2 -> list = authors.findByName(arg);
                    case 3 -> list = authors.findByAliveYear(Integer.parseInt(arg.strip()));
                    case 4 -> list = books.findByLanguage(arg);
                    case 5 -> list = authors.findOneByName(arg).getLivros();
                    case 6 -> list = books.findAllSorted();
                    case 7 -> list = authors.findAllSorted();
                    case 8 -> { return; }
                    default -> {
                        System.out.println("Digite uma opção valida!");
                        continue;
                    }
                }
                if (!list.isEmpty()) {
                    list.forEach(System.out::println);
                } else {
                    System.out.print("\nNada encontrado\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite um ano!");
            } catch (IncorrectResultSizeDataAccessException e) {
                System.out.println("seja mais especifico!");
            } catch (NullPointerException e) {
                System.out.println("isso não é um nome!");
            }
            System.out.println("presione enter para continuar");
            consoleInput.nextLine();
        }
    }

    private List<Livro> searchBook(String name) {
        final String url = "https://gutendex.com/books/search=" + name.replace(" ", "%20");
        try {
            var mapper = new ObjectMapper();
            var jsonArray = mapper.readTree(URI.create(url).toURL()).get("results");
            return mapper.readValue(jsonArray.traverse(), new TypeReference<>(){});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
