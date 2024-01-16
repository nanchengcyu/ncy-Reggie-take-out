package cn.nanchengyu.reggie.common;

/**
 * ClassName: BaseContext
 * Package: cn.nanchengyu.reggie.common
 * Description:
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录用户的id
 *
 * @Author 南城余
 * @Create 2024/1/16 12:31
 * @Version 1.0
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    //每个线程互不干扰
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
