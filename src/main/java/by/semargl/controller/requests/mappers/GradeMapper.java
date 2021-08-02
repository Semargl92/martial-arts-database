package by.semargl.controller.requests.mappers;

import by.semargl.controller.requests.GradeRequest;

import by.semargl.domain.Grade;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGradeFromGradeRequest(GradeRequest gradeRequest, @MappingTarget Grade grade);
}