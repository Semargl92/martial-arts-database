package by.semargl.controller.rest;

import by.semargl.controller.requests.UserRequest;
import by.semargl.controller.requests.mappers.UserMapper;
import by.semargl.domain.User;
import by.semargl.repository.UserRepository;
import by.semargl.util.UserGenerator;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
//@ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Request was successfully performed!"),
//        @ApiResponse(code = 500, message = "Internal server error! https://stackoverflow.com/questions/37405244/how-to-change-the-response-status-code-for-successful-operation-in-swagger")
//})
public class UserController {

    private final UserRepository userRepository;
    private final UserGenerator userGenerator;
    private final UserMapper userMapper;

    @ApiOperation(value = "find all users")
    @GetMapping("/all/admin")
    public Page<User> findAll() {
        System.out.println("find all");
        return userRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "id")));
    }

    @ApiOperation(value = "find all existing users")
    @GetMapping("/all")
    public List<User> findAllExisting() {
        System.out.println("find all existing");
        return userRepository.findByIsDeletedFalse();
    }

    @ApiOperation(value = "find one user")
    @GetMapping("/user/{userId}")
    public User findOne(@PathVariable("userId") Long id) {
        System.out.println("find one");
        return userRepository.findById(id).orElseThrow();
    }

    @ApiOperation(value = "remove user from the database")
    @GetMapping("/delete/admin/{userId}")
    public void delete(@PathVariable("userId") Long id) {
        System.out.println("hard delete");
        userRepository.deleteById(id);
    }

    @ApiOperation(value = "set user as deleted")
    @GetMapping("/delete/{userId}")
    public void softDelete(@PathVariable("userId") Long id) {
        System.out.println("soft delete");
        userRepository.softDelete(id);
    }

    @ApiOperation(value = "create one user")
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
    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable("userId") Long id, @RequestBody UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow();

        userMapper.updateUserFromUserRequest(userRequest, user);
        user.setChanged(LocalDateTime.now());

        return userRepository.save(user);
    }

    @ApiOperation(value = "Generate auto users in system")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "usersCount", dataType = "string", paramType = "path",
                    value = "Count of generated users", required = true, defaultValue = "100")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Users was successfully created!"),
            @ApiResponse(code = 500, message = "Internal server error! https://stackoverflow.com/questions/37405244/how-to-change-the-response-status-code-for-successful-operation-in-swagger")
    })
    @PostMapping("/generate/{usersCount}")
    public List<User> generateUsers(@PathVariable("usersCount") Integer count) {
        throw new RuntimeException("Haha!");
//        List<User> generateUsers = userGenerator.generate(count);
//        userRepository.batchInsert(generateUsers);
//
//        return userRepository.findAll();
    }

   /* @ApiImplicitParams({
            @ApiImplicitParam(name = "Secret-Key", dataType = "string", paramType = "header",
                    value = "Secret header for secret functionality!! Hoho")
    })
    @GetMapping("/hello")
    public List<User> securedFindAll(HttpServletRequest request) {
        return userRepository.findAll();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", dataType = "string", paramType = "query", value = "Limit users in result list"),
            @ApiImplicitParam(name = "query", dataType = "string", paramType = "query", value = "Search query"),
    })
    @GetMapping("/search")
    public List<User> userSearch(@RequestParam Integer limit, @RequestParam String query) {
        return userRepository.findUsersByQuery(limit, query);
    } */
}
