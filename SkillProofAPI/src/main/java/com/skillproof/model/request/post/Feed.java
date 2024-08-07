package com.skillproof.model.request.post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Feed {

    private List<PostDTO> posts;
}
