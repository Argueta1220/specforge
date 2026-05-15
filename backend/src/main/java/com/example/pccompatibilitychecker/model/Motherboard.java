package com.example.pccompatibilitychecker.model;

public class Motherboard {
    private Long id;
    private String name;
    private String socket;
    private String ramType;
    private int wattageWatts;

    public Motherboard() {
    }

    public Motherboard(Long id, String name, String socket, String ramType, int wattageWatts) {
        this.id = id;
        this.name = name;
        this.socket = socket;
        this.ramType = ramType;
        this.wattageWatts = wattageWatts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getRamType() {
        return ramType;
    }

    public void setRamType(String ramType) {
        this.ramType = ramType;
    }

    public int getWattageWatts() {
        return wattageWatts;
    }

    public void setWattageWatts(int wattageWatts) {
        this.wattageWatts = wattageWatts;
    }
}
