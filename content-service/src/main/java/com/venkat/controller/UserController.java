package com.venkat.controller;

import com.venkat.model.CustomUserDetail;
import com.venkat.model.User;
import com.venkat.service.UserService;
import com.venkat.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserVO getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail principal = (CustomUserDetail)authentication.getPrincipal();
        User user = userService.getUserByEmail(principal.getUsername()).get();
        UserVO userVO = new UserVO(user.getId(),user.getFirstName(),user.getLastName(), user.getEmail(), user.getRole().name());
        return userVO;
    }
}
