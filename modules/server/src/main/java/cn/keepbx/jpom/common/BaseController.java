package cn.keepbx.jpom.common;

import cn.keepbx.jpom.common.interceptor.LoginInterceptor;
import cn.keepbx.jpom.model.data.UserModel;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Objects;

/**
 * base
 *
 * @author jiangzeyin
 * @date 2018/9/28
 */
public abstract class BaseController extends BaseJpomController {
    private static final ThreadLocal<UserModel> USER_MODEL_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void resetInfo() {
        USER_MODEL_THREAD_LOCAL.set(getUserModel());
    }

    protected UserModel getUser() {
        UserModel userModel = USER_MODEL_THREAD_LOCAL.get();
        Objects.requireNonNull(userModel);
        return userModel;
    }

    public static void remove() {
        USER_MODEL_THREAD_LOCAL.remove();
    }

    public static UserModel getUserModel() {
        return (UserModel) getRequestAttributes().getAttribute(LoginInterceptor.SESSION_NAME, RequestAttributes.SCOPE_SESSION);
    }


    /**
     * 获取当前登录人
     *
     * @return 用户名
     */
    public static String getUserName() {
        UserModel userModel = getUserModel();
        if (userModel == null) {
            return null;
        }
        return userModel.getId();
    }

    public static String getOptUserName() {
        UserModel userModel = getUserModel();
        String userId;
        if (userModel.isSystemUser()) {
            userId = UserModel.SYSTEM_OCCUPY_NAME;
        } else {
            userId = userModel.getId();
        }
        return userId;
    }

}
