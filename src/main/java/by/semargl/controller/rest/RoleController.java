package by.semargl.controller.rest;

import by.semargl.domain.Role;
import by.semargl.service.RoleService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @ApiOperation(value = "find all roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Roles were successfully found")
    })
    @GetMapping("/all")
    public List<Role> findAll() {
        return roleService.findAllRole();
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
        return roleService.findOneRole(id);
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
    public void delete(@PathVariable("roleId") Long id) {
        roleService.deleteRole(id);
    }

    @ApiOperation(value = "create role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Role was successfully created")
    })
    @PostMapping("/create")
    public Role create(@RequestBody String roleName) {
        return roleService.createRole(roleName);
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
    public Role update(@PathVariable("roleId") Long id, @RequestBody String roleName) {
        return roleService.updateRole(id, roleName);
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
        return roleService.addUserForRole(roleId, userId);
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
        return roleService.deleteUserForRole(roleId, userId);
    }
}
