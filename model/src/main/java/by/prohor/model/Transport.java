package by.prohor.model;

import by.prohor.model.type.FuelType;
import by.prohor.model.type.TypeTransport;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */

public class Transport {

    private Integer transportId;
    private TypeTransport type;
    private FuelType fuelType;
    private Integer capacity;
    private LocalDate dateOfManufacture;
    private Integer numberRoute;


    public Transport(TypeTransport type, FuelType fuelType, Integer capacity, LocalDate dateOfManufacture, Integer routeId) {
        this.type = type;
        this.numberRoute = routeId;
        this.fuelType = fuelType;
        this.capacity = capacity;
        this.dateOfManufacture = dateOfManufacture;
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public TypeTransport getType() {
        return type;
    }

    public void setType(TypeTransport type) {
        this.type = type;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDate getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(LocalDate dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public Integer getRouteId() {
        return numberRoute;
    }

    public void setRouteId(Integer routeId) {
        this.numberRoute = routeId;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "transportId=" + transportId +
                ", type=" + type +
                ", fuelType=" + fuelType +
                ", capacity=" + capacity +
                ", dateOfManufacture=" + dateOfManufacture +
                ", routeId=" + numberRoute +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transport)) return false;
        Transport transport = (Transport) o;
        return Objects.equals(getTransportId(), transport.getTransportId()) &&
                type == transport.type &&
                getFuelType() == transport.getFuelType() &&
                Objects.equals(getCapacity(), transport.getCapacity()) &&
                Objects.equals(getDateOfManufacture(), transport.getDateOfManufacture()) &&
                Objects.equals(numberRoute, transport.numberRoute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransportId(), type, getFuelType(), getCapacity(), getDateOfManufacture(), numberRoute);
    }
}
