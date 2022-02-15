package by.karpov.rent_cars_final_project.controller.filter.dao.impl;

public final class QueryDao {
    /**
     * USER QUERY
     */
    protected static final String FIND_BY_EMAIL_USER = """
            SELECT users.id,
             first_name,
             last_name,
             role_user,
             status_user,
             email_login,
             phone_user,
             su.status,
             ru.role
             FROM rent_car.users
             JOIN rent_car.status_users su ON su.id = users.status_user
             JOIN rent_car.role_users ru ON ru.id = users.role_user
             WHERE users.email_login = ?
            """;
    protected static final String FIND_USERS_BY_LIMIT = """
            SELECT users.id,
                      first_name,
                      last_name,
                      role_user,
                      status_user,
                      email_login,
                      phone_user,
                      su.status,
                      ru.role
                      FROM rent_car.users
                      JOIN rent_car.status_users su ON su.id = users.status_user
                      JOIN rent_car.role_users ru ON ru.id = users.role_user
             LIMIT ?, ?
            """;
    protected static final String FIND_BY_EMAIL_AND_PASSWORD_USER = """
            SELECT users.id,
             first_name,
             last_name,
             role_user,
             status_user,
             email_login,
             phone_user,
             su.status,
             ru.role
             FROM rent_car.users
             JOIN rent_car.status_users su ON su.id = users.status_user
             JOIN rent_car.role_users ru ON ru.id = users.role_user
             WHERE users.email_login = ? AND users.password = ?
            """;
    protected static final String FIND_BY_CODE_AUTHENTICATION_USER = """
            SELECT users.id,
             first_name,
             last_name,
             role_user,
             status_user,
             email_login,
             phone_user,
             su.status,
             ru.role
             FROM rent_car.users
             JOIN rent_car.status_users su ON su.id = users.status_user
             JOIN rent_car.role_users ru ON ru.id = users.role_user
             WHERE users.password_authentication = ?
            """;
    protected static final String FIND_BY_ID_USER = """
            SELECT users.id,
             first_name,
             last_name,
             role_user,
             status_user,
             email_login,
             phone_user,
             su.status,
             ru.role
             FROM rent_car.users
             JOIN rent_car.status_users su ON su.id = users.status_user
             JOIN rent_car.role_users ru ON ru.id = users.role_user
             WHERE users.id = ?
            """;
    protected static final String FIND_ALL_USER = """ 
            SELECT users.id,
             first_name,
             last_name,
             role_user,
             status_user,
             email_login,
             phone_user,
             ru.role,
             su.status
             FROM rent_car.users
             JOIN rent_car.status_users su ON su.id = users.status_user
             JOIN rent_car.role_users ru ON ru.id = users.role_user
            """;
    protected static final String INSERT_USER = """
            INSERT INTO rent_car.users(
            first_name,
            last_name,
            email_login,
            password,
            role_user,
            status_user,
            phone_user,
            password_authentication)
            VALUES(?,?,?,?,?,?,?,?)
            """;
    protected static final String DELETE_USER = """
            DELETE
            FROM rent_car.users
            WHERE id = ?
            """;
    protected static final String UPDATE_USER = """
            UPDATE rent_car.users
            SET first_name = ?,
            last_name = ?,
            email_login = ?,
            role_user = ?,
            status_user = ?,
            phone_user = ?
            WHERE id = ?
            """;
    protected static final String SQL_UPDATE_CODE = """
            UPDATE rent_car.users
            SET
            password_authentication = ?
            WHERE id = ?
            """;
    protected static final String SQL_UPDATE_PASSWORD = """
            UPDATE rent_car.users
            SET
            password = ?
            WHERE email_login = ?
            """;
    /**
     * INSURANCE QUERY
     **/
    protected static final String INSERT_INSURANCE = """
             INSERT INTO rent_car.insurances(
            start_insurance,
            end_insurance,
            is_active,
            identification_number,
            car_id
            )
             VALUES(?,?,?,?,?)
             """;
    protected static final String UPDATE_INSURANCE = """
             UPDATE rent_car.insurances
             SET start_insurance = ?,
            end_insurance = ?,
            is_active = ?,
            identification_number = ?,
            car_id = ?
             WHERE id = ?
             """;
    protected static final String FIND_BY_ID_INSURANCE = """
            SELECT insurances.id,
             start_insurance,
             end_insurance,
             is_active,
             identification_number,
             c.id
             FROM rent_car.insurances
            JOIN rent_car.cars c on c.id = insurances.car_id
             WHERE insurances.id = ?
            """;
    protected static final String FIND_ALL_INSURANCE = """
            SELECT insurances.id,
             start_insurance,
             end_insurance,
             is_active,
             identification_number,
             c.id
             FROM rent_car.insurances
            JOIN rent_car.cars c on c.id = insurances.car_id
            """;
    protected static final String DELETE_INSURANCE = """
            DELETE
            FROM rent_car.insurances
            WHERE id = ?
            """;

    /**
     * CAR QUERY
     **/
    protected static final String FIND_ALL_CAR = """ 
            SELECT cars.id,
            registration_number,
            cost,
            image_url,
            car_status,
            transmission_type,
            conditioner,
            car_description,
            cs.status,
            tt.type
             FROM rent_car.cars
             JOIN   rent_car.status_cars cs on cs.id = cars.car_status
             JOIN rent_car.transmission_type tt on tt.id = cars.transmission_type
            """;
    protected static final String FIND_CARS_BY_LIMIT = """ 
            SELECT cars.id,
            registration_number,
            cost,
            image_url,
            car_status,
            transmission_type,
            conditioner,
            car_description,
            cs.status,
            tt.type
             FROM rent_car.cars
             JOIN   rent_car.status_cars cs on cs.id = cars.car_status
             JOIN rent_car.transmission_type tt on tt.id = cars.transmission_type
             ORDER BY cars.id
             LIMIT ?,?
            """;

    protected static final String FIND_BY_ID_CAR = FIND_ALL_CAR + """
             WHERE
             cars.id = ?
            """;
    protected static final String FIND_BY_REGISTRATION_NUMBER_CAR = FIND_ALL_CAR + """
             WHERE
             cars.registration_number = ?
            """;

    protected static final String INSERT_CAR = """
            INSERT INTO rent_car.cars(
            registration_number,
            cost,
            image_url,
            car_status,
            transmission_type,
            conditioner,
            car_description
            )
            VALUES(?,?,?,?,?,?,?)
            """;
    protected static final String UPDATE_CAR = """
            UPDATE rent_car.cars
            SET registration_number = ?,
            cost = ?,
            image_url = ?,
            car_status = ?,
            transmission_type = ?,
            conditioner = ?,
            car_description = ?
            WHERE id = ?
            """;
    protected static final String DELETE_CAR = """
            DELETE
            FROM rent_car.cars
            WHERE id = ?
            """;
    /**
     * ORDERS QUERY
     **/
    protected static final String FIND_ORDERS_BY_LIMIT = """
            SELECT orders.id,
                        price,
                        rent_date,
                        return_date,
                        car_id,
                        user_id,
                        status_id,
                        so.order_type
                        FROM rent_car.orders
                        JOIN rent_car.status_orders so on so.id = orders.status_id
                        ORDER BY so.id
             LIMIT ?, ?
            """;
    public static final String FIND_ORDERS_BY_USER_ID_AND_LIMIT = """
            SELECT orders.id,
            price,
            rent_date,
            return_date,
            car_id,
            user_id,
            status_id,
            so.order_type
            FROM rent_car.orders
            JOIN rent_car.status_orders so on so.id = orders.status_id
            WHERE  user_id = ?
            ORDER BY so.id
            LIMIT ?, ?
                    """;

    protected static final String FIND_ALL_ORDERS = """
            SELECT orders.id,
            price,
            rent_date,
            return_date,
            car_id,
            user_id,
            status_id,
            so.order_type
            FROM rent_car.orders
            JOIN rent_car.status_orders so on so.id = orders.status_id
            """;

    protected static final String FIND_ALL_ORDERS_WITH_STATUS = """
            SELECT orders.id,
            price,
            rent_date,
            return_date,
            car_id,
            user_id,
            status_id,
            so.order_type
            FROM rent_car.orders
            JOIN rent_car.status_orders so on so.id = orders.status_id WHERE status = ?
            """;

    protected static final String FIND_BY_ID_ORDER = FIND_ALL_ORDERS + """
            WHERE
            orders.id = ?
            """;


    protected static final String INSERT_ORDER = """
            INSERT INTO rent_car.orders(
            price,
            rent_date,
            return_date,
            car_id,
            user_id,
            status_id
            )
            VALUES (?,?,?,?,?,?)
            """;
    protected static final String UPDATE_ORDER = """
            UPDATE rent_car.orders
            SET price = ?,
            rent_date = ?,
            return_date = ?,
            car_id = ?,
            user_id = ?,
            status_id = ?
            WHERE id = ?
            """;
    protected static final String DELETE_ORDER = """
            DELETE
            FROM rent_car.orders
            WHERE id = ?
            """;
    public static final String SQL_FIND_CAR_ID_BY_USER_ID = """
            SELECT DISTINCT car_id 
            FROM rent_car.orders
            where user_id = ? 
            """;

    /**
     * COLUMN NAME
     **/

    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_FIRST_NAME = "first_name";
    protected static final String COLUMN_LAST_NAME = "last_name";
    protected static final String COLUMN_EMAIL_LOGIN = "email_login";
    protected static final String COLUMN_PASSWORD = "password";
    protected static final String COLUMN_ROLE_USER = "ru.role";
    protected static final String COLUMN_STATUS_USER = "su.status";
    protected static final String COLUMN_PHONE_USER = "phone_user";

    protected static final String COLUMN_PRICE_ORDER = "price";
    protected static final String COLUMN_RENT_DATE_ORDER = "rent_date";
    protected static final String COLUMN_RETURN_DATE_ORDER = "return_date";
    protected static final String COLUMN_ORDER_CAR_ID = "car_id";
    protected static final String COLUMN_ORDER_USER_ID = "user_id";
    protected static final String COLUMN_ORDER_STATUS_ID = "so.order_type";

    protected static final String COLUMN_DATE_START = "start_insurance";
    protected static final String COLUMN_DATE_END = "end_insurance";
    protected static final String COLUMN_IS_ACTIVE = "is_active";
    protected static final String COLUMN_IDENTIFICATION_NUMBER = "identification_number";
    protected static final String COLUMN_CAR_ID = "c.id";

    protected static final String REGISTRATION_NUMBER = "registration_number";
    protected static final String COST_BY_CAR = "cost";
    protected static final String ID_CAR = "car_id";
    protected static final String IMAGE_URL = "image_url";
    protected static final String CAR_STATUS = "cs.status";
    protected static final String TRANSMISSION_TYPE = "tt.type";
    protected static final String CONDITIONER = "conditioner";
    protected static final String CAR_DESCRIPTION = "car_description";
}
