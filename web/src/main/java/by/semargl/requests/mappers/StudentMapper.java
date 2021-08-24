package by.semargl.requests.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import by.semargl.requests.StudentRequest;
import by.semargl.domain.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromStudentRequest(StudentRequest studentRequest, @MappingTarget Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentRequestFromStudent(Student student, @MappingTarget StudentRequest studentRequest);
}
