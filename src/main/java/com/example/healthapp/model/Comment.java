package com.example.healthapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("comments")
public class Comment {
    @Id
    private String id;
    private String content;
    private User author;
    private Date createdAt;
    private String discussionId;
}
