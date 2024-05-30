package com.diarpy.taskmanagementsystem;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.1
 */

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    private final MyUserRepository userRepository;

    public MyUserDetailsServiceImpl(MyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "Not Found"));
        return new MyUserAdapter(myUser);
    }
}
