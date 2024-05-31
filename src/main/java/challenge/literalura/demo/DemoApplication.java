package challenge.literalura.demo;

import challenge.literalura.demo.repository.AutorRepository;
import challenge.literalura.demo.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.Scanner;

@ComponentScan("challenge.literalura.demo.repository")
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
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
        var unName = new UnName(books,authors);
        var running = true;
        while (running) {
            var input = consoleInput.nextLine().split(" ");
            switch (input[0]) {
                case "close" -> running = false;
                case "search" -> {
                    var name = Arrays.stream(input).filter(str -> !str.equals("search")).toArray();
                    var resultado = unName.searchBook(tostring(name));
                    if (!resultado.isEmpty()) {
                        resultado.forEach(System.out::println);
                    } else {
                        System.out.println("Nenhum resultado encontrado!");
                    }
                }
                case "list" -> {
                    if (input.length == 1) {
                        unName.searchAuthor("").forEach(System.out::println);
                    } else {
                        unName.searchAuthor(input[1]).forEach(System.out::println);
                    }
                }
                case "alive" -> unName.listAlive(Integer.parseInt(input[1])).forEach(System.out::println);
                case "language" -> unName.listLanguage(input[1]).forEach(System.out::println);
                case "all" -> unName.listAllByAthor(input[1]).forEach(System.out::println);
                case "" -> {}
                default -> System.out.println("Comando invalido! Para ajuda digite help.");
            }
        }
        consoleInput.close();
    }

    public static String tostring(Object... args) {
        var str = new StringBuilder();
        for (var arg : args) {
            str.append((String) arg).append(' ');
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }
}
