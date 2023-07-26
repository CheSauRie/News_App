package com.example.myapplication.Models;

import java.io.Serializable;
import java.util.List;

public class LoteryResult implements Serializable {
    private List<String> ĐB;
    private List<String> G1;
    private List<String> G2;
    private List<String> G3;
    private List<String> G4;
    private List<String> G5;
    private List<String> G6;
    private List<String> G7;

    public List<String> getDB() {
        return ĐB;
    }

    public void setDB(List<String> DB) {
        this.ĐB = DB;
    }

    public List<String> getG1() {
        return G1;
    }

    public void setG1(List<String> g1) {
        G1 = g1;
    }

    public List<String> getG2() {
        return G2;
    }

    public void setG2(List<String> g2) {
        G2 = g2;
    }

    public List<String> getG3() {
        return G3;
    }

    public void setG3(List<String> g3) {
        G3 = g3;
    }

    public List<String> getG4() {
        return G4;
    }

    public void setG4(List<String> g4) {
        G4 = g4;
    }

    public List<String> getG5() {
        return G5;
    }

    public void setG5(List<String> g5) {
        G5 = g5;
    }

    public List<String> getG6() {
        return G6;
    }

    public void setG6(List<String> g6) {
        G6 = g6;
    }

    public List<String> getG7() {
        return G7;
    }

    public void setG7(List<String> g7) {
        G7 = g7;
    }
}
