package com.skillproof.exceptions.apiError;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SubError {
    private String object;
    private String field;
    private String message;
}
