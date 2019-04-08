package com.qujie.mintwo.ustils;

import java.util.List;
import java.util.Map;

public class Menudepartment {

    private int id;
    private String text;
    private int departmentOrder;
    private Integer departmentFather;
    private Integer departmentLevel;
    private Map<String, Object> state;


    // 子菜单
    private List<Menudepartment> nodes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDepartmentOrder() {
        return departmentOrder;
    }

    public void setDepartmentOrder(int departmentOrder) {
        this.departmentOrder = departmentOrder;
    }

    public Integer getDepartmentFather() {
        return departmentFather;
    }

    public void setDepartmentFather(Integer departmentFather) {
        this.departmentFather = departmentFather;
    }

    public Integer getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(Integer departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    public List<Menudepartment> getNodes() {
        return nodes;
    }

    public void setNodes(List<Menudepartment> nodes) {
        this.nodes = nodes;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }
}
