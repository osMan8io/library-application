package libraryapp.service.impl;

import libraryapp.entity.User;
import libraryapp.entity.common.UserPrinciple;
import libraryapp.repository.UserRepository;
import libraryapp.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =  userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserPrinciple(user);
    }

}
