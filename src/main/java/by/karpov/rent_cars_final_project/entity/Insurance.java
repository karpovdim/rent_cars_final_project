package by.karpov.rent_cars_final_project.entity;

import java.time.LocalDateTime;

public class Insurance implements BaseEntity {
    private Long id;
    private Long identificationNumber;
    private LocalDateTime startInsurance;
    private LocalDateTime endInsurance;
    private Car car;
    private Boolean isActive;

    Insurance(Long id, Long identificationNumber, LocalDateTime startInsurance, LocalDateTime endInsurance, Car car) {
        this.id = id;
        this.identificationNumber = identificationNumber;
        this.startInsurance = startInsurance;
        this.endInsurance = endInsurance;
        this.car = car;
        this.isActive = LocalDateTime.now().isBefore(endInsurance);
    }

    public static InsuranceBuilder builder() {
        return new InsuranceBuilder();
    }

    public Long getId() {
        return id;
    }

    public Long getIdentificationNumber() {
        return this.identificationNumber;
    }

    public LocalDateTime getStartInsurance() {
        return this.startInsurance;
    }

    public LocalDateTime getEndInsurance() {
        return this.endInsurance;
    }

    public Car getCar() {
        return this.car;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdentificationNumber(Long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setStartInsurance(LocalDateTime startInsurance) {
        this.startInsurance = startInsurance;
    }

    public void setEndInsurance(LocalDateTime endInsurance) {
        this.endInsurance = endInsurance;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Insurance)) return false;
        final Insurance other = (Insurance) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$identificationNumber = this.getIdentificationNumber();
        final Object other$identificationNumber = other.getIdentificationNumber();
        if (this$identificationNumber == null ? other$identificationNumber != null : !this$identificationNumber.equals(other$identificationNumber))
            return false;
        final Object this$startInsurance = this.getStartInsurance();
        final Object other$startInsurance = other.getStartInsurance();
        if (this$startInsurance == null ? other$startInsurance != null : !this$startInsurance.equals(other$startInsurance))
            return false;
        final Object this$endInsurance = this.getEndInsurance();
        final Object other$endInsurance = other.getEndInsurance();
        if (this$endInsurance == null ? other$endInsurance != null : !this$endInsurance.equals(other$endInsurance))
            return false;
        final Object this$car = this.getCar();
        final Object other$car = other.getCar();
        if (this$car == null ? other$car != null : !this$car.equals(other$car)) return false;
        final Object this$isActive = this.getIsActive();
        final Object other$isActive = other.getIsActive();
        if (this$isActive == null ? other$isActive != null : !this$isActive.equals(other$isActive)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Insurance;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $identificationNumber = this.getIdentificationNumber();
        result = result * PRIME + ($identificationNumber == null ? 43 : $identificationNumber.hashCode());
        final Object $startInsurance = this.getStartInsurance();
        result = result * PRIME + ($startInsurance == null ? 43 : $startInsurance.hashCode());
        final Object $endInsurance = this.getEndInsurance();
        result = result * PRIME + ($endInsurance == null ? 43 : $endInsurance.hashCode());
        final Object $car = this.getCar();
        result = result * PRIME + ($car == null ? 43 : $car.hashCode());
        final Object $isActive = this.getIsActive();
        result = result * PRIME + ($isActive == null ? 43 : $isActive.hashCode());
        return result;
    }

    public String toString() {
        return "Insurance(id=" + this.getId() + ", identificationNumber=" + this.getIdentificationNumber() + ", startInsurance=" + this.getStartInsurance() + ", endInsurance=" + this.getEndInsurance() + ", car=" + this.getCar() + ", isActive=" + this.getIsActive() + ")";
    }

    public static class InsuranceBuilder {
        private Long id;
        private Long identificationNumber;
        private LocalDateTime startInsurance;
        private LocalDateTime endInsurance;
        private Car car;
        private Boolean isActive;

        InsuranceBuilder() {
        }

        public InsuranceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public InsuranceBuilder identificationNumber(Long identificationNumber) {
            this.identificationNumber = identificationNumber;
            return this;
        }

        public InsuranceBuilder startInsurance(LocalDateTime startInsurance) {
            this.startInsurance = startInsurance;
            return this;
        }

        public InsuranceBuilder endInsurance(LocalDateTime endInsurance) {
            this.endInsurance = endInsurance;
            return this;
        }

        public InsuranceBuilder car(Car car) {
            this.car = car;
            return this;
        }

        public InsuranceBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Insurance build() {
            return new Insurance(id, identificationNumber, startInsurance, endInsurance, car);
        }

        public String toString() {
            return "Insurance.InsuranceBuilder(id=" + this.id + ", identificationNumber=" + this.identificationNumber + ", startInsurance=" + this.startInsurance + ", endInsurance=" + this.endInsurance + ", car=" + this.car + ", isActive=" + this.isActive + ")";
        }
    }
}
