package com.skillproof.skillproofapi.exceptions;

import lombok.Getter;

@Getter
public abstract class SkillProofException extends RuntimeException {

    public SkillProofException(String msg, Object... msgArgs) {
        super(Messages.msg(msg, msgArgs));
    }

    protected SkillProofException() {
    }

    private String objectName;
    private String fieldName;

    public SkillProofException(Exception e, String msg){
        super(msg, e);
    }

    public SkillProofException setObjectName(String objectName){
        this.objectName = objectName;
        return this;
    }

    public SkillProofException setObjectAndFieldNames(String objectName, String fieldName){
        this.objectName = objectName;
        this.fieldName = fieldName;
        return this;
    }
}
