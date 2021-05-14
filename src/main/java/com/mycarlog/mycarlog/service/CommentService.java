package com.mycarlog.mycarlog.service;

import com.mycarlog.mycarlog.exception.InformationExistException;
import com.mycarlog.mycarlog.exception.InformationForbidden;
import com.mycarlog.mycarlog.model.Comment;
import com.mycarlog.mycarlog.model.User;
import com.mycarlog.mycarlog.repository.CommentRepository;
import com.mycarlog.mycarlog.repository.LogRepository;
import com.mycarlog.mycarlog.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    private UtilityService utilityService = new UtilityService();

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private LogRepository logRepository;

    // PUBLIC USER has access, returns the list of all comments related to the log in a vehicle page
    public List<Comment> getLogComments(Long vehicleId, Long logId){
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository, vehicleId, "Vehicle");
        utilityService.errorIfRepositoryElementNotExistById(logRepository, logId,"Log");
        if (logRepository.findById(logId).get().getVehicle().getId() != vehicleId)
            throw new InformationForbidden("Log with ID " + logId + " is in a different vehicle page");
        return commentRepository.findByLogId(logId);
    }

    // PUBLIC USER has access, returns a single comment related to the log
    public Comment getLogComment(Long vehicleId, Long logId, Long commentId){
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository,vehicleId, "Vehicle");
        utilityService.errorIfRepositoryElementNotExistById(logRepository, logId,"Log");
        utilityService.errorIfRepositoryElementNotExistById(commentRepository,commentId,"Comment");
        if ((commentRepository.findById(commentId).get().getLog().getId() == logId) &&
            (commentRepository.findById(commentId).get().getLog().getVehicle().getId() == vehicleId))
            return commentRepository.findById(commentId).get();
        else
            throw new InformationForbidden("Comment with ID " + commentId + " is in a different log");
    }

    // AUTHENTICATED USER has access, creates new comment in the log page
    public Comment createComment(Long vehicleId, Long logId, Comment commentObject){
        User currentUser = utilityService.getAuthenticatedUser();
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository,vehicleId, "Vehicle");
        utilityService.errorIfRepositoryElementNotExistById(logRepository, logId,"Log");
        if (logRepository.findById(logId).get().getVehicle().getId() != vehicleId)
            throw new InformationForbidden("Log with ID " + logId + " belongs to a different vehicle page");
        commentObject.setLog(logRepository.findById(logId).get());
        commentObject.setUser(currentUser);
        commentObject.setDateCreated(new Date(System.currentTimeMillis()));
        return commentRepository.save(commentObject);
    }

    // AUTHENTICATED USER has access, updates existing comment in the log page
    public Comment updateComment(Long vehicleId, Long logId, Long commentId, Comment commentObject){
        User currentUser = utilityService.getAuthenticatedUser();
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository,vehicleId, "Vehicle");
        utilityService.errorIfRepositoryElementNotExistById(logRepository, logId,"Log");
        utilityService.errorIfRepositoryElementNotExistById(commentRepository, commentId,"Comment");
        if (logRepository.findById(logId).get().getVehicle().getId() != vehicleId)
            throw new InformationForbidden("Log with ID " + logId + " belongs to a different vehicle page");
        if (commentRepository.findById(logId).get().getLog().getId() != logId)
            throw new InformationForbidden("Comment with ID " + commentId + " is within different log");
        Comment currentComment = commentRepository.findById(commentId).get();
        if (currentComment.getUser().getId() != currentUser.getId())
            throw new InformationForbidden("Comment with ID " + commentId + " belongs to a different user");
        if (currentComment.getTextContent().equals(commentObject.getTextContent()))
            throw new InformationExistException("No changes provided");
        currentComment.setTextContent(commentObject.getTextContent());
        return commentRepository.save(currentComment);
    }

    // AUTHENTICATED USER can delete his comment/Admin can delete all comments
    public void deleteComment(Long vehicleId, Long logId, Long commentId){
        User currentUser = utilityService.getAuthenticatedUser();
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository,vehicleId, "Vehicle");
        utilityService.errorIfRepositoryElementNotExistById(logRepository, logId,"Log");
        utilityService.errorIfRepositoryElementNotExistById(commentRepository, commentId,"Comment");
        if (logRepository.findById(logId).get().getVehicle().getId() != vehicleId)
            throw new InformationForbidden("Log with ID " + logId + " belongs to a different vehicle page");
        if (commentRepository.findById(logId).get().getLog().getId() != logId)
            throw new InformationForbidden("Comment with ID " + commentId + " is within different log");
        Comment currentComment = commentRepository.findById(commentId).get();
        if ((currentComment.getUser().getId() == currentUser.getId())|| utilityService.isUserAdmin(currentUser))
            commentRepository.deleteById(commentId);
        else
            throw new InformationForbidden("You must be the original poster or an admin to delete this comment!");
    }

//    //Public USER has access, returns the list of all child comments of a particular comment
//    //Will also check if data is set properly
//    public List<Comment> getChildComments(Long topicId, Long articleId, Long commentId){
//        return this.getArticleComment(topicId,articleId,commentId).getChildrenComments();
//    }
//
//
//    // AUTHENTICATED USER has access, creates new child comment (REPLY) to an existing comment
//    // Will also check if data is set properly
//    public Comment createChildComment(Long topicId, Long articleId,
//                                      Long commentId, Comment commentObject) {
//        Comment parent = this.getArticleComment(topicId,articleId,commentId);
//        commentObject.setParentComment(parent);
//        Comment currentNewComment = this.createComment(topicId,articleId,commentObject);
//        return commentRepository.save(parent);
//    }
}
