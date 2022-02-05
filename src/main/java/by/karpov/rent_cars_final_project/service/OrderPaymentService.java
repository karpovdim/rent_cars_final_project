package by.karpov.rent_cars_final_project.service;

import java.math.BigDecimal;

public interface OrderPaymentService {
    boolean payForOrder(String cardNumber, String cvv, BigDecimal price);
    boolean checkPayDate(String cardNumber, String cvv);
}
