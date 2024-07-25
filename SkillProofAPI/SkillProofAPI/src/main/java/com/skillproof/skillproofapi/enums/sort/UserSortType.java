package com.skillproof.skillproofapi.enums.sort;

import com.fasterxml.jackson.annotation.JsonValue;
import com.skillproof.skillproofapi.validators.QueryParam;
import com.skillproof.skillproofapi.validators.QueryParamValue;
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
