package com.softhub.flowermart;

public class FetchedListOfSales {

    private String custId;
    private String custName;
    private String custMobile;
    private String marketName;
    private String flowersaleName;
    private String vehicleNo;
    private String flowersaleWeight;
    private String flowersaleRate;
    private String totalsaleAmount;

    public FetchedListOfSales(String custId, String custName, String custMobile, String marketName, String flowersaleName, String vehicleNo, String flowersaleWeight, String flowersaleRate, String totalsaleAmount) {
        this.custId = custId;
        this.custName = custName;
        this.custMobile = custMobile;
        this.marketName = marketName;
        this.flowersaleName = flowersaleName;
        this.vehicleNo = vehicleNo;
        this.flowersaleWeight = flowersaleWeight;
        this.flowersaleRate = flowersaleRate;
        this.totalsaleAmount = totalsaleAmount;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustMobile() {
        return custMobile;
    }

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getFlowersaleName() {
        return flowersaleName;
    }

    public void setFlowersaleName(String flowersaleName) {
        this.flowersaleName = flowersaleName;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getFlowersaleWeight() {
        return flowersaleWeight;
    }

    public void setFlowersaleWeight(String flowersaleWeight) {
        this.flowersaleWeight = flowersaleWeight;
    }

    public String getFlowersaleRate() {
        return flowersaleRate;
    }

    public void setFlowersaleRate(String flowersaleRate) {
        this.flowersaleRate = flowersaleRate;
    }

    public String getTotalsaleAmount() {
        return totalsaleAmount;
    }

    public void setTotalsaleAmount(String totalsaleAmount) {
        this.totalsaleAmount = totalsaleAmount;
    }
}
