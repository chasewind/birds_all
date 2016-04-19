package com.bird;

import java.util.Date;

public class Person {

    private String id;
    private String name;
    private Date   birthDay;
    
    private SexEnum sex;

    public static enum SexEnum {
                            male, female, other
    }

    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public Date getBirthDay() {
        return birthDay;
    }

    
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    
    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }
    /////////////

    
    
    //
    
    
    
    
    
    
    
    
    
    
}
