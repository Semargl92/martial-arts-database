package by.semargl.requests.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import by.semargl.requests.GradeRequest;
import by.semargl.domain.Grade;

@Mapper(componentModel = "spring")
public interface GradeMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGradeFromGradeRequest(GradeRequest gradeRequest, @MappingTarget Grade grade);
}
