package org.reggiemcdonald.api.model.api;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AppUserApiModel {

    @NotEmpty
    private String username;

    @NotEmpty
    @Size(min = 8)
    private String password;

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
}
