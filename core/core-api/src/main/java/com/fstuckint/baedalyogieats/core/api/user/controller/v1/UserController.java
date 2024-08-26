package com.fstuckint.baedalyogieats.core.api.user.controller.v1;

import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.LoginDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.SignupDto;
import com.fstuckint.baedalyogieats.core.api.user.domain.UserService;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<?>> signUp(@RequestBody SignupDto signupDto) {
        return userService.signUp(signupDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getUsers(@RequestHeader("Authorization") String token,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
                                                   @PageableDefault(size = 10) Pageable pageable,
                                                   @RequestParam(required = false) String sortKey,
                                                   @RequestParam(required = false) String direction) {
        return userService.getUserList(token, cursor, pageable, sortKey, direction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}