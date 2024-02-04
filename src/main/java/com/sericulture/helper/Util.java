package com.sericulture.helper;

import com.sericulture.authentication.model.JwtPayloadData;
import com.sericulture.authentication.service.UserInfoDetails;
import com.sericulture.authentication.utils.TokenDecrypterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public final class Util {


    @Autowired
    ApplicationContext applicationContext;

    public static JwtPayloadData getTokenValues() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String token = ((UserInfoDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getJwtToken();
        return TokenDecrypterUtil.extractJwtPayload(token);
    }
    public static long getUserId(JwtPayloadData jwtPayloadData) {
        return jwtPayloadData.getUserMasterId();
    }
}
