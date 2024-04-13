package com.example.healthapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("discussions")
public class Discussion {
    @Id
    private String id;
    private String type;
    @DBRef(lazy = false)
    private Category category;
    private String title;
    private String content;
    @DBRef(lazy = false)
    private User author;
    private Date createdAt;
    private Integer upvotes;
    private Integer comments;
}
