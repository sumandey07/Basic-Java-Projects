package com.dataflair.onlineattendancesystem.Model;

public class Model {

    String id,mail,name,profilepic,role,className,attendance,totalDays;

    public Model() {
    }

    public Model(String id, String mail, String name, String profilepic, String role, String className, String attendance, String totalDays) {
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.profilepic = profilepic;
        this.role = role;
        this.className = className;
        this.attendance = attendance;
        this.totalDays = totalDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(String totalDays) {
        this.totalDays = totalDays;
    }
}

