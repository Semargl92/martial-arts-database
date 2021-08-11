package by.semargl.controller.rest;

import by.semargl.controller.requests.UserCreateRequest;
import by.semargl.controller.requests.UserRequest;
import by.semargl.domain.Credentials;
import by.semargl.domain.User;
import by.semargl.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "find all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found")
    })
    @GetMapping("/all/admin")
    public Page<User> findAll() {
        return userService.findAllUsers();
    }

    @ApiOperation(value = "find all existing users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found")
    })
    @GetMapping("/all")
    public List<UserRequest> findAllExisting() {
        return userService.findAllExistingUsers();
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
    @GetMapping("/user/admin/{userId}")
    public User findOne(@PathVariable("userId") Long id) {
        return userService.findOneUser(id);
    }

    @ApiOperation(value = "find one existing user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for search", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully found"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @GetMapping("/user/{userId}")
    public UserRequest findOneExisting(@PathVariable("userId") Long id) {
        return userService.findOneExistingUser(id);
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
        userService.deleteUser(id);
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
        userService.softDeleteUser(id);
    }

    @ApiOperation(value = "create one user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully created"),
            @ApiResponse(code = 500, message = "User with this login already exists, please try another option")
    })
    @PostMapping("/create")
    public UserRequest create(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
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
    public UserRequest update(@PathVariable("userId") Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @ApiOperation(value = "update credentials for user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for update", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Credentials were successfully updated"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @PutMapping("/update_credentials/{userId}")
    public Credentials updateCredentials(@PathVariable("userId") Long id, @RequestBody Credentials credentials) {
        return userService.updateCredentials(id, credentials);
    }
}
