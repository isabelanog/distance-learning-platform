package com.dlp.authuser.configs.security;

import com.dlp.authuser.models.UserModel;
import com.dlp.authuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.getUserModelByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
        return UserDetailsImpl.build(userModel);
    }
}
