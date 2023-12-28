package me.truec0der.mwhitelist.models;

public class UserModel {
    private final String nickname;

    public UserModel(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
