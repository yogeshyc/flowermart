package com.softhub.flowermart;

public class FetchedListOfDriver {

    private String driverId;
    private String driverName;
    private String driverMobile;
    private String driverPassword;
    private String driverStatus;

    public FetchedListOfDriver(String driverId, String driverName, String driverMobile, String driverPassword, String driverStatus) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverMobile = driverMobile;
        this.driverPassword = driverPassword;
        this.driverStatus = driverStatus;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getDriverPassword() {
        return driverPassword;
    }

    public void setDriverPassword(String driverPassword) {
        this.driverPassword = driverPassword;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }
}
