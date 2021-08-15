package by.semargl.controller.requests.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import by.semargl.controller.requests.ExerciseRequest;
import by.semargl.domain.Exercise;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateExerciseFromExerciseRequest(ExerciseRequest exerciseRequest, @MappingTarget Exercise exercise);
}
