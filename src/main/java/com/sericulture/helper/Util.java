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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public final class Util {

    static DecimalFormat decimalFormat = new DecimalFormat("#.##");


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

    public static int objectToInteger(Object object) {
        return object == null ? 0 : Integer.parseInt(String.valueOf(object));
    }

    public static String objectToString(Object object) {
        return object == null ? "" : String.valueOf(object);
    }

    public static float objectToFloat(Object object) {
        return object == null ? 0 : Float.valueOf(decimalFormat.format(Float.parseFloat(String.valueOf(object))));
    }

    public static LocalDate getISTLocalDate() {
        LocalDateTime l = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        return l.toLocalDate();
    }


    public static long objectToLong(Object object) {
        return object == null ? 0 : Long.parseLong(String.valueOf(object));
    }
}
