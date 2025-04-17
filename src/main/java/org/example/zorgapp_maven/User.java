package org.example.zorgapp_maven;

class User {
    String userName;
    int userID;
    Role role;

    public User(int id, String name, Role role) {
        this.userID = id;
        this.userName = name;
        this.role = role;
    }

    String getUserName() {
        return userName;
    }

    String getRole(){
        return role.name;
    }

    int getUserID() {
        return userID;
    }
}
