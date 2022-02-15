package by.karpov.rent_cars_final_project.validator;

import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.util.List;

import static by.karpov.rent_cars_final_project.entity.User.UserRole.ADMIN;


public class InputDataValidator {

    private static final InputDataValidator instance = new InputDataValidator();

    private static final String PASSWORD_REGEX = "^[^<>]{5,64}$";
    private static final String DESCRIPTION_REGEX = "^[^<>]{0,64}$";
    private static final String REGISTRATION_NUMBER_REGEX = "^\\d{4}\\w{2}$";
    private static final String NAME_REGEX = "[a-zA-Z]+|[а-яА-ЯёЁ]+";
    private static final int MAX_LENGTH_NAME = 20;
    private static final String PHONE_NUMBER_REGEX = "^[25|44|33|29]\\d{8}";
    private static final String CARD_NUMBER_REGEX = "^\\d{16}$";
    private static final String CVV_REGEX = "^\\d{3}$";
    private static final String CODE_AUTHENTICATION_REGEX = "^\\d{4}$";

    private InputDataValidator() {
    }

    public static InputDataValidator getInstance() {
        return instance;
    }


    public boolean isUserValid(String email, String password, String firstName, String lastName, String phoneNumber) {
        return isEmailValid(email) && isPasswordValid(password) && isNameValid(firstName) && isNameValid(lastName)
                && isPhoneNumberValid(phoneNumber);
    }

    public boolean isIdValid(long id) {
        return id > 0 && id < 1000000;
    }

    public boolean isEmailValid(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email) && StringUtils.isNotBlank(email);
    }

    public boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_REGEX) && StringUtils.isNotBlank(password);
    }

    public boolean isNameValid(String name) {
        return name.length() <= MAX_LENGTH_NAME && name.matches(NAME_REGEX) && StringUtils.isNotBlank(name);
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_REGEX) && StringUtils.isNotBlank(phoneNumber);
    }

    public boolean isCardNumberValid(String cardNumber) {
        return cardNumber.matches(CARD_NUMBER_REGEX) && StringUtils.isNotBlank(cardNumber);
    }

    public boolean isCvvValid(String cvv) {
        return cvv.matches(CVV_REGEX) && StringUtils.isNotBlank(cvv);
    }

    public boolean isAdmin(User user) {
        return ADMIN.equals(user.getRole());
    }

    public boolean isCanBeChange(User user, Long id) {
        return !user.getId().equals(id);
    }

    public boolean isCodeValid(String code) {
        return code.matches(CODE_AUTHENTICATION_REGEX) && StringUtils.isNotBlank(code);
    }

    public boolean isStatusUserPresent(String status) {
        return EnumUtils.isValidEnum(User.UserStatus.class, status);
    }

    public boolean isRolePresent(String role) {
        return EnumUtils.isValidEnum(User.UserRole.class, role);
    }

    public boolean isActiveUser(User user) {
        return user.getUserStatus() == User.UserStatus.ACTIVE;
    }

    public boolean isActiveAdmin(User user) {
        return isAdmin(user) && isActiveUser(user);
    }

    public boolean isStatusCarPresent(String carStatus) {
        return EnumUtils.isValidEnum(Car.CarStatus.class, carStatus);
    }

    public boolean isTransmissionCarPresent(String transmissionType) {
        return EnumUtils.isValidEnum(Car.TransmissionType.class, transmissionType);
    }

    public boolean isCostValid(String cost) {
        try {
            final var value = Long.parseLong(cost);
            if (value <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isDescriptionValid(String description) {
        return description.matches(DESCRIPTION_REGEX) && StringUtils.isNotBlank(description);
    }

    public boolean isRegNumberValid(String regNumber) {
        return regNumber.matches(REGISTRATION_NUMBER_REGEX) && StringUtils.isNotBlank(regNumber);
    }

    public boolean isCarPresent(String regNumber) {
        final var carService = CarServiceImpl.getInstance();
        if (isRegNumberValid(regNumber)) {
            try {
                final var optionalCar = carService.findByRegistrationNumber(regNumber);
                if (optionalCar.isPresent()) {
                    return true;
                }
            } catch (ServiceException e) {
                return false;
            }
        }
        return false;
    }

    public boolean isStatusOrderPresent(String orderStatus) {
        return EnumUtils.isValidEnum(Order.OrderStatus.class, orderStatus);
    }
    public boolean isCarFreeOnThisDate(LocalDate rentDate, LocalDate returnDate, List<Order> listOrders) {
        int countTrueOptions = 0;
        for (Order order : listOrders) {
            if (returnDate.isBefore(order.getRentDate()) || rentDate.isAfter(order.getReturnDate())) {
                countTrueOptions++;
            }
        }
        return countTrueOptions == listOrders.size();
    }
}
