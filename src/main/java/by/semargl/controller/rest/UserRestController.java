package by.semargl.controller.rest;

import by.semargl.controller.requests.UserCreateRequest;
import by.semargl.domain.User;
import by.semargl.repository.UserRepository;
import by.semargl.util.UserGenerator;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/rest/users")
@RequiredArgsConstructor
//@ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Request was successfully performed!"),
//        @ApiResponse(code = 500, message = "Internal server error! https://stackoverflow.com/questions/37405244/how-to-change-the-response-status-code-for-successful-operation-in-swagger")
//})
public class UserRestController {

    private final UserRepository userRepository;
    private final UserGenerator userGenerator;

    @GetMapping
    public List<User> findAll() {
        System.out.println("In rest controller");
        return userRepository.findAll();
    }


    @ApiImplicitParams({
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
    }

    @ApiOperation(value = "Creating one user")
    @PostMapping
    public User createUser(@RequestBody UserCreateRequest createRequest) {
        User generatedUser = userGenerator.generate();
        generatedUser.setWeight(createRequest.getWeight());
        generatedUser.setLogin(createRequest.getLogin());
        generatedUser.setName(createRequest.getName());
        generatedUser.setSurname(createRequest.getSurname());

        return userRepository.save(generatedUser);
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
}
