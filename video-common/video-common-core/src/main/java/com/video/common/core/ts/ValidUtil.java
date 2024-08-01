package com.video.common.core.ts;

import org.bouncycastle.cert.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

public class ValidUtil {
    private static final Logger log = LoggerFactory.getLogger(ValidUtil.class);


    public static boolean required(Req t) {
        try {
            if (null != t) {
                Field[] fields = t.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (field.isAnnotationPresent(Required.class)) {
                        field.setAccessible(true);

                        Object value = field.get(t);

                        String fieldType = field.getType().toString();
                        if (fieldType.endsWith("String")) {
                            if (null == value || !StringUtils.hasText(value.toString())) {
                                return false;
                            }
                        } else {
                            if (null == value) {
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
        } catch (IllegalAccessException e) {

        }
        return false;
    }

    public static boolean in(Object value, List valueList) {
        return valueList.contains(value);
    }

    public static String getUid(HttpServletRequest request) {
        // 先判断是否带token ,有些业务场景需要
        if (StrUtil.isBlank(StpUtil.getTokenValue())) {
            return null;
        }
        return SatokenLoginHelper.getUserId();
    }


    public static Long getUid() {
        // 先判断是否带token ,有些业务场景需要
        if (StrUtil.isBlank(StpUtil.getTokenValue())) {
            return null;
        }
        return StpUtil.getLoginIdAsLong();
    }

    public static String getLanguage(HttpServletRequest request) {
        if (request == null){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            request = attributes.getRequest();
        }
        if(request !=null){
            String language = request.getHeader("X-Language");
            log.info("X-Language:{}", language);
            if(StrUtil.isEmpty(language)){
                language = request.getHeader("x-language");
                log.info("x-language:{}", language);
            }
            if(StrUtil.isEmpty(language) || !CollUtil.contains(ResponseMessageI18N.languageList, language)){
                return ResponseMessageI18N.DEFAULT_LANGUAGE;
            }
            return language;
        }
        return ResponseMessageI18N.DEFAULT_LANGUAGE;
    }

    public static Integer getOs(HttpServletRequest request) {
        if (request == null){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            request = attributes.getRequest();
        }
        if(request !=null){
            String os = request.getHeader("os");
            if(!hasText(os)){
                return 5;
            }
            return Integer.valueOf(os);
        }
        return 5;
    }

    public static String getLanguage() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes !=null) {
            HttpServletRequest request = attributes.getRequest();
            if(request !=null){
                String language = request.getHeader("X-Language");
                log.info("X-Language:{}", language);
                if(StrUtil.isEmpty(language)){
                    language = request.getHeader("x-language");
                    log.info("x-language:{}", language);
                }
                if(StrUtil.isEmpty(language) || !CollUtil.contains(ResponseMessageI18N.languageList, language)){
                    return ResponseMessageI18N.DEFAULT_LANGUAGE;
                }
                return language;
            }
        }
        return ResponseMessageI18N.DEFAULT_LANGUAGE;
    }

    public static String getTenantSys(@Nullable HttpServletRequest request) {
        if (request == null){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            request = attributes.getRequest();
        }
        if(request !=null){
            String tenantSys = request.getHeader("tenantSys");
            return tenantSys;
        }
        return null;
    }

    public static String getIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getHeader("X-CLIENT-IP");
    }
}
