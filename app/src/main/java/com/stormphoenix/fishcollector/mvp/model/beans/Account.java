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
}
