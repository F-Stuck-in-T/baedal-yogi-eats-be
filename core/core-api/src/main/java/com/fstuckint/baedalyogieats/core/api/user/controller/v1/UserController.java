package com.fstuckint.baedalyogieats.core.api.user.controller.v1;

import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.LoginDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.SignupDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.UpdateUserDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.response.UserPageResponse;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.response.UserResponse;
import com.fstuckint.baedalyogieats.core.api.user.domain.UserService;
import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> signUp(@Validated @RequestBody SignupDto signupDto) {
        userService.signUp(signupDto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PostMapping("/authorization")
    public ResponseEntity<ApiResponse<?>> createToken(@RequestBody LoginDto loginDto) {
        String token = userService.createToken(loginDto);
        return ResponseEntity.ok().header(JwtUtils.AUTHORIZATION_HEADER, token).body(ApiResponse.success(token));
    }

    @DeleteMapping("/token")
    public ResponseEntity<ApiResponse<?>> deleteToken(HttpServletRequest request) {
        userService.deleteToken(request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<?>> getUsers(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
            @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "id") String sortKey,
            @RequestParam(defaultValue = "ASC") String direction, HttpServletRequest request) {
        return userService.getUserList(cursor, limit, sortKey, direction, request);
=======
    public ResponseEntity<ApiResponse<?>> getUsers(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
                                                   @RequestParam(defaultValue = "10") Integer limit,
                                                   @RequestParam(defaultValue = "createdAt") String sortKey,
                                                   @RequestParam(defaultValue = "ASC") String direction,
                                                   HttpServletRequest request) {
        UserPageResponse data = userService.getUserList(cursor, limit, sortKey, direction, request);
        return ResponseEntity.ok(ApiResponse.success(data));
>>>>>>> feature/tmp
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable UUID uuid, HttpServletRequest request) {
        UserResponse data = UserResponse.of(userService.getUser(uuid, request));
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PutMapping("/{uuid}")
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<?>> updateUser(@PathVariable UUID uuid, @RequestBody UpdateUserDto updateUserDto,
            HttpServletRequest request) {
        return userService.updateUser(uuid, updateUserDto, request);
=======
    public ResponseEntity<ApiResponse<?>> updateUser(@PathVariable UUID uuid, @RequestBody UpdateUserDto updateUserDto, HttpServletRequest request) {
        UserResponse data = UserResponse.of(userService.updateUser(uuid, updateUserDto, request));
        return ResponseEntity.ok(ApiResponse.success(data));
>>>>>>> feature/tmp
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable UUID uuid, HttpServletRequest request) {
        userService.deleteUser(uuid, request);
        return ResponseEntity.ok(ApiResponse.success());
    }

}