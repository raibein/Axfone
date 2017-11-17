package com.raben.axfone.POJO;

/**
 * Created by raben on 30-Oct-17.
 */
public class ParkingID {
        String id, name;

        public ParkingID(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
