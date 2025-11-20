package org.parking.parkinglot.common;

public class CarDto {
    Long id;
    String licensePlate;
    String parkingLot;
    String ownerName;

    public CarDto(Long id, String licensePlate, String parkingLot, String ownerName) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.parkingLot = parkingLot;
        this.ownerName = ownerName;
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
