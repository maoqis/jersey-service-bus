package com.maoqis.bean;

public class BusSResp {
    int station;
    float distance;
    int mis;

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getMis() {
        return mis;
    }

    public void setMis(int mis) {
        this.mis = mis;
    }

    @Override
    public String toString() {
        return "BusSResp{" +
                "station=" + station +
                ", distance=" + distance +
                ", mis=" + mis +
                '}';
    }
}
