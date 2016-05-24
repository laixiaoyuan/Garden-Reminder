package edu.scu.lwang.finalprojectscene;

/**
 * Created by Sarah on 5/23/2016.
 */

public class Plant {
    protected int id;
    protected String plantName;
    protected String photoPath;
    protected String waterInterval;
    protected String lastWater;
    protected String date;

    public Plant(int id, String plantName, String photoPath, String date, String waterInterval, String lastWater) {
        this.id = id;
        this.plantName = plantName;
        this.photoPath = photoPath;
        this.waterInterval = waterInterval;
        this.lastWater = lastWater;
        this.date = date;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getWaterInterval() {
        return waterInterval;
    }

    public void setWaterInterval(String waterInterval) {
        this.waterInterval = waterInterval;
    }

    public String getLastWater() {
        return lastWater;
    }

    public void setLastWater(String lastWater) {
        this.lastWater = lastWater;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
