package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.repository.UserMsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMsRepository userMsRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        com.application.foodhubAdmin.domain.User user = userMsRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return User.withUsername(user.getId())
                .password(user.getPasswd())
                .roles(user.getMembershipType().toString())
                .build();

    }

}