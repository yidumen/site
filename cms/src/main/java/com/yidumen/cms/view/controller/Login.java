package com.yidumen.cms.view.controller;

import com.yidumen.dao.entity.CmsUser;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Named("login")
@SessionScoped
public class Login implements Serializable {

    private CmsUser user;
    public Login() {
        user = new CmsUser();
    }

    public String login() {
        if (user.getUsername().equals("admin") && user.getPassword().equals("admin")) {
            return "framework?faces-redirect=true";
        } else {
            return null;
        }
    }

    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }
}
