package by.semargl.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.controller.requests.UserCreateRequest;
import by.semargl.controller.requests.UserRequest;
import by.semargl.controller.requests.mappers.UserCreateMapper;
import by.semargl.controller.requests.mappers.UserMapper;
import by.semargl.domain.Credentials;
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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteUserWithOrphans(Long id) {
        User user = findOneUser(id);
        studentRepository.deleteWithUser(user);
        userRepository.delete(user);
    }

    @Transactional
    public void softDeleteUser(Long id) {
        userRepository.softDelete(id);
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

    public List<UserRequest> findUserByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        if (users.isEmpty()) {
            throw new NoSuchEntityException("There is no users with similar names");
        }
        return wrapUsersListWithUserRequest(users);
    }

    public List<UserRequest> findUserByNameAndSurname(String name, String surname) {
        List<User> users = userRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(name, surname);
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
