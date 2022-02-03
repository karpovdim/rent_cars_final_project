package by.karpov.rent_cars_final_project.command;

public final class RequestParameter {
	public static final String COMMAND = "command";
	public static final String CURRENT_PAGE_NUMBER = "page";
	public static final String CAR_BOOKED = "booked";

	// Sign up confirmation code markers
	public static final String CODE = "code";

	public static final String CHANGE_CARS_STATUS_INCORRECT = "change_car_status_incorrect";
	public static final String ORDER_INCORRECT_DATE = "incorrect_date";
	public static final String ORDER_PICK_UP_BEFORE_RETURN = "pick_up_date_is_before_return_date";
	public static final String INVALID_FILE = "invalid_file";
	public static final String INPUT_DATA_INCORRECT = "input_data_incorrect";
	public static final String ORDER_ID_INCORRECT = "order_id_incorrect";
	public static final String CAR_MANUFACTURER_INCORRECT = "car_manufacturer_incorrect";

	// Date data markers
	public static final String RENT_DATE = "pick_up_date";
	public static final String RETURN_DATE = "return_date";

	// User data markers
	public static final String USER_ID = "id";
	public static final String USER_EMAIL = "email";
	public static final String NEW_USER_EMAIL = "new_email";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_DATE_OF_BIRTH = "date_of_birth";
	public static final String USER_PHONE_NUMBER = "phone_number";
	public static final String USER_ROLE = "role";
	public static final String USER_STATUS = "status";
	public static final String USER_PASSWORD_FOR_AUTHENTICATION = "password_for_authentication";
	// Password data markers
	public static final String OLD_PASSWORD = "old_password";
	public static final String NEW_PASSWORD = "new_password";

	// Car data marker
	public static final String CAR_ID = "car_id";
	public static final String CAR_CONDITIONER = "conditioner";
	public static final String CAR_TRANSMISSION = "transmission";
	public static final String CAR_COST = "cost";
	public static final String CAR_STATUS = "car_status";
	public static final String CAR_DESCRIPTION = "car_description";
	public static final String CAR_REGISTRATION_NUMBER = "registration_number";
	// List data markers
	public static final String LIST_ORDERS = "orders";
	public static final String LIST_CARS = "cars";
	public static final String LIST_USERS = "users";

	// Order data markers
	public static final String ORDER_ID = "order_id";
	public static final String ORDER_STATUS = "order_status";

	// Error data markers
	public static final String AUTHENTICATION_ERROR = "authentication_error";
	public static final String ENTERED_CODE_ERROR = "entered_code_error";

	// Incorrect data to changes markers
	public static final String CHANGE_STATUS_INCORRECT = "change_status_incorrect";
	public static final String CHANGE_CAR_STATUS_INCORRECT = "change_car_status_incorrect";
	public static final String CHANGE_ORDER_STATUS_INCORRECT = "change_order_status_incorrect";
	public static final String USER_NOT_FOUND_OF_ROLE = "user_not_found_of_role";
	public static final String USER_NOT_FOUND_OF_STATUS = "user_not_found_of_status";
	public static final String CHANGE_ROLE_INCORRECT = "change_role_incorrect";
	public static  final String DELETE_USER_INCORRECT = "delete_user_incorrect";
	public static final String USER_NOT_FOUND_OF_DELETE = "user_not_found_of_delete";
	public static final String CAR_NOT_FOUND_OF_STATUS = "car_not_found_of_status";
	public static final String ORDER_NOT_FOUND_OF_STATUS = "order_not_found_of_status";
	public static final String CAR_NOT_FOUND_OF_COST = "car_not_found_of_cost";
	public static final String CHANGE_COST_INCORRECT = "change_cost_incorrect";
	public static final String CHANGE_ERROR = "change_error";

	// Incorrect data markers
	public static final String CAR_NOT_FOUND_OF_DELETE = "car_not_found_of_delete";
	public static  final String DELETE_CAR_INCORRECT = "delete_car_incorrect";
//	public static final String CHANGE_CARS_STATUS_INCORRECT = "change_car_status_incorrect";
//	public static final String ORDER_INCORRECT_DATE = "incorrect_date";
//	public static final String ORDER_PICK_UP_BEFORE_RETURN = "pick_up_date_is_before_return_date";
//	public static final String INVALID_FILE = "invalid_file";
//	public static final String INPUT_DATA_INCORRECT = "input_data_incorrect";
//	public static final String ORDER_ID_INCORRECT = "order_id_incorrect";
//	public static final String CAR_MANUFACTURER_INCORRECT = "car_manufacturer_incorrect";
	public static final String INPUT_DATA_CAR_STATUS_INCORRECT = "car_status_incorrect";
	public static final String INPUT_DATA_CAR_TRANSMISSION_INCORRECT = "car_transmission_type_incorrect";
	public static final String INPUT_DATA_CAR_COST_INCORRECT = "car_cost_incorrect";
	public static final String INPUT_DATA_CAR_DESCRIPTION_INCORRECT = "car_description_incorrect";
	public static final String INPUT_DATA_CAR_REGISTRATION_NUMBER_INCORRECT = "car_registration_number_incorrect";
	public static final String CAR_EXISTS = "car_exists";

	private RequestParameter() {
	}
}
