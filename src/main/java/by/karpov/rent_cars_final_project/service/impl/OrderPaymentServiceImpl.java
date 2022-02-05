package by.karpov.rent_cars_final_project.service.impl;

import by.karpov.rent_cars_final_project.service.OrderPaymentService;
import by.karpov.rent_cars_final_project.validator.InputDataValidator;

import java.math.BigDecimal;

public class OrderPaymentServiceImpl implements OrderPaymentService {
    private static final OrderPaymentServiceImpl instance = new OrderPaymentServiceImpl();

    private OrderPaymentServiceImpl() {
    }
    public static OrderPaymentServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean payForOrder(String cardNumber, String cvv, BigDecimal price) {
        return checkPayDate(cardNumber, cvv);
    }

    @Override
    public boolean checkPayDate(String cardNumber, String cvv) {
        InputDataValidator validator =  InputDataValidator.getInstance();
        return validator.isCvvValid(cvv) && validator.isCardNumberValid(cardNumber);
    }
}
