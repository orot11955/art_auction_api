package com.sparta.camp.java.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class JwtUtil {

  // 현재 인증된 사용자의 ID를 반환
  public static Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getDetails() instanceof Long) {
      return (Long) authentication.getDetails();
    }
    return null;
  }

  // 현재 인증된 사용자의 이름을 반환
  public static String getCurrentUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getName() != null
        && !"anonymousUser".equals(authentication.getName())) {
      return authentication.getName();
    }
    return null;
  }

  // 현재 인증된 사용자의 권한을 반환
  public static String getCurrentUserRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getAuthorities() != null
        && !authentication.getAuthorities().isEmpty()) {
      return authentication.getAuthorities().iterator().next().getAuthority();
    }
    return null;
  }

  // 현재 사용자가 인증되었는지 확인
  public static boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null &&
        authentication.isAuthenticated() &&
        !"anonymousUser".equals(authentication.getName());
  }

  // 현재 사용자가 특정 역할을 가지고 있는지 확인
  public static boolean hasRole(String role) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getAuthorities() != null) {
      return authentication.getAuthorities().stream()
          .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
    return false;
  }
}

