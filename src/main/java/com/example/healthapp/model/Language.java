package com.example.healthapp.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "languages")
public class Language {

    @Id
    String id;

    String name;

}
