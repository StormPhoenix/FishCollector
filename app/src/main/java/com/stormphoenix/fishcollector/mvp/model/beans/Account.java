package com.stormphoenix.fishcollector.mvp.model.beans;

/**
 * Created by Developer on 16-12-28.
 * Wang Cheng is a intelligent Android developer.
 */

public class Account {
    private String username;
    private String password;
    private String telephone;
    private String email;
    // 超级管理员、课题组、指导老师、学生
    private String description;
    // 是否激活
    private String active;
    // 权限名称
    private String superiorName;
    // 角色名称
    private String roleName;
    // 是否被分配了任务
    private boolean isDespatched;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSuperiorName() {
        return superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isDespatched() {
        return isDespatched;
    }

    public void setDespatched(boolean despatched) {
        isDespatched = despatched;
    }
}
