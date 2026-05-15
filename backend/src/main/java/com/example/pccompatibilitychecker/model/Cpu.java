package com.example.pccompatibilitychecker.model;

public class Cpu {
    private Long id;
    private String name;
    private String socket;
    private int tdpWatts;

    public Cpu() {
    }

    public Cpu(Long id, String name, String socket, int tdpWatts) {
        this.id = id;
        this.name = name;
        this.socket = socket;
        this.tdpWatts = tdpWatts;
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

    public int getTdpWatts() {
        return tdpWatts;
    }

    public void setTdpWatts(int tdpWatts) {
        this.tdpWatts = tdpWatts;
    }
}
