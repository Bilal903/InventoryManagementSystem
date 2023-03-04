package com.example.inventory_manage_application.Model;



/**
 * In this code, a constructor for the OutSourced class is being defined
 * The constructor starts by calling the super method,
 which invokes the constructor of the superclass (the class from which OutSourced is derived).
 *This constructor allows you to create a new OutSourced object by specifying the required information as arguments.
 * And finally getter and setter are made
 */


public class OutSourced extends Parts {
    private String companyName;

    public OutSourced(int partID, int stock, int min, int max, String name, double partCost, String companyName) {
        super(partID, stock, min, max, name, partCost);

        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
