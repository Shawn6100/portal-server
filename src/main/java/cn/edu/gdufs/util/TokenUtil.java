package cn.edu.gdufs.util;

import cn.edu.gdufs.constant.CacheConstant;
import cn.edu.gdufs.exception.ApiException;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Description: Token工具类
 * Author: 严仕鹏
 * Date: 2022/12/2
 */
@Component
public class TokenUtil {

    private final RedisUtil redisUtil;

    private static final long SEVEN_DAY_TIME_SECOND = 60 * 60 * 24 * 7;

    public TokenUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 发放token
     *
     * @param userId   用户id
     * @param role 权限
     * @return 获取到的token
     */
    public String grantToken(int userId, int role) {
        String tokenKey = String.format(CacheConstant.TOKEN_KEY, userId);

        // 删除之前的token
        String lastToken = (String) redisUtil.get(tokenKey);
        if (lastToken != null) {
            redisUtil.del(lastToken);
        }

        // token携带信息
        String tokenInfo = String.format(CacheConstant.TOKEN_INFO, userId, role);
        String token = UUID.randomUUID().toString().replace("-", "");

        // 设置过期时间为 7 天
        redisUtil.set(tokenKey, token, SEVEN_DAY_TIME_SECOND);
        redisUtil.set(token, tokenInfo, SEVEN_DAY_TIME_SECOND);

        return token;
    }

    /**
     * 获取token对应的用户信息数组
     *
     * @param token token
     * @return 用户id和用户权限
     */
    public int[] getInfoByToken(String token) {
        String tokenInfo = (String) redisUtil.get(token);
        if (tokenInfo == null || "".equals(tokenInfo)) {
            throw new ApiException("token失效");
        }
        int index = tokenInfo.indexOf("_");
        int userId = Integer.parseInt(tokenInfo.substring(0, index));
        int role = Integer.parseInt(tokenInfo.substring(index + 1));
        return new int[]{userId, role};
    }

    /**
     * 获取token对应的用户id
     *
     * @param token token
     * @return 用户id
     */
    public int getIdByToken(String token) {
        return getInfoByToken(token)[0];
    }

    /**
     * 获取token对应的权限
     *
     * @param token token
     * @return 权限
     */
    public int getRoleByToken(String token) {
        return getInfoByToken(token)[1];
    }

    /**
     * 刷新token
     */
    public void refreshToken(String token, int userId) {
        Long last = redisUtil.getExpire(token); // 获取token剩余时间
        if (last <= (60 * 60 * 24)) { // 有效期小于一天
            // 更新有效期
            redisUtil.setExpire(String.format(CacheConstant.TOKEN_KEY, userId), SEVEN_DAY_TIME_SECOND);
            redisUtil.setExpire(token, SEVEN_DAY_TIME_SECOND);
        }
    }
}
