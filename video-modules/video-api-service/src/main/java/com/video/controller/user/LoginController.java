package com.video.controller.user;

import ai.yue.library.base.view.R;
import ai.yue.library.base.view.Result;
import com.video.common.core.domain.R;
import com.video.model.req.LoginReq;
import hupu.business.common.config.aop.NotRepeatSubmit;
import hupu.business.common.domain.LoginUser;
import hupu.business.common.enums.CodeBusTypeEnum;
import hupu.business.common.enums.LoginTypeEnum;
import hupu.business.common.thread.UserLocal;
import hupu.business.member.pojo.vo.req.BaseLoginExtReq;
import hupu.business.member.service.ILoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    private ILoginService loginService;

    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public R<?> login(@RequestBody @Valid LoginReq req, HttpServletRequest request) {
        LoginUser loginUser = UserLocal.getContext();

        CodeBusTypeEnum codeBusTypeEnum = CodeBusTypeEnum.ofOpt(type);
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.ofOpt(loginType);

        // 附加信息
        BaseLoginExtReq baseLoginExtReq = BaseLoginExtReq.builder()
                .deviceCode(deviceCode)
                .deviceModel(deviceModel)
                .deviceSystemVersion(deviceSystemVersion)
                .memberAppVersion(memberAppVersion)
                .build();

        return loginService.baseLogin(loginUser.getTenantSys(), codeBusTypeEnum, loginTypeEnum, number, pwd, tel, loginUser.getTerminal().getCode(), code, baseLoginExtReq);
    }

//    /**
//     * 注册 - （邮箱密码版）
//     */
//    @PostMapping("register/pwd")
//    public Result<?> registerPwd(
//            @RequestParam(value = "number", required = true) String number, // 账号
//            @RequestParam(value = "pwd", required = true) String pwd // 密码
//    ) {
//        LoginUser loginUser = UserLocal.getContext();
//        return loginService.registerPwd(loginUser.getTenantSys(), number, pwd);
//    }


    /**
     * token刷新 (遗留问题? 会返回新的token 多端不统一造成问题 建议token不变 刷新过期时间)
     */
    @PostMapping("refresh/token")
    public Result<?> refreshToken() {
        return R.success(loginService.refreshToken(UserLocal.getMemberId()));
    }

}
