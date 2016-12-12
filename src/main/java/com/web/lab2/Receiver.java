package com.web.lab2;

import java.awt.Point;
import java.util.Date;
import java.util.Random;

public class Receiver {
    private Point coordinates;
    private String serial;
    private int temperature;
    private boolean summerTime;
    private Date receivedAt;

    public Receiver(String serial, Point coordinates, boolean summerTime) {
        this.serial = serial;
        this.summerTime = summerTime;
        this.coordinates = coordinates;
        this.receivedAt = new Date();
    }

    public void askTemperature() throws InterruptedException {
        new Thread(() -> {
            synchronized(this) {
                try {
                    Thread.sleep(new Random().nextInt(1000));
                    temperature = fetchTemperature();
                    receivedAt = new Date();
                    notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public int getTemperature() { return temperature; }

    public Point getCoordinates() {
        return coordinates;
    }

    public String getSerial() {
        return serial;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public boolean isSummerTime() {
        return summerTime;
    }

    private int fetchTemperature() {
        if (summerTime) {
            return  5 + new Random().nextInt(35);
        } else {
            return  -20 + new Random().nextInt(25);
        }
    }
}
