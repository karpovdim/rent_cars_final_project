package by.karpov.rent_cars_final_project.entity;


import java.math.BigDecimal;

public class Car implements BaseEntity {

    public enum CarStatus {
        BOOKED, FREE, CAR_IS_SERVICED, IMPOSSIBLE_TO_RENT
    }

    public enum TransmissionType {
        MANUAL, AUTOMATIC, CONTINUOUSLY_VARIABLE, SEMI_AUTOMATIC
    }


    private Long id;
    private String registrationNumber;
    private Boolean conditioner;
    private BigDecimal cost;
    private String imageUrl;
    private CarStatus carStatus;
    private TransmissionType transmissionType;
    private String carDescription;

    public Car(Long id, String registrationNumber, Boolean conditioner, BigDecimal cost, String imageUrl, CarStatus carStatus, TransmissionType transmissionType, String carDescription) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.conditioner = conditioner;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.carStatus = carStatus;
        this.transmissionType = transmissionType;
        this.carDescription = carDescription;
    }

    public Car() {
    }

    public Car(Long id) {
        this.id = id;
    }

    public static CarBuilder builder() {
        return new CarBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public Boolean isConditioner() {
        return this.conditioner;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public CarStatus getCarStatus() {
        return this.carStatus;
    }

    public String getCarDescription() {
        return this.carDescription;
    }


    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setConditioner(boolean conditioner) {
        this.conditioner = conditioner;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public String toString() {
        return "Car(id=" + this.getId() + ", registrationNumber=" + this.getRegistrationNumber() + ", conditioner=" + this.conditioner + ", cost=" + this.getCost() + ", imageUrl=" + this.getImageUrl() + ", carStatus=" + this.getCarStatus() + ", transmissionType=" + this.getTransmissionType() + ", carDescription=" + this.getCarDescription()  + ")";
    }

    public static class CarBuilder {

        private Long id;
        private String registrationNumber;
        private Boolean conditioner;
        private BigDecimal cost;
        private String imageUrl;
        private CarStatus carStatus;
        private TransmissionType transmissionType;
        private String descriptionCar;

        CarBuilder() {
        }

        public CarBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CarBuilder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public CarBuilder conditioner(Boolean conditioner) {
            this.conditioner = conditioner;
            return this;
        }

        public CarBuilder cost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public CarBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public CarBuilder carStatus(CarStatus carStatus) {
            this.carStatus = carStatus;
            return this;
        }

        public CarBuilder transmissionType(TransmissionType transmissionType) {
            this.transmissionType = transmissionType;
            return this;
        }

        public CarBuilder descriptionCar(String descriptionCar) {
            this.descriptionCar = descriptionCar;
            return this;
        }

        public Car build() {
            return new Car(id, registrationNumber, conditioner, cost, imageUrl, carStatus, transmissionType, descriptionCar);
        }

        public String toString() {
            return "Car.CarBuilder(id=" + this.id + ", registrationNumber=" + this.registrationNumber + ", conditioner=" + this.conditioner + ", cost=" + this.cost + ", imageUrl=" + this.imageUrl + ", carStatus=" + this.carStatus + ", transmissionType=" + this.transmissionType + ", descriptionCar=" + this.descriptionCar + ", insurances=" +  ")";
        }
    }
}
