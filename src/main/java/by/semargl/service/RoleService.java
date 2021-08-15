package by.semargl.service;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import by.semargl.domain.Role;
import by.semargl.domain.User;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.RoleRepository;
import by.semargl.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    public Role findOneRole(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Role not found by id " + id));
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role createRole(String roleName) {
        Role role = new Role();

        role.setRoleName(roleName.toUpperCase(Locale.ROOT));

        return roleRepository.save(role);
    }

    public Role updateRole(Long id, String roleName) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Role not found by id " + id));

        role.setRoleName(roleName.toUpperCase(Locale.ROOT));

        return roleRepository.save(role);
    }

    public Role addUserForRole (Long roleId, Long userId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchEntityException("Role not found by id " + roleId));
        User userForAdding = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchEntityException("User not found by id " + userId));

        Set<User> users = role.getUsers();
        users.add(userForAdding);
        role.setUsers(users);

        return roleRepository.save(role);
    }

    public Role deleteUserForRole (Long roleId, Long userId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NoSuchEntityException("Role not found by id " + roleId));
        User userForDeleting = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchEntityException("User not found by id " + userId));

        Set<User> users = role.getUsers();
        users.remove(userForDeleting);
        role.setUsers(users);

        return roleRepository.save(role);
    }
}
