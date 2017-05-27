package com.stormphoenix.fishcollector.mvp.model.beans;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Developer on 16-12-28.
 * Wang Cheng is a intelligent Android developer.
 */

@Entity
public class Account {
    @NotNull
    @Expose
    private String username;
    @NotNull
    @Expose
    private String password;
    @Nullable
    @Expose
    private String telephone;
    @Nullable
    @Expose
    private String email;
    // 超级管理员、课题组、指导老师、学生
    @Nullable
    @Expose
    private String description;
    // 是否激活
    @NotNull
    @Expose
    private String active;
    // 权限名称
    @NotNull
    @Expose
    private String superiorName;
    // 角色名称
    private String roleName;
    // 是否被分配了任务
    @NotNull
    @Expose
    private boolean isDespatched;

    /**
     * 以下是针对 greendao做出的修改
     **/
    @Id
    private Long id;

    @Generated(hash = 105013457)
    public Account(@NotNull String username, @NotNull String password,
            String telephone, String email, String description,
            @NotNull String active, @NotNull String superiorName, String roleName,
            boolean isDespatched, Long id) {
        this.username = username;
        this.password = password;
        this.telephone = telephone;
        this.email = email;
        this.description = description;
        this.active = active;
        this.superiorName = superiorName;
        this.roleName = roleName;
        this.isDespatched = isDespatched;
        this.id = id;
    }

    @Generated(hash = 882125521)
    public Account() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActive() {
        return this.active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSuperiorName() {
        return this.superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean getIsDespatched() {
        return this.isDespatched;
    }

    public void setIsDespatched(boolean isDespatched) {
        this.isDespatched = isDespatched;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
