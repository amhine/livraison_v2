package com.livraison.ai;

import java.util.List;

public class OptimizationContext {
    private List<DeliveryPoint> deliveryPoints;
    private Vehicle vehicle;
    private Warehouse warehouse;
    private Constraints constraints;

    // Getters and setters
    public List<DeliveryPoint> getDeliveryPoints() {
        return deliveryPoints;
    }

    public void setDeliveryPoints(List<DeliveryPoint> deliveryPoints) {
        this.deliveryPoints = deliveryPoints;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Constraints getConstraints() {
        return constraints;
    }

    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }
}

class DeliveryPoint {
    private Long id;
    private double latitude;
    private double longitude;
    private double weight;
    private double volume;
    // Getters and setters
}

class Vehicle {
    private Long id;
    private double maxWeight;
    private double maxVolume;
    // Getters and setters
}

class Warehouse {
    private Long id;
    private double latitude;
    private double longitude;
    // Getters and setters
}

class Constraints {
    private int maxStops;
    private int maxDistance;
    // Getters and setters
}
