package by.semargl.controller.rest;

import by.semargl.domain.Role;
import by.semargl.domain.User;
import by.semargl.repository.RoleRepository;
import by.semargl.repository.UserRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @ApiOperation(value = "find all roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Roles were successfully found")
    })
    @GetMapping("/all")
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @ApiOperation(value = "find one role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", dataType = "string", paramType = "path",
                    value = "id of role for search", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role was successfully found"),
            @ApiResponse(code = 500, message = "There is no role with such id")
    })
    @GetMapping("/{roleId}")
    public Role findOne(@PathVariable("roleId") Long id) {
        return roleRepository.findById(id).orElseThrow();
    }

    @ApiOperation(value = "remove role from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", dataType = "string", paramType = "path",
                    value = "id of role for deleting from database", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no role with such id")
    })
    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long id) {
        roleRepository.deleteById(id);
    }

    @ApiOperation(value = "create role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role was successfully created")
    })
    @PostMapping("/create")
    public Role createRole(@RequestBody String roleName) {
        Role role = new Role();

        role.setRoleName(roleName.toUpperCase(Locale.ROOT));

        return roleRepository.save(role);
    }

    @ApiOperation(value = "update one role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", dataType = "string", paramType = "path",
                    value = "id of role for update", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role was successfully updated"),
            @ApiResponse(code = 500, message = "There is no role with such id")
    })
    @PutMapping("/update/{roleId}")
    public Role updateRole(@PathVariable("roleId") Long id, @RequestBody String rolename) {
        Role role = roleRepository.findById(id).orElseThrow();

        role.setRoleName(rolename.toUpperCase(Locale.ROOT));

        return roleRepository.save(role);
    }

    @ApiOperation(value = "give the user a new role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", dataType = "string", paramType = "query",
                    value = "id of role for user adding", required = true),
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "query",
                    value = "id of user for new role", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User got a new role"),
            @ApiResponse(code = 500, message = "There is no role with such id")
    })
    @PutMapping("/add_user")
    public Role addUserForRole (@RequestParam Long roleId, @RequestParam Long userId) {
        Role role = roleRepository.findById(roleId).orElseThrow();
        User userForAdding = userRepository.findById(userId).orElseThrow();

        Set<User> users = role.getUsers();
        users.add(userForAdding);
        role.setUsers(users);

        return roleRepository.save(role);
    }

    @ApiOperation(value = "remove user from a role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", dataType = "string", paramType = "query",
                    value = "id of role for user deleting", required = true),
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "query",
                    value = "id of user for exclusion from the list", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was excluded"),
            @ApiResponse(code = 500, message = "There is no role with such id")
    })
    @PutMapping("/delete_user")
    public Role deleteUserForRole (@RequestParam Long roleId, @RequestParam Long userId) {
        Role role = roleRepository.findById(roleId).orElseThrow();
        User userForDeleting = userRepository.findById(userId).orElseThrow();

        Set<User> users = role.getUsers();
        users.remove(userForDeleting);
        role.setUsers(users);

        return roleRepository.save(role);
    }
}
