package by.karpov.rent_cars_final_project.service;

import java.math.BigDecimal;

/**
 * OrderPayment interface
 */
public interface OrderPaymentService {
    /**
     * pay for order
     *
     * @param cardNumber
     * @param cvv
     * @param price
     * @return boolean result
     */
    boolean payForOrder(String cardNumber, String cvv, BigDecimal price);

    /**
     * check pay date
     *
     * @param cardNumber
     * @param cvv
     * @return boolean result
     */
    boolean checkPayDate(String cardNumber, String cvv);
}
