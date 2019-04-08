package com.qujie.mintwo.system.user.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

@TableName("tbUser")
public class TbUser {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer Id;

    @TableField("AccountName")
    private String AccountName;

    @TableField("Password")
    private String Password;

    @TableField("RealName")
    private String RealName;

    @TableField("MobilePhone")
    private String MobilePhone;

    @TableField("Email")
    private String Email;

    @TableField("IsAble")
    private Boolean IsAble;

    @TableField("IfChangePwd")
    private Boolean IfChangePwd;

    @TableField("Description")
    private String Description;

    @TableField("CreateBy")
    private String CreateBy;

    @TableField("CreateTime")
    private Date CreateTime;

    @TableField("UpdateBy")
    private String UpdateBy;

    @TableField("UpdateTime")
    private Date UpdateTime;

    @TableField("PID")
    private Integer PID;

    @TableField("SaleBrokerage")
    private BigDecimal SaleBrokerage;

    @TableField("HZType")
    private String HZType;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getAble() {
        return IsAble;
    }

    public void setAble(Boolean able) {
        IsAble = able;
    }

    public Boolean getIfChangePwd() {
        return IfChangePwd;
    }

    public void setIfChangePwd(Boolean ifChangePwd) {
        IfChangePwd = ifChangePwd;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }

    public Integer getPID() {
        return PID;
    }

    public void setPID(Integer PID) {
        this.PID = PID;
    }

    public BigDecimal getSaleBrokerage() {
        return SaleBrokerage;
    }

    public void setSaleBrokerage(BigDecimal saleBrokerage) {
        SaleBrokerage = saleBrokerage;
    }

    public String getHZType() {
        return HZType;
    }

    public void setHZType(String HZType) {
        this.HZType = HZType;
    }
}
