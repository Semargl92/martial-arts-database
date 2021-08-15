package by.semargl.controller.requests.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import by.semargl.controller.requests.MartialArtRequest;
import by.semargl.domain.MartialArt;

@Mapper(componentModel = "spring")
public interface MartialArtMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMartialArtFromMartialArtRequest(MartialArtRequest martialArtRequest, @MappingTarget MartialArt martialArt);
}
