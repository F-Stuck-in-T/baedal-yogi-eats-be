package com.fstuckint.baedalyogieats.core.api.user.support;

import com.fstuckint.baedalyogieats.core.api.common.security.UserDetailsImpl;
import com.fstuckint.baedalyogieats.core.api.user.domain.CurrentUser;
import com.fstuckint.baedalyogieats.storage.db.core.user.UserRole;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserRole userRole = UserRole.valueOf(userDetails.getAuthorities().iterator().next().getAuthority());
        return new CurrentUser(userDetails.getUserUuid(), userDetails.getUsername(), userRole);
    }

}
