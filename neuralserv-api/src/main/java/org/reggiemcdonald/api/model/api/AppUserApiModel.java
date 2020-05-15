package org.reggiemcdonald.api.model.api;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
public class AppUserApiModel {

    public AppUserApiModel(String _username, String _password) {
        username = _username;
        password = _password;
    }

    @NotEmpty(message="username is a required field")
    private String username;

    @NotEmpty(message="password is a required field")
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
