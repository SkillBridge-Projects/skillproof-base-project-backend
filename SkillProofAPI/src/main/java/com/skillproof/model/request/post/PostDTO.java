package com.skillproof.model.request.post;

import com.skillproof.model.request.comment.CommentResponse;
import com.skillproof.model.request.like.LikeResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PostDTO {

    private PostResponse post;
    private List<LikeResponse> likes;
    private List<CommentResponse> comments;

}
