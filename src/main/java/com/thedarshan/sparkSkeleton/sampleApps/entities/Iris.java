/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thedarshan.sparkSkeleton.sampleApps.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author Darshan
 */
@DatabaseTable(tableName = "iris")
public class Iris {


    @DatabaseField(id = true) public int id;
    @DatabaseField     public Float sepalLength;
    @DatabaseField     public Float sepalWidth;
    @DatabaseField     public Float petalLength;
    @DatabaseField     public Float petalWidth;
    @DatabaseField     public String species;

    public Iris(Float sepalLength, Float sepalWidth, Float petalLength, Float petalWidth, String species) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.species = species;
    }

    public Iris() {
    }
    

    public int getId() {
        return id;
    }

    public Float getSepalLength() {
        return sepalLength;
    }

    public Float getSepalWidth() {
        return sepalWidth;
    }

    public Float getPetalLength() {
        return petalLength;
    }

    public Float getPetalWidth() {
        return petalWidth;
    }

    public String getSpecies() {
        return species;
    }    
}
