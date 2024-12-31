package moe.yushi.authlibinjector.login;

import java.util.function.Function;

public class LoginArgumentsInjector implements Function<String[], String[]> {
    @Override
    public String[] apply(String[] args) {
        new LoginDialog();
        return args;
    }
}
