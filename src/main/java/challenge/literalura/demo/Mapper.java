package challenge.literalura.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.util.List;

public class Mapper {
    private ObjectMapper mapper;

    Mapper() {
        this.mapper = new ObjectMapper();
    }

    public List<Livro> asList() {
        try (var b = new FileReader("download.json")) {
            var c = mapper.readTree(b);
            var jsonArray = c.get("results");
            return mapper.readValue(jsonArray.traverse(), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
