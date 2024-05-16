package com.example.oop_project;

public class MasinaErind extends Exception{
    private final double raha;

    public MasinaErind(String message, double raha) {
        super(message);
        this.raha = raha;
    }

    public double getRaha() {
        return raha;
    }
}
