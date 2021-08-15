package by.semargl.controller.rest;

import java.util.List;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

import by.semargl.controller.requests.UserCreateRequest;
import by.semargl.controller.requests.UserRequest;
import by.semargl.domain.Credentials;
import by.semargl.domain.User;
import by.semargl.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "find all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found")
    })
    @GetMapping("/admin")
    public Page<User> findAll() {
        return userService.findAllUsers();
    }

    @ApiOperation(value = "find all existing users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found")
    })
    @GetMapping
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
    @GetMapping("/admin/{userId}")
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
    @GetMapping("/{userId}")
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
    @DeleteMapping("/admin/{userId}")
    public void delete(@PathVariable("userId") Long id) {
        userService.deleteUser(id);
    }


    @ApiOperation(value = "remove user with orphans")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for deleting from database", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @DeleteMapping("delete_with_students/admin/{userId}")
    public void deleteWithOrphans(@PathVariable("userId") Long id) {
        userService.deleteUserWithOrphans(id);
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
    @PostMapping
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
    @PutMapping("/{userId}")
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
    @PatchMapping("/update_credentials/{userId}")
    public Credentials updateCredentials(@PathVariable("userId") Long id, @RequestBody Credentials credentials) {
        return userService.updateCredentials(id, credentials);
    }
}
