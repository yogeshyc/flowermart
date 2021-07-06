package com.softhub.flowermart;

public class FetchedListOfFlower {

    private String flowerId;
    private String flowerName;

    public FetchedListOfFlower(String flowerId, String flowerName) {
        this.flowerId = flowerId;
        this.flowerName = flowerName;
    }

    public String getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(String flowerId) {
        this.flowerId = flowerId;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }
}
