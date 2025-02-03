/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sample.models.I_List;
import sample.models.Mountain;
import sample.models.Statistics;
import sample.models.Student;
import sample.models.StudentMountain;
import sample.utils.Utils;

/**
 *
 * @author hoadoan
 */
public class StudentMountainList extends ArrayList<StudentMountain> implements I_List {

    @Override
    public boolean create() {
        boolean result = false;
        try {
            boolean checkDuplicate = true;
            Student student = new Student();
            do {

//            nhap id: hai ký tự đầu là mã campus, 6 ký tự sau là số
                boolean countinous = true;
                do {
                    String id = Utils.getString("Input Student ID: ");
                    if (Utils.isValidateID(id)) {
                        student.setId(id.toUpperCase());
                        countinous = false;
                    }
                } while (countinous);
//            Nhap name từ 2 đến 20 ký tự
                countinous = true;
                do {
                    String name = Utils.getString("Input Student Name: ");
                    if (name.length() > 1 && name.length() < 21) {
                        student.setName(name);
                        countinous = false;
                    }
                } while (countinous);
//            nhap so dien thoai: 10 so
                countinous = true;
                do {
                    String phone = Utils.getString("Input Student phone: ");
                    if (Utils.isValidatePhone(phone)) {
                        student.setPhone(phone);
                        countinous = false;
                    }
                } while (countinous);
//            Nhập email
                countinous = true;
                do {
                    String email = Utils.getString("Input Student êmail: ");
                    if (Utils.isValidateEmail(email)) {
                        student.setEmail(email);
                        countinous = false;
                    }
                } while (countinous);
                if (this.indexOf(new StudentMountain(student)) == -1) {
                    checkDuplicate = false;
                } else {
                    System.out.println("Duplicate Student roi !");
                }
            } while (checkDuplicate);
//            tới đây là đã nhập xong sinh viên( sinh viên chưa đăng ký). 
//            load thông tin núi từ file 
            ArrayList<Object> listMountain = Utils.readListOjectFromFile("mountainList.bin");
            boolean countinous = true;
            String mountainCode = "";
            do {
                mountainCode = Utils.getString("Input mountain code:");
                if (listMountain.indexOf(new Mountain(mountainCode)) != -1) {
                    countinous = false;
                }
            } while (countinous);
            double fee = Utils.BASE_FEE;
            String first3Character = student.getPhone().substring(0, 3);
            if (first3Character.equals("098") || first3Character.equals("097")
                    || first3Character.equals("090") || first3Character.equals("091")) {
                fee = Utils.BASE_FEE * Utils.DISCOUNT_FEE / 100;
            }
            this.add(new StudentMountain(student, mountainCode, fee));
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean saveToFile(String path) {
        boolean check = false;
        try {
            Utils.writeListObjectToFile(path, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    @Override
    public boolean update(String id) {
        boolean result = false;
        int index = -1;
        String name, phone, email;
        Student student = new Student();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getStudent().getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("This student has not registered yet!");
        } else {
            //            Nhap name từ 2 đến 20 ký tự
            boolean countinous = true;
            do {
                name = Utils.getString("Input Student Name: ");
                if (name.length() > 1 && name.length() < 21) {
                    student.setName(name);
                    countinous = false;
                }
            } while (countinous);
//            nhap so dien thoai: 10 so
            countinous = true;
            do {
                phone = Utils.getString("Input Student phone: ");
                if (Utils.isValidatePhone(phone)) {
                    student.setPhone(phone);
                    countinous = false;
                }
            } while (countinous);
//            Nhập email
            countinous = true;
            do {
                email = Utils.getString("Input Student êmail: ");
                if (Utils.isValidateEmail(email)) {
                    student.setEmail(email);
                    countinous = false;
                }
            } while (countinous);

            double fee = Utils.BASE_FEE;
            String first3Character = student.getPhone().substring(0, 3);
            if (first3Character.equals("098") || first3Character.equals("097")
                    || first3Character.equals("090") || first3Character.equals("091")) {
                fee = Utils.BASE_FEE * Utils.DISCOUNT_FEE / 100;
            }

            student = new Student(id, name, phone, email);
            this.get(index).setStudent(student);
            this.get(index).setFee(fee);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getStudent().getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("This student has not registered yet!");
        } else {
            this.get(index).displayDelete();
            boolean confirmDelete = Utils.confirmYesNo("Are you sure you want to delete this registration? (Y/N):");
            if (confirmDelete) {
                this.remove(index);
            }
        }
        return result;
    }

    @Override
    public List<Object> search(String name) {
        List<Object> searchList = new ArrayList<>();

        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getStudent().getName().equalsIgnoreCase(name)) {
                searchList.add(this.get(i));
            }
        }

        return searchList;
    }

    @Override
    public void displayList(List<Object> list, String type) {
        if (list.size() == 0) {
            if (type.equalsIgnoreCase("display")) {
                System.out.println("No students have registered yet.");
            } else {
                System.out.println("No one matches the search criteria.");
            }
        } else {
            for (int i = 0; i < 71; i++) {
                System.out.print("-");
            }
            System.out.println("");
            System.out.println("Student ID   | Name              | Phone      | Peak Code | Fee");
            for (int i = 0; i < 71; i++) {
                System.out.print("-");
            }
            System.out.println("");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(((StudentMountain) list.get(i)).toString());
            }
        }
        for (int i = 0; i < 71; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    @Override
    public List<Object> filter(String name) {
        List<Object> filterList = new ArrayList<>();

        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getStudent().filterCampus(name) == true) {
                filterList.add(this.get(i));
            }
        }

        return filterList;
    }

    @Override
    public List<Object> statistic() {
        List<Object> list = new ArrayList<>();
        ArrayList<Object> listMountain = new ArrayList<>();
        try {
            listMountain = Utils.readListOjectFromFile("mountainList.bin");
        } catch (IOException ex) {
            Logger.getLogger(StudentMountainList.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < listMountain.size(); i++) {
            int participants = 0;
            double cost = 0;
            for (int j = 0; j < this.size(); j++) {
                String tmp = listMountain.get(i).toString();
                if (tmp.length() < 2) {
                    tmp = "0" + tmp;
                }
                tmp = "MT" + tmp;
                if (this.get(j).getMountainCode().equals(tmp)) {
                    participants++;
                    cost += this.get(j).getFee();
                }
            }
            if (participants != 0) {
                String tmp = (((Mountain)listMountain.get(i)).getCode());
                if(tmp.length() < 2) {
                    tmp = "0" + tmp;
                }
                tmp = "MT" + tmp;
                list.add(new Statistics(tmp, participants, cost));
            }

        }
        return list;
    }

    public void displayStatistics(List<Object> list) {
        if (list.size() == 0) {
            System.out.println("No one matches the search criteria!");
        } else {
            for (int i = 0; i < 66; i++) {
                System.out.print("-");
            }
            System.out.println("");
            System.out.println("Peak Code   | Number of participants       | Total cost");
            for (int i = 0; i < 66; i++) {
                System.out.print("-");
            }
            System.out.println("");
            for (int i = 0; i < list.size(); i++) {
                System.out.println(((Statistics) list.get(i)).toString());
            }
        }
        for (int i = 0; i < 66; i++) {
            System.out.print("-");
        }
        System.out.println("");
    }

    public void loadData() {
        try {
            List<Object> list = Utils.readListOjectFromFile("studentMountainList.bin");
            for (int i = 0; i < list.size(); i++) {
                StudentMountain tmp = (StudentMountain) list.get(i);
                tmp.setMountainCode(tmp.getMountainCode());
                this.add(tmp);
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentMountainList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
