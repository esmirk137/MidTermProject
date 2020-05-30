package com.company;

import java.io.Serializable;

/**
 * This class
 */
public class Request implements Serializable {
    private String type;
    private String name;
    private String address;

    public Request(String type, String name, String address){
        this.type=type;
        this.name=name;
        this.address=address;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }
}
