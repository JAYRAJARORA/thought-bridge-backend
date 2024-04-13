package com.example.healthapp.service;

import com.example.healthapp.exception.BadRequestException;
import com.example.healthapp.exception.NotFoundException;
import com.example.healthapp.model.*;
import com.example.healthapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UpvoteRepository upvoteRepository;


    public List<Discussion> getTopTrendingArticles() {
        // Define page request to limit the result to 10 articles and sort by upvotes in descending order
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "upvotes"));
        return discussionRepository.findByTypeOrderByUpvotesDesc("articles", pageable);
    }

    public Page<Discussion> getDiscussions(String type) {
        int page = 0;
        int size = 10;
        Sort.Direction direction = Sort.Direction.DESC;
        String[] properties = {"id"};
        Pageable pageable = PageRequest.of(page, size, direction, properties);
        if (type.equals("issues")) {
            return discussionRepository.findByType("issues", pageable);
        } else if (type.equals("articles")) {
            return discussionRepository.findByType("articles", pageable);
        } else {
            throw new NotFoundException("Type of discussion not found");
        }
    }

    public Optional<Discussion> getDiscussionById(String id) {
        return discussionRepository.findById(id);
    }

    public Discussion createDiscussion(Discussion request) {
        // Find user by username
        User user = userRepository.findByUsername(request.getAuthor().getUsername());
        if (!(request.getType().equals("issues") || request.getType().equals("articles"))) {
            throw new BadRequestException("Type of discussion not found");
        }
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        // Find category by name
        Category category = categoryRepository.findByName(request.getCategory().getName());
        if (category == null) {
            throw new NotFoundException("Category not found");
        }

        // Create discussion object
        Discussion discussion = new Discussion();
        discussion.setTitle(request.getTitle());
        discussion.setContent(request.getContent());
        discussion.setAuthor(user);
        discussion.setCategory(category);
        discussion.setType(request.getType());
        discussion.setUpvotes(0); // initially no upvotes

        // Save discussion to database
        return discussionRepository.save(discussion);
    }

    public Comment createComment(String discussionId, Comment newComment) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotFoundException("Discussion not found"));
        System.out.println(discussion);
        User user = userRepository.findByUsername(newComment.getAuthor().getUsername());
        System.out.println("--------");
        System.out.println(user);
        System.out.println(user.getId());
        System.out.println("----------");
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        Comment comm = new Comment();
        comm.setAuthor(user);
        comm.setContent(newComment.getContent());
        comm.setDiscussionId(discussionId);
        System.out.println("+++++++++");

        System.out.println(comm.getAuthor().getUsername());



        return commentRepository.save(comm);
    }


    public List<Comment> getComments(String discussionId) {
        return commentRepository.findAllByDiscussionId(discussionId);

    }

    public Discussion toggleVoteOnDiscussion(String discussionId, String userId) {
        Discussion discussion = discussionRepository.findById(discussionId).orElse(null);
        User currentUser = userRepository.findById(userId).orElse(null);
        if (discussion == null)
            throw new NotFoundException("Discussion not found");
        else {
            Upvote upvote = hasUserUpvoted(discussionId, userId);
            if (upvote == null) {
                return upvoteDiscussion(discussion, currentUser);
            } else {
                return removeUpvoteOnDiscussion(discussion, upvote);
            }
        }
    }

    private Discussion upvoteDiscussion(Discussion discussion, User user) {
        Upvote newUpvote = new Upvote();
        newUpvote.setUserId(user.getId());
        newUpvote.setDiscussionId(discussion.getId());
        upvoteRepository.save(newUpvote);
        discussion.setUpvotes(discussion.getUpvotes() + 1);
        return discussionRepository.save(discussion);
    }

    private Discussion removeUpvoteOnDiscussion(Discussion discussion, Upvote upvote) {
        upvoteRepository.delete(upvote);
        discussion.setUpvotes(discussion.getUpvotes() - 1);
        return discussionRepository.save(discussion);
    }

    public Upvote hasUserUpvoted(String discusssId, String userId) {
       return this.upvoteRepository.findByUserIdAndDiscussionId(userId, discusssId);
    }

}

