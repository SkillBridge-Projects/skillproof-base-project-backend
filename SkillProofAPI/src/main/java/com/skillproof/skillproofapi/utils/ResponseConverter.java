package com.skillproof.skillproofapi.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static <S, T> List<T> copyListProperties(List<S> sourceObject, Class<T> targetClass){
        LOG.info("Start of copyListProperties method.");
        List<T> responseList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sourceObject)){
            responseList = sourceObject.stream()
                    .map(entity -> MODEL_MAPPER.map(entity, targetClass))
                    .collect(Collectors.toList());
        }
        LOG.info("End of copyListProperties method.");
        return responseList;
    }
}
