package com.skillproof.skillproofapi.model.request.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    @Schema(name = "token", example = "Bearer sfa73hsfs87sf23ua63gaf7a.sahfjuwi536827sefui.iwy7863hfw7",
            accessMode = Schema.AccessMode.READ_ONLY)
    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
