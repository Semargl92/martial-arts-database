package by.semargl.security.service;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.semargl.domain.Role;
import by.semargl.domain.User;
import by.semargl.service.UserService;

@Service
@RequiredArgsConstructor
public class UserProviderService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
                User user = userService.findByLogin(username);
                return new org.springframework.security.core.userdetails.User(
                        user.getCredentials().getLogin(),
                        user.getCredentials().getPassword(),
                        AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.joining(",")))
                );
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }
}
