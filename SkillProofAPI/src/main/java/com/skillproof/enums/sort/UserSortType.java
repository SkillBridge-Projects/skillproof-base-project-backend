package com.skillproof.enums.sort;

import com.fasterxml.jackson.annotation.JsonValue;
import com.skillproof.validators.QueryParam;
import com.skillproof.validators.QueryParamValue;
import lombok.Getter;

@QueryParam
@Getter
public enum UserSortType {

    ID_ASC("id", "id"),
    ID_DESC("-id", "id DESC");

    private final String orderBy;

    @QueryParamValue
    private final String displayName;

    UserSortType(String displayName, String orderBy){
        this.displayName = displayName;
        this.orderBy = orderBy;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

}
