package com.example.demo_rest_2.security.controller;

import com.example.demo_rest_2.security.entity.MyUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
class UserController {

    @GetMapping("/welcome")
    public String welcome() { return "Endpoint is not secure";}

    @GetMapping("/user")
    public String userProfile(
            @AuthenticationPrincipal MyUserDetails userDetails
    ) {
        return "User Profile: "
                + userDetails.getFirstName() + " "
                + userDetails.getLastName();
    }

    @GetMapping("/admin")
    public String adminProfile(
            @AuthenticationPrincipal MyUserDetails userDetails
    ) {
        return "Admin Profile: "
                + userDetails.getFirstName() + " "
                + userDetails.getLastName();
    }
}

