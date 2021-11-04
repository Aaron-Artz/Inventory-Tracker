package InventorySystem.Model;

/*
 *
 * Aaron Artz
 * May 1, 2020
 * WGU C482 Final
 *
 */


public class OutSourced extends Part {

        private String companyName;


    public OutSourced(int partID, String name, double price, int inStock, int min, int max, String companyName) {
        super(partID, name, price, inStock, min, max);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
