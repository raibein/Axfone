package com.raben.axfone.POJO;

/**
 * Created by raben on 19-Oct-17.
 */
public class Customer {
    String name, id, langId, slots, slotsUsed, parkPlaceId, parkPlaceName, tariffId;

    public Customer(String id, String name, String langId, String slots, String slotsUsed, String parkPlaceId, String parkPlaceName, String tariffId) {
        this.id = id;
        this.name = name;
        this.langId = langId;
        this.slots = slots;
        this.slotsUsed = slotsUsed;
        this.parkPlaceId = parkPlaceId;
        this.parkPlaceName = parkPlaceName;
        this.tariffId = tariffId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParkPlaceId() {
        return parkPlaceId;
    }

    public void setParkPlaceId(String parkPlaceId) {
        this.parkPlaceId = parkPlaceId;
    }


    public String getParkPlaceName() {
        return parkPlaceName;
    }

    public void setParkPlaceName(String parkPlaceName) {
        this.parkPlaceName = parkPlaceName;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getSlotsUsed() {
        return slotsUsed;
    }

    public void setSlotsUsed(String slotsUsed) {
        this.slotsUsed = slotsUsed;
    }

    public String getTariffId() {
        return tariffId;
    }

    public void setTariffId(String tariffId) {
        this.tariffId = tariffId;
    }
}
