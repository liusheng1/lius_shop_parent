package com.ls.member.mapper.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库操作实例〉
 *
 * @author liusheng
 * @create 2019/8/8
 * @since 1.0.0
 */
@Data
public class UserDo {
    /**
     * userid
     */
    private Long userid;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
        @ApiModelProperty(value = "密码")
        private String password;
    /**
     * 用户名称
     */

    private String userName;
    /**
     * 性别 0 男 1女
     */

    private char sex;
    /**
     * 年龄
     */

    private Long age;
    /**
     * 注册时间
     */

    private Date createTime;
    /**
     * 修改时间
     *
     */

    private Date updateTime;
    /**
     * 账号是否可以用 1 正常 0冻结
     */

    private char is_avalible;
    /**
     * 用户头像
     */

    private String pic_img;
    /**
     * 用户关联 QQ 开放ID
     */

    private Date qq_openid;
    /**
     * 用户关联 微信 开放ID
     */

    private Date WX_OPENID;


}
