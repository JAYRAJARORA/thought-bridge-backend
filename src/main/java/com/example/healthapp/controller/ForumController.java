package com.example.healthapp.controller;

import com.example.healthapp.exception.BadRequestException;
import com.example.healthapp.exception.NotFoundException;
import com.example.healthapp.model.Comment;
import com.example.healthapp.model.Discussion;
import com.example.healthapp.model.Upvote;
import com.example.healthapp.service.ForumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@Tag(name="Discussions details", description = "APIs for creating and updating the issues " +
        "and articles along with comments and upvotes")
@RequestMapping("/discussions")
public class ForumController {
    @Autowired
    private ForumService forumService;

    private final Logger logger = LoggerFactory.getLogger(ForumController.class);

    @Operation(
            summary = "Get all discussion based on the type(article/issue)",
            description = "Fetch all articles/issues based on pagination set. TODO: pagination part")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all discsssions based on type"),
    })
    @GetMapping("")
    public ResponseEntity<List<Discussion>> getDiscussions(
            @RequestParam String type,
            @RequestParam(name = "userId", required = false) String userId
    ) {
        Page<Discussion> discussionPage = forumService.getDiscussions(type, userId);
        List<Discussion> discussions = discussionPage.getContent();

        if (discussions.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(discussions);
        }
    }

    @Operation(
            summary = "Show trending articles based on popularity",
            description = "Fetch all articles for the home page which are most upvoted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches all trending articles"),
    })
    @GetMapping("/trending")
    public ResponseEntity<List<Discussion>> getTopTrendingArticles() {
        // Retrieve the top 10 trending articles based on upvotes
        List<Discussion> trendingArticles = forumService.getTopTrendingArticles();
        return ResponseEntity.ok(trendingArticles);
    }

    @Operation(
            summary = "Get Discussion by ID",
            description = "Fetch the details of a discussion by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetches the discussion details"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Discussion>> getDiscussionById(@PathVariable String id) {
        Optional<Discussion> discussion = forumService.getDiscussionById(id);
        System.out.println(discussion);
        if (discussion.isPresent())  {
            return ResponseEntity.ok(discussion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Create Discussion",
            description = "Create discussion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create a discussion"),
            @ApiResponse(responseCode = "404", description = "Invalid user"),
            @ApiResponse(responseCode = "400", description = "Invalid discussion type")
    })
    @PostMapping("")
    public ResponseEntity<?> createDiscussion(@RequestBody Discussion discussion) {
        System.out.println(discussion);
        try {
            Discussion createdDiscussion = forumService.createDiscussion(discussion);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscussion);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(
            summary = "Add comment on Discussion",
            description = "Create comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create a comment"),
            @ApiResponse(responseCode = "404", description = "Invalid discussion id"),
    })
    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addCommentOnDiscussion(@PathVariable String id, @RequestBody Comment comment) {
        try {
            Comment newComment = this.forumService.createComment(id, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Operation(
            summary = "Get comments for a Discussion",
            description = "Get comments for discussion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get comment"),
            @ApiResponse(responseCode = "404", description = "Invalid discussion id"),
    })
    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommentsForDiscussion(@PathVariable String id) {
        try {
            return ResponseEntity.ok(this.forumService.getComments(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @Operation(
            summary = "Toggle vote on discussion",
            description = "Create or remove the upvote from the discussion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Toggle vote on the discussion"),
            @ApiResponse(responseCode = "404", description = "Invalid discussion id"),
    })
    @PostMapping("/{id}/toggle-vote")
    public ResponseEntity<?> toggleVoteOnDiscussion(@PathVariable("id") String discussionId, @RequestParam String userId) {

        Discussion updatedDiscussion = forumService.toggleVoteOnDiscussion(discussionId, userId);
        if(updatedDiscussion != null) {
            return ResponseEntity.ok(updatedDiscussion);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Discussion Not found");
        }

    }

    @Operation(
            summary = "Has user upvotes on discussion",
            description = "Check the user has upvotes or not")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upvote ovbject"),
            @ApiResponse(responseCode = "204", description = "Not upvoted"),
            @ApiResponse(responseCode = "404", description = "Invalid discussion id or user id"),
    })
    @PostMapping("/{id}/check-vote")
    public ResponseEntity<?> hasUserUpvoted(@PathVariable("id") String discussionId, @RequestParam String userId) {

        Upvote upvote = forumService.hasUserUpvoted(discussionId, userId);
        if(upvote != null) {
            return ResponseEntity.ok(upvote);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }
}


