package com.skillproof.model.request.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    @Schema(name = "token", example = "Bearer sfa73hsfs87sf23ua63gaf7a.sahfjuwi536827sefui.iwy7863hfw7",
            accessMode = Schema.AccessMode.READ_ONLY)
    private final String token;

    @Schema(name = "userId", example = "SBsfji238sy7fs",
            accessMode = Schema.AccessMode.READ_ONLY)
    private final String userId;

    public JwtResponse(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }
}
