package com.softhub.flowermart;

public class FetchedListOfVendor {

    private String vendorId;
    private String vendorName;
    private String added_name;
    private String vendorMobile;
    private String vendorAddress;
    private String flowerName;
    private String flowerWeight;
    private String flowerRate;
    private String totalAmount;
    private String paidAmount;
    private String reAmount;

    public FetchedListOfVendor(String vendorId, String vendorName,String added_name, String vendorMobile, String vendorAddress, String flowerName, String flowerWeight, String flowerRate, String totalAmount, String paidAmount, String reAmount) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.added_name = added_name;
        this.vendorMobile = vendorMobile;
        this.vendorAddress = vendorAddress;
        this.flowerName = flowerName;
        this.flowerWeight = flowerWeight;
        this.flowerRate = flowerRate;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.reAmount = reAmount;
    }

    public FetchedListOfVendor() {

    }
/* public FetchedListOfVendor(String vendorId, String vendorName, String vendorMobile) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorMobile = vendorMobile;
    }*/

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorMobile() {
        return vendorMobile;
    }

    public void setVendorMobile(String vendorMobile) {
        this.vendorMobile = vendorMobile;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getFlowerWeight() {
        return flowerWeight;
    }

    public void setFlowerWeight(String flowerWeight) {
        this.flowerWeight = flowerWeight;
    }

    public String getFlowerRate() {
        return flowerRate;
    }

    public void setFlowerRate(String flowerRate) {
        this.flowerRate = flowerRate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getReAmount() {
        return reAmount;
    }

    public void setReAmount(String reAmount) {
        this.reAmount = reAmount;
    }

    public String getAdded_name() {
        return added_name;
    }

    public void setAdded_name(String added_name) {
        this.added_name = added_name;
    }
}
