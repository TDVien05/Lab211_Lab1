package sample.views;

import java.util.List;
import sample.controllers.Menu;
import sample.controllers.StudentMountainList;
import sample.models.I_List;
import sample.models.I_Menu;
import sample.utils.Utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hoa Doan
 */
public class MountainHikingManagement {

    public static void main(String args[]) {
        I_Menu menu = new Menu();
        menu.addItem("1. New registration");
        menu.addItem("2. Update registration information");
        menu.addItem("3. Display registered list");
        menu.addItem("4. Delete registration information");
        menu.addItem("5. Search paticipants by name");
        menu.addItem("6. Filter data by campus");
        menu.addItem("7. Statistics of registration number by location");
        menu.addItem("8. Save data to registration file");
        menu.addItem("9: Quit");
        int choice;
        boolean cont = false;
        String id;
        I_List list = new StudentMountainList();
        ((StudentMountainList)list).loadData();
        do {
            menu.showMenu();
            choice = menu.getChoice();            
            switch (choice) {
                case 1:
                    Utils.displayStatus(list.create(), "Add new registration successfully.", "Add registration was failed.");
                    break;
                case 2:
                    id = Utils.getString("Input student id to update : ");
                    Utils.displayStatus(list.update(id.toUpperCase()), "Update registration successfully.", "This student has not registered yet.");
                    break;
                case 3:
                    ((StudentMountainList)list).displayList((List<Object>) list, "display");
                    break;
                case 4:
                    id = Utils.getString("Input student id to delete : ");
                    Utils.displayStatus(list.delete(id.toUpperCase()), "Delete registration successfully.", "no students have registered yet.");
                    break;
                case 5:
                    String name = Utils.getString("Input participant's name to search : ");
                    List<Object> searchedList = list.search(name);
                    ((StudentMountainList)list).displayList(searchedList, "search");
                    break;
                case 6:
                    String campus = Utils.getString("Input campus [SE/ DE/ HE/ CE/ QE] : ");
                    List<Object> filterList = list.filter(campus.toUpperCase());
                    ((StudentMountainList)list).displayList(filterList, "filter");
                    break;
                case 7:
                    List<Object> statisticsList = list.statistic();
                    ((StudentMountainList)list).displayStatistics(statisticsList);
                    break;
                case 8:
                    Utils.displayStatus(list.saveToFile("studentMountainList.bin"), "Save data successfully", "Save data was failured");
                    break;
                case 9:
                    cont = menu.confirmYesNo("Do you want to quit?(Y/N)");
                    break;
            }
        } while (choice >= 0 && choice <= 9 && cont == false);
    }
}
