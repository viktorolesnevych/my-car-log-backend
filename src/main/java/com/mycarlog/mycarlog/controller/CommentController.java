package com.mycarlog.mycarlog.controller;

import com.mycarlog.mycarlog.model.Comment;
import com.mycarlog.mycarlog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/vehicles/{vehicleId}/logs/{logId}/comments")
    public List<Comment> getLogComments(@PathVariable Long vehicleId, @PathVariable Long logId){
        return commentService.getLogComments(vehicleId,logId);
    }

    @GetMapping("/vehicles/{vehicleId}/logs/{logId}/comments/{commentId}")
    public Comment getLogComment(@PathVariable Long vehicleId, @PathVariable Long logId, @PathVariable Long commentId){
        return commentService.getLogComment(vehicleId,logId,commentId);
    }

    @PostMapping("/vehicles/{vehicleId}/logs/{logId}/comments")
    public Comment createComment(@PathVariable Long vehicleId, @PathVariable Long logId, @RequestBody Comment commentObject){
        return commentService.createComment(vehicleId, logId, commentObject);
    }

    @PutMapping("/vehicles/{vehicleId}/logs/{logId}/comments/{commentId}")
    public Comment updateComment(@PathVariable Long vehicleId, @PathVariable Long logId, @PathVariable Long commentId,
                                 @RequestBody Comment commentObject){
        return commentService.updateComment(vehicleId, logId, commentId, commentObject);
    }

    @DeleteMapping("/vehicles/{vehicleId}/logs/{logId}/comments/{commentId}")
    public ResponseEntity<HashMap> deleteComment(@PathVariable Long vehicleId, @PathVariable Long logId,
                                                 @PathVariable Long commentId){
        commentService.deleteComment(vehicleId, logId, commentId);
        HashMap response = new HashMap();
        response.put("Response", "Comment with id " + commentId + " has been deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
