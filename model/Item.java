package model;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    private String name;
    private String phone;
    private String itemName;
    private String place;
    private Date date;
    private String description;

    public Item(String name, String phone, String itemName, String place, Date date, String description) {
        this.name = name;
        this.phone = phone;
        this.itemName = itemName;
        this.place = place;
        this.date = date;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getItemName() {
        return itemName;
    }

    public String getPlace() {
        return place;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }
}
