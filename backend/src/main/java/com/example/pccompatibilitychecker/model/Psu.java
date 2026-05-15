package com.example.pccompatibilitychecker.model;

public class Psu {
    private Long id;
    private String name;
    private int wattageWatts;

    public Psu() {
    }

    public Psu(Long id, String name, int wattageWatts) {
        this.id = id;
        this.name = name;
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

    public int getWattageWatts() {
        return wattageWatts;
    }

    public void setWattageWatts(int wattageWatts) {
        this.wattageWatts = wattageWatts;
    }
}
