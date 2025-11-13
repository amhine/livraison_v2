package com.livraison.ai;

import java.util.List;

public class OptimizedTour {
    private List<Long> deliveryOrder;
    private double totalDistance;
    private double totalTime;
    private double totalCost;
    private List<Double> distances;
    private List<Double> times;

    // Getters and setters
    public List<Long> getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(List<Long> deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<Double> getDistances() {
        return distances;
    }

    public void setDistances(List<Double> distances) {
        this.distances = distances;
    }

    public List<Double> getTimes() {
        return times;
    }

    public void setTimes(List<Double> times) {
        this.times = times;
    }
}
