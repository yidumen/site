package com.yidumen.cms.view.controller;

import com.yidumen.dao.entity.CmsUser;
import org.springframework.stereotype.Controller;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Controller
public class Login {

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
