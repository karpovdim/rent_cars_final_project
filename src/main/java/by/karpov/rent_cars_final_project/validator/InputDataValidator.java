package by.karpov.rent_cars_final_project.validator;

import by.karpov.rent_cars_final_project.entity.Car;
import by.karpov.rent_cars_final_project.entity.Order;
import by.karpov.rent_cars_final_project.entity.User;
import by.karpov.rent_cars_final_project.exception.ServiceException;
import by.karpov.rent_cars_final_project.service.impl.CarServiceImpl;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.validator.routines.EmailValidator;

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
        return id > 0;
    }

    public boolean isEmailValid(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public boolean isPasswordValid(String password) {
        if (password == null || password.isBlank()) {
            return false;
        }
        return password.matches(PASSWORD_REGEX);
    }

    public boolean isNameValid(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        return name.length() <= MAX_LENGTH_NAME && name.matches(NAME_REGEX);
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            return false;
        }
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    public boolean isCardNumberValid(String cardNumber) {
        if (cardNumber == null || cardNumber.isBlank()) {
            return false;
        }
        return cardNumber.matches(CARD_NUMBER_REGEX);
    }

    public boolean isCvvValid(String cvv) {
        if (cvv == null || cvv.isBlank()) {
            return false;
        }
        return cvv.matches(CVV_REGEX);
    }

    public boolean isAdmin(User user) {
        return  ADMIN.equals(user.getRole() ) ;
    }

    public boolean isCanBeDelete(User user, Long id) {
        return !user.getId().equals(id);
    }

    public boolean isCodeValid(String code) {
        if (code == null || code.isBlank()) {
            return false;
        }
        return code.matches(CODE_AUTHENTICATION_REGEX);
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
        if (description == null || description.isBlank()) {
            return false;
        }
        return description.matches(DESCRIPTION_REGEX);
    }

    public boolean isRegNumberValid(String regNumber) {
        if (regNumber == null || regNumber.isBlank()) {
            return false;
        }
        return regNumber.matches(REGISTRATION_NUMBER_REGEX);
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
}
