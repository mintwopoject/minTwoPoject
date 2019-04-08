package com.qujie.mintwo.system.userRole.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("tbUserRole")
public class TbUserRole  implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableField("UserId")
    private Integer UserId;
    @TableField("RoleId")
    private Integer RoleId;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }
}
