package com.example.inventory_manage_application.Model;

public class  InHouse extends Parts {
    private int machineID;
    /**
     *here a constructor is made to create instance of a class
     * a getter which returns machine ID
     * and setter to sets machine ID auto
     */
    public InHouse(int partID, int stock, int min, int max, String name, double partCost, int machineID) {
        super(partID, stock, min, max, name, partCost);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
