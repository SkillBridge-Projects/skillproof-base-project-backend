package com.skillproof.model.request.post;

import com.skillproof.constants.CommonConstants;
import com.skillproof.validators.Messages;
import com.skillproof.validators.NotEmptyOnly;
import com.skillproof.validators.RegEx;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdatePostRequest {

    @NotEmptyOnly(label = CommonConstants.CONTENT)
    @Size(max = 1000, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "content", example = "Hi All, i am open to work.")
    private String content;
}
