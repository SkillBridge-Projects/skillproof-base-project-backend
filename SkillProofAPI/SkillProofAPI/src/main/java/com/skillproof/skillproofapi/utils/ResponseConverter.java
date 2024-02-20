package com.skillproof.skillproofapi.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ResponseConverter {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseConverter.class);

    private static final ModelMapper MODEL_MAPPER = createModelMapper();

    private static ModelMapper createModelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    public static  <S, T> T copyProperties(S sourceObject, Class<T> targetClass){
        LOG.info("Start of copyProperties method.");
        T targetObject = null;
        if (ObjectUtils.isNotEmpty(sourceObject)){
            targetObject = MODEL_MAPPER.map(sourceObject, targetClass);
        }
        LOG.info("End of copyProperties method.");
        return targetObject;
    }
}
