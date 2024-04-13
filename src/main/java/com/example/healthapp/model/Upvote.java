package com.example.healthapp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("upvotes")
public class Upvote {
    @Id
    private String id;
    private String userId;
    private String discussionId;
    private Date datetime;
}
