package com.vasseurr.chatApp.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

public class MappingHelper {

    private static ModelMapper mapper;

    private MappingHelper() {
    }

    public static ModelMapper getMapper() {
        if (mapper == null) {
            mapper = initMapper();
            initTypeMappings(mapper);
        }
        return mapper;
    }

    private static void initTypeMappings(ModelMapper mapper) {
        // Burada entity ve Dto lara özel TypeMapping ler oluşturulabilir.
    }

    private static ModelMapper initMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    public static <S, D> TypeMap<S, D> getTypeMapping(Class<S> source, Class<D> destination) {
        TypeMap typeMap = getMapper().getTypeMap(source, destination);
        if (typeMap == null) {
            typeMap = getMapper().createTypeMap(source, destination);
        }
        return typeMap;
    }
}
