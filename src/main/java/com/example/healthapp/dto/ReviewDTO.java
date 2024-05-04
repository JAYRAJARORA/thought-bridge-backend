package com.example.healthapp.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ReviewDTO {
    private String id;
    private String therapistId;
    private String userId;
    private String username;
    private Integer rating;
    private String content;
    private Date date;
}
