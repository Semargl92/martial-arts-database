package by.semargl.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.SecondaryTable;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.controller.requests.UserCreateRequest;
import by.semargl.controller.requests.UserRequest;
import by.semargl.controller.requests.mappers.UserCreateMapper;
import by.semargl.controller.requests.mappers.UserMapper;
import by.semargl.domain.Credentials;
import by.semargl.domain.Student;
import by.semargl.domain.User;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.StudentRepository;
import by.semargl.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final UserMapper userMapper;
    private final UserCreateMapper userCreateMapper;

    public Page<User> findAllUsers() {
        return userRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "id")));
    }

    @Cacheable("users")
    public List<UserRequest> findAllExistingUsers() {
        List<User> notDeletedUsers = userRepository.findByIsDeletedFalse();
        return wrapUsersListWithUserRequest(notDeletedUsers);
    }

    public User findOneUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("User not found by id " + id));
    }

    @Cacheable("users")
    public UserRequest findOneExistingUser(Long id) {
        UserRequest request = new UserRequest();
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NoSuchEntityException("User not found by id " + id));
        userMapper.updateUserRequestFromUser(user, request);
        request.setLogin(user.getCredentials().getLogin());
        return request;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteUserWithOrphans(Long id) {
        User user = findOneUser(id);
        studentRepository.deleteWithUser(user);
        userRepository.delete(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void softDeleteUser(Long id) {
        User user = findOneUser(id);
        Set<Student> students = user.getStudents();
        if(!students.isEmpty()) {
            for (Student student : students) {
                studentRepository.softDeleteStudent(student.getId());
            }
        }
        userRepository.softDeleteUser(id);
    }

    public UserRequest createUser(UserCreateRequest userCreateRequest) {
        User user = new User();

        userCreateMapper.updateUserFromUserCreateRequest(userCreateRequest, user);
        user.setCreated(LocalDateTime.now());
        user.setChanged(LocalDateTime.now());
        user.setIsDeleted(false);

        UserRequest request = new UserRequest();
        user = userRepository.save(user);
        userMapper.updateUserRequestFromUser(user, request);
        request.setLogin(user.getCredentials().getLogin());

        return request;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public UserRequest updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("User not found by id " + id));

        userMapper.updateUserFromUserRequest(userRequest, user);
        user.setChanged(LocalDateTime.now());
        if (userRequest.getLogin() != null ) {
            Credentials loginUpdate = user.getCredentials();
            loginUpdate.setLogin(userRequest.getLogin());
            user.setCredentials(loginUpdate);
        }

        UserRequest request = new UserRequest();
        user = userRepository.save(user);
        userMapper.updateUserRequestFromUser(user, request);
        request.setLogin(user.getCredentials().getLogin());

        return request;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public Credentials updateCredentials(Long id, Credentials credentials) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("User not found by id " + id));

        Credentials forUpdate = user.getCredentials();
        if (credentials.getLogin() != null ) {
            forUpdate.setLogin(credentials.getLogin());
        }
        if (credentials.getPassword() != null ) {
            forUpdate.setPassword(credentials.getPassword());
        }
        user.setCredentials(forUpdate);
        user.setChanged(LocalDateTime.now());
        userRepository.save(user);

        return forUpdate;
    }

    @Cacheable("users")
    public List<UserRequest> findUserByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        users.removeIf(User::getIsDeleted);
        if (users.isEmpty()) {
            throw new NoSuchEntityException("There is no users with similar names");
        }
        return wrapUsersListWithUserRequest(users);
    }

    @Cacheable("users")
    public List<UserRequest> findUserByNameAndSurname(String name, String surname) {
        List<User> users = userRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(name, surname);
        users.removeIf(User::getIsDeleted);
        if (users.isEmpty()) {
            throw new NoSuchEntityException("There is no users with similar names and surnames");
        }
        return wrapUsersListWithUserRequest(users);
    }

    private List<UserRequest> wrapUsersListWithUserRequest (List<User> users) {
        List<UserRequest> result = new ArrayList<>();
        for (User user : users) {
            UserRequest request = new UserRequest();
            userMapper.updateUserRequestFromUser(user, request);
            request.setLogin(user.getCredentials().getLogin());
            result.add(request);
        }
        return result;
    }
}
