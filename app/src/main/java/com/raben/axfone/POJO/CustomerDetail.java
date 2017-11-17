package com.raben.axfone.POJO;

/**
 * Created by raben on 20-Oct-17.
 */
public class CustomerDetail {

    private String name, id, time;

    public  CustomerDetail(String name, String id, String time){
        this.name = name;
        this.id = id;
        this.time = time;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
