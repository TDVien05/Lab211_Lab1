/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.models;

import java.io.Serializable;

/**
 *
 * @author hoadoan
 */
public class Student implements Serializable{
    private String id;
    private String name;
    private String phone;
    private String email;

    public Student() {
    }

    public Student(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        Student st = (Student) obj;
        return this.getId().equals(st.getId());
    }

    public boolean filterCampus(String campus) {
        return campus.equalsIgnoreCase(this.getId().substring(0, 2));
    }
    
    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + '}';
    }
    
    
}
