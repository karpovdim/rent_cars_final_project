package by.karpov.rent_cars_final_project.exception;

import java.sql.SQLException;

public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(Throwable throwable) {
        super(throwable);
    }

    public DaoException(String msg, SQLException e) {
        super(msg, e);
    }

    public DaoException(String msg) {
        super(msg);
    }
}
