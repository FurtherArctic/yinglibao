package com.bjpowernode.web.service;

import com.bjpowernode.common.RCode;
import com.bjpowernode.db.domain.UserDO;
import com.bjpowernode.web.struct.request.UserParam;

import java.util.Map;

/**
 * @author wangjunchen
 */
public interface UserService {
    /**
     * 用户注册
     *
     * @param param 用户参数对象
     * @return 枚举值
     */
    RCode userRegister(UserParam param);

    /**
     * 用户登录验证
     *
     * @param param 前端发送的登录信息，即用户参数对象
     * @return 用户信息
     */
    UserDO userLogin(UserParam param);

    /**
     * 将token存储到redis中
     *
     * @param token     令牌
     * @param redisData 键值对数据，field和value
     * @return boolean
     */
    boolean saveTokenRedis(String token, Map<String, String> redisData);

    /**
     * 根据用户用户id获取用户信息
     *
     * @param uid 用户id
     * @return 用户信息
     */
    UserDO queryById(Integer uid);

    /**
     * 实名认证
     *
     * @param uid    用户id
     * @param name   用户实际名字
     * @param idCard 用户身份证号
     * @return RCode枚举值
     */
    RCode doRealName(Integer uid, String name, String idCard);

    /**
     * 存储uid和token
     *
     * @param token token
     * @param id    用户id
     */
    void saveUidTokenRedis(String token, Integer id);

    /**
     * 获取已存在的token
     *
     * @param id 用户
     * @return token
     */
    String getTokenForUid(Integer id);
}
