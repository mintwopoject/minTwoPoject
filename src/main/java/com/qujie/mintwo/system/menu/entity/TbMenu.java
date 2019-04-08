package com.qujie.mintwo.system.menu.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2019-04-02
 */

@TableName("tbMenu")
public class TbMenu{

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    @TableField("Name")
    private String text;

    @TableField("ParentId")
    private Integer ParentId;

    @TableField("Code")
    private String Code;

    @TableField("LinkAddress")
    private String LinkAddress;

    @TableField("Icon")
    private String Icon;

    @TableField("Sort")
    private Integer Sort;

    @TableField("CreateTime")
    private Date CreateTime;

    @TableField("CreateBy")
    private String CreateBy;

    @TableField("UpdateTime")
    private Date UpdateTime;

    @TableField("UpdateBy")
    private String UpdateBy;
    @TableField(exist = false)
    private List<TbMenu> nodes;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getParentId() {
        return ParentId;
    }

    public void setParentId(Integer parentId) {
        ParentId = parentId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getLinkAddress() {
        return LinkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        LinkAddress = linkAddress;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public Integer getSort() {
        return Sort;
    }

    public void setSort(Integer sort) {
        Sort = sort;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public List<TbMenu> getNodes() {
        return nodes;
    }

    public void setNodes(List<TbMenu> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbMenu tbMenu = (TbMenu) o;
        return Objects.equals(Id, tbMenu.Id) &&
                Objects.equals(text, tbMenu.text) &&
                Objects.equals(ParentId, tbMenu.ParentId) &&
                Objects.equals(Code, tbMenu.Code) &&
                Objects.equals(LinkAddress, tbMenu.LinkAddress) &&
                Objects.equals(Icon, tbMenu.Icon) &&
                Objects.equals(Sort, tbMenu.Sort) &&
                Objects.equals(CreateTime, tbMenu.CreateTime) &&
                Objects.equals(CreateBy, tbMenu.CreateBy) &&
                Objects.equals(UpdateTime, tbMenu.UpdateTime) &&
                Objects.equals(UpdateBy, tbMenu.UpdateBy) &&
                Objects.equals(nodes, tbMenu.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, text, ParentId, Code, LinkAddress, Icon, Sort, CreateTime, CreateBy, UpdateTime, UpdateBy, nodes);
    }
}
