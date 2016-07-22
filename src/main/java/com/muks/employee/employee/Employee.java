package com.muks.employee.employee;

/**
 * Created by 15692 on 04/07/16.
 * -
 */
public class Employee {
    private String name;
    private int age;
    private String city;


    public void setName(String fName) { this.name = fName; }
    public void setAge(int age) { this.age = age; }
    public void setCity(String city) { this.city = city; }

    public String getName() {
        return name;
    }
    public int getAge() { return age; }
    public String getCity() { return city; }

}
