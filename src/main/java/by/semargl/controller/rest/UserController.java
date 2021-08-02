package by.semargl.controller.rest;

import by.semargl.controller.requests.UserRequest;
import by.semargl.controller.requests.mappers.UserMapper;
import by.semargl.domain.User;
import by.semargl.repository.UserRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @ApiOperation(value = "find all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found")
    })
    @GetMapping("/all/admin")
    public Page<User> findAll() {
        return userRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "id")));
    }

    @ApiOperation(value = "find all existing users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found")
    })
    @GetMapping("/all")
    public List<UserRequest> findAllExisting() {
        List<User> notDeletedUsers = userRepository.findByIsDeletedFalse();
        List<UserRequest> result = new ArrayList<>();
        for (User user : notDeletedUsers) {
            UserRequest request = new UserRequest();
            userMapper.updateUserRequestFromUser(user, request);
            result.add(request);
        }
        return result;
    }

    @ApiOperation(value = "find one user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for search", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully found"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @GetMapping("/user/{userId}")
    public User findOne(@PathVariable("userId") Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @ApiOperation(value = "remove user from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for deleting from database", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @DeleteMapping("/delete/admin/{userId}")
    public void delete(@PathVariable("userId") Long id) {
        userRepository.deleteById(id);
    }

    @ApiOperation(value = "set user as deleted")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for soft delete", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @PutMapping("/delete/{userId}")
    public void softDelete(@PathVariable("userId") Long id) {
        userRepository.softDelete(id);
    }

    @ApiOperation(value = "create one user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully created"),
            @ApiResponse(code = 500, message = "User with this login already exists, please try another option")
    })
    @PostMapping("/create")
    public User createUser(@RequestBody UserRequest userRequest) {
        User user = new User();

        userMapper.updateUserFromUserRequest(userRequest, user);
        user.setCreated(LocalDateTime.now());
        user.setChanged(LocalDateTime.now());
        user.setIsDeleted(false);

        return userRepository.save(user);
    }

    @ApiOperation(value = "update one user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for update", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully updated"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable("userId") Long id, @RequestBody UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow();

        userMapper.updateUserFromUserRequest(userRequest, user);
        user.setChanged(LocalDateTime.now());

        return userRepository.save(user);
    }
}
