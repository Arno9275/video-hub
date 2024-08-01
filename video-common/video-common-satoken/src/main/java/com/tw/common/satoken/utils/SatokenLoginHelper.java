package com.tw.common.satoken.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录鉴权助手
 * <p>
 * user_type 为 用户类型 同一个用户表 可以有多种用户类型 例如 pc,app
 * deivce 为 设备类型 同一个用户类型 可以有 多种设备类型 例如 web,ios
 * 可以组成 用户类型与设备类型多对多的 权限灵活控制
 * <p>
 * 多用户体系 针对 多种用户类型 但权限控制不一致
 * 可以组成 多用户类型表与多设备类型 分别控制权限
 *
 * @author Lion Li
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SatokenLoginHelper {

    public static final String USER_KEY = "satoken:uid";

    public static final Long outTime = 15L * 24L * 60L * 60L;

    /**
     * 登录系统 基于 设备类型
     * 针对相同用户体系不同设备
     *
     * @param uid     登录用户信息
     * @param timeOut 过期时间 秒
     */
    public static String loginByOs(Long uid, Integer os, Long timeOut) {
        SaStorage storage = SaHolder.getStorage();
        storage.set(USER_KEY, uid);
        SaLoginModel model = new SaLoginModel();
//        if (ObjectUtil.isNotNull(os)) {
//            model.setDevice(os.toString());
//        }
        /**
         * 过期时间
         */
        if (ObjectUtil.isNotNull(timeOut)) {
            model.setTimeout(timeOut);
        }
        model.setTimeout(timeOut);
        // 判断是否有token 有则不传
        String tokenValueByLoginId = StpUtil.getTokenValueByLoginId(uid);
        if (StrUtil.isNotEmpty(tokenValueByLoginId)) {
            StpUtil.logout(uid);
        }
        StpUtil.login(uid, model.setExtra(USER_KEY, uid));
        return StpUtil.getTokenValue();
    }


    /**
     * 获取用户id
     */
    public static String getUserId() {
        String userId = null;
        try {
            userId = Convert.toStr(SaHolder.getStorage().get(USER_KEY));
            if (ObjectUtil.isNull(userId)) {
                userId = Convert.toStr(StpUtil.getExtra(USER_KEY));
                SaHolder.getStorage().set(USER_KEY, userId);
            }
            if(StrUtil.isBlank(userId)){
                Object loginId = StpUtil.getLoginId();
                if(ObjectUtil.isNotNull(loginId)){
                    return String.valueOf(StpUtil.getLoginId());
                }
            }
        } catch (Exception e) {
            return null;
        }
        return userId;
    }

    /**
     * 获取用户id long
     */
    public static Long getLoginIdAsLong() {
        // 先判断是否带token ,有些业务场景需要
        if (StrUtil.isBlank(StpUtil.getTokenValue())) {
            return null;
        }
        Long uid = null;
        try {
            uid = StpUtil.getLoginIdAsLong();
        }catch (Exception e){
            return null;
        }
        return uid;
    }

    /**
     * 获取用户id
     */
    public static String getUserIdByToken(String token) {
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);
        if (ObjectUtil.isNotNull(loginIdByToken)) {
            return String.valueOf(loginIdByToken);
        }
        return null;
    }

    public static String renewTimeout(String token) {
        // 判断token 是否过期
        StpUtil.renewTimeout(token, outTime);
        return token;
    }

    /**
     * 将用户下线
     * @param uid
     */
    public static void logout(Long uid){
        String tokenValue = StpUtil.getTokenValueByLoginId(uid);
        log.info("{}将用户下线:token={}", uid, tokenValue);
        StpUtil.logout(uid);
    }

}
