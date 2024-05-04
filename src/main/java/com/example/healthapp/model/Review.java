package com.example.healthapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.Date;

@Document(collection = "reviews")
@Data
public class Review {
    @Id
    private String id;
    private String therapistId;
    private String userId;
    private Integer rating;
    private String content;
    private Date date;
}

