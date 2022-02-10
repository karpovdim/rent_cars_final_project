package by.karpov.rent_cars_final_project.entity;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;


public class Order implements BaseEntity {
    public Order(int id) {
    }

    public enum OrderStatus {
        PAID, AWAITS_PAYMENT, DECLINED
    }

    private Long id;
    private BigDecimal price;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private OrderStatus status;
    private Car car;
    private User user;

    public Order(Long id, BigDecimal price, LocalDate rentDate, LocalDate returnDate, OrderStatus status, Car car, User user) {
        this.id = id;
        this.price = price;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.status = status;
        this.car = car;
        this.user = user;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public BigDecimal getPrice() {
        if (returnDate == null || rentDate == null || car.getCost() == null) {
            return BigDecimal.ZERO;
        }
        final var period = Period.between(rentDate, returnDate);
        final var daysRent = BigDecimal.valueOf(period.getDays());
        this.price = daysRent.multiply(car.getCost());
        return this.price;
    }

    public LocalDate getRentDate() {
        return this.rentDate;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Car getCar() {
        return this.car;
    }

    public User getUser() {
        return this.user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Order)) return false;
        final Order other = (Order) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$rentDate = this.getRentDate();
        final Object other$rentDate = other.getRentDate();
        if (this$rentDate == null ? other$rentDate != null : !this$rentDate.equals(other$rentDate)) return false;
        final Object this$returnDate = this.getReturnDate();
        final Object other$returnDate = other.getReturnDate();
        if (this$returnDate == null ? other$returnDate != null : !this$returnDate.equals(other$returnDate))
            return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$car = this.getCar();
        final Object other$car = other.getCar();
        if (this$car == null ? other$car != null : !this$car.equals(other$car)) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Order;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $rentDate = this.getRentDate();
        result = result * PRIME + ($rentDate == null ? 43 : $rentDate.hashCode());
        final Object $returnDate = this.getReturnDate();
        result = result * PRIME + ($returnDate == null ? 43 : $returnDate.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $car = this.getCar();
        result = result * PRIME + ($car == null ? 43 : $car.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        return result;
    }

    public String toString() {
        return "Order(id=" + this.getId() + ", price=" + this.getPrice() + ", rentDate=" + this.getRentDate() + ", returnDate=" + this.getReturnDate() + ", status=" + this.getStatus() + ", car=" + this.getCar() + ", user=" + this.getUser() + ")";
    }

    public static class OrderBuilder {
        private Long id;
        private BigDecimal price;
        private LocalDate rentDate;
        private LocalDate returnDate;
        private OrderStatus status;
        private Car car;
        private User user;

        OrderBuilder() {
        }

        public OrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderBuilder rentDate(LocalDate rentDate) {
            this.rentDate = rentDate;
            return this;
        }

        public OrderBuilder returnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public OrderBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderBuilder car(Car car) {
            this.car = car;
            return this;
        }

        public OrderBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Order build() {
            return new Order(id, price, rentDate, returnDate, status, car, user);
        }

        public String toString() {
            return "Order.OrderBuilder(id=" + this.id + ", price=" + this.price + ", rentDate=" + this.rentDate + ", returnDate=" + this.returnDate + ", status=" + this.status + ", car=" + this.car + ", user=" + this.user + ")";
        }
    }
}
