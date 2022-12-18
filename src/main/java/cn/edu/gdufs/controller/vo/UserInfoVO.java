package cn.edu.gdufs.controller.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Author: 程诗怡
 * Date: 2022/12/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {

    /**
     * 用户id
     */
    @JSONField(ordinal = 1)
    private Long id;

    /**
     * 用户邮箱
     */
    @JSONField(ordinal = 2)
    private String email;

    /**
     * 用户头像路径
     */
    @JSONField(ordinal = 3)
    private String avatar;

    /**
     * 用户昵称
     */
    @JSONField(ordinal = 4)
    private String nickname;

}
