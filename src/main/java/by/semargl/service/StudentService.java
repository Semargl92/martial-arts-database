package by.semargl.service;

import by.semargl.controller.requests.StudentRequest;
import by.semargl.controller.requests.mappers.StudentMapper;
import by.semargl.domain.Student;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.GradeRepository;
import by.semargl.repository.StudentRepository;
import by.semargl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final StudentMapper studentMapper;

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public List<StudentRequest> findAllExistingStudents() {
        List<Student> notDeletedStudents = studentRepository.findByIsDeletedFalse();
        List<StudentRequest> result = new ArrayList<>();
        for (Student student: notDeletedStudents) {
            StudentRequest studentRequest = new StudentRequest();
            studentMapper.updateStudentRequestFromStudent(student, studentRequest);
            if (student.getUser() != null ) {
                studentRequest.setUserId(student.getUser().getId());
            }
            if (student.getGrade() != null ) {
                studentRequest.setGradeId(student.getGrade().getId());
            }
            result.add(studentRequest);
        }
        return result;
    }

    public Student findOneStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Student not found by id " + id));
    }

    public StudentRequest findOneExistingStudent(Long id) {
        StudentRequest studentRequest = new StudentRequest();
        Student student = studentRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NoSuchEntityException("Student not found by id " + id));
        studentMapper.updateStudentRequestFromStudent(student, studentRequest);
        if (student.getUser() != null ) {
            studentRequest.setUserId(student.getUser().getId());
        }
        if (student.getGrade() != null ) {
            studentRequest.setGradeId(student.getGrade().getId());
        }
        return studentRequest;
    }

    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.delete(id);
    }

    @Transactional
    public void softDeleteStudent(Long id) {
        studentRepository.softDelete(id);
    }

    public Student createStudent(StudentRequest studentRequest) {
        Student student = new Student();

        studentMapper.updateStudentFromStudentRequest(studentRequest, student);
        student.setCreated(LocalDateTime.now());
        student.setChanged(LocalDateTime.now());
        student.setIsDeleted(false);
        student.setUser(userRepository.findById(studentRequest.getUserId())
                .orElseThrow(() -> new NoSuchEntityException("There is no such user for this student")));
        student.setGrade(gradeRepository.findById(studentRequest.getGradeId())
                .orElseThrow(() -> new NoSuchEntityException("There is no such grade for this student")));

        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Student not found by id " + id));

        studentMapper.updateStudentFromStudentRequest(studentRequest, student);
        student.setChanged(LocalDateTime.now());
        if (studentRequest.getUserId() != null ) {
            student.setUser(userRepository.findById(studentRequest.getUserId())
                    .orElseThrow(() -> new NoSuchEntityException("There is no such user for this student")));
        }
        if (studentRequest.getGradeId() != null ) {
            student.setGrade(gradeRepository.findById(studentRequest.getGradeId())
                    .orElseThrow(() -> new NoSuchEntityException("There is no such grade for this student")));
        }

        return studentRepository.save(student);
    }
}
