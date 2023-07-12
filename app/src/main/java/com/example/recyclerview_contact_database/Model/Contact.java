package com.example.recyclerview_contact_database.Model;

public class Contact {
   private int id;
    private String name;
    private String number;
    private String imgPath;

    public Contact(int id, String name, String number, String imgPath) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.imgPath = imgPath;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getImgPath() {

        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
