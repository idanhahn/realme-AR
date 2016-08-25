package com.realme_demo.realmeapp.activities.rm_stylify;

import com.realme_demo.realmeapp.vu.models.MeshObject;

/**
 * Created by idanhahn on 8/19/2016.
 */
public class StylifyItem {

    String model;
    String title;
    String designer;
    String rating;
    String price;
    MeshObject modelObj;
    boolean acquired;

    public StylifyItem(String model, String title, String designer, String rating, String price, MeshObject modelObj, boolean acquired) {
        this.model = model;
        this.title = title;
        this.designer = designer;
        this.rating = rating;
        this.price = price;
        this.modelObj = modelObj;
        this.acquired = acquired;
    }

}
