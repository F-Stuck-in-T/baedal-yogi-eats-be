package com.fstuckint.baedalyogieats.core.api.user.controller.v1;

import com.fstuckint.baedalyogieats.core.api.common.jwt.JwtUtils;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.LoginDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.SignupDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.request.UpdateUserDto;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.response.UserPageResponse;
import com.fstuckint.baedalyogieats.core.api.user.controller.v1.response.UserResponse;
import com.fstuckint.baedalyogieats.core.api.user.domain.UserService;
import com.fstuckint.baedalyogieats.core.api.user.support.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
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


    /**
     * 당신은 필터로 대체되었습니다.
     *
     * @PostMapping("/authorization") public ResponseEntity<ApiResponse<?>>
     * createToken(@RequestBody LoginDto loginDto) { log.info("절대 동작 안해!"); String token =
     * userService.createToken(loginDto); return
     * ResponseEntity.ok().header(JwtUtils.AUTHORIZATION_HEADER,
     * token).body(ApiResponse.success(token)); }
     **/

    @DeleteMapping("/token")
    public ResponseEntity<ApiResponse<?>> deleteToken(
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        userService.deleteToken(bearerToken);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getUsers(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime cursor,
            @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "createdAt") String sortKey,

            @RequestParam(defaultValue = "ASC") String direction) {
        UserPageResponse data = userService.getUserList(cursor, limit, sortKey, direction);
        return ResponseEntity.ok(ApiResponse.success(data));

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable UUID uuid) {
        UserResponse data = UserResponse.of(userService.getUser(uuid));
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ApiResponse<?>> updateUser(@PathVariable UUID uuid, @RequestBody UpdateUserDto updateUserDto,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        UserResponse data = UserResponse.of(userService.updateUser(uuid, updateUserDto, bearerToken));
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable UUID uuid,
            @RequestHeader(JwtUtils.AUTHORIZATION_HEADER) String bearerToken) {
        userService.deleteUser(uuid, bearerToken);
        return ResponseEntity.ok(ApiResponse.success());
    }

}