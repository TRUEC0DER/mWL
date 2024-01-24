package me.truec0der.mwhitelist.models;

public class UserModel {
    private final String nickname;
    private long time;

    public UserModel(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
