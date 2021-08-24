package by.semargl.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.requests.StudentRequest;
import by.semargl.requests.mappers.StudentMapper;
import by.semargl.domain.Grade;
import by.semargl.domain.Student;
import by.semargl.domain.User;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.GradeRepository;
import by.semargl.repository.StudentRepository;
import by.semargl.repository.UserRepository;

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

    @Cacheable(value = "students", key = "#root.methodName")
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

    @Cacheable(value = "students", key = "{ #root.methodName, #id }")
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteStudent(Long id) {
        studentRepository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void softDeleteStudent(Long id) {
        studentRepository.softDeleteStudent(id);
    }

    public StudentRequest createStudent(StudentRequest studentRequest) {
        Student student = new Student();

        studentMapper.updateStudentFromStudentRequest(studentRequest, student);
        student.setCreated(LocalDateTime.now());
        student.setChanged(LocalDateTime.now());
        student.setIsDeleted(false);
        student.setGrade(gradeRepository.findById(studentRequest.getGradeId())
                .orElseThrow(() -> new NoSuchEntityException("There is no such grade for this student")));

        User user = userRepository.findById(studentRequest.getUserId())
                .orElseThrow(() -> new NoSuchEntityException("There is no such user for this student"));

        if (user.getIsDeleted()) {
            throw new NoSuchEntityException("There is no such user for this student");
        }
        student.setUser(user);

        studentRepository.save(student);

        return studentRequest;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public StudentRequest updateStudent(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Student not found by id " + id));

        studentMapper.updateStudentFromStudentRequest(studentRequest, student);
        student.setChanged(LocalDateTime.now());
        if (studentRequest.getUserId() != null ) {
            User user = userRepository.findById(studentRequest.getUserId())
                    .orElseThrow(() -> new NoSuchEntityException("There is no such user for this student"));

            if (user.getIsDeleted()) {
                throw new NoSuchEntityException("There is no such user for this student");
            }
            student.setUser(user);
        }
        if (studentRequest.getGradeId() != null ) {
            student.setGrade(gradeRepository.findById(studentRequest.getGradeId())
                    .orElseThrow(() -> new NoSuchEntityException("There is no such grade for this student")));
        }

        student = studentRepository.save(student);
        studentMapper.updateStudentRequestFromStudent(student, studentRequest);

        return studentRequest;
    }

    @Cacheable(value = "students", key = "{ #root.methodName, #id }")
    public List<Student> findAllStudentsForMartialArt(Long id) {
        List<Grade> allGrades = gradeRepository.findByMartialArtId(id);
        if (allGrades.isEmpty()) {
            throw new NoSuchEntityException("There is no grades for this martial art id");
        }
        List<Student> students = studentRepository.findByGradeIsIn(allGrades);
        if (students.isEmpty()) {
            throw new NoSuchEntityException("There is no students for this martial art id");
        }
        return students;
    }

    @Cacheable(value = "students", key = "{ #root.methodName, #id }")
    public List<Student> findAllStudentsForGrade(Long id) {
        List<Student> students = studentRepository.findByGradeId(id);
        if (students.isEmpty()) {
            throw new NoSuchEntityException("There is no students for this grade id");
        }
        return students;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = SQLException.class)
    public void updateStudentsGrade(Long studentId, Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId).
                orElseThrow(() -> new NoSuchEntityException("There is no such grade"));
        studentRepository.updateStudentsGrade(studentId, grade);
    }
}
