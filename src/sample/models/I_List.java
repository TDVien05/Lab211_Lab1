/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.models;

import java.util.List;

/**
 *
 * @author hoadoan
 */
public interface I_List {
    boolean create();
    boolean update(String id);
    boolean delete(String id);
    void displayList(List<Object> list, String type);
    List<Object> search(String name);
    List<Object> filter(String name);
    List<Object> statistic();
    boolean saveToFile(String path);
    
}
