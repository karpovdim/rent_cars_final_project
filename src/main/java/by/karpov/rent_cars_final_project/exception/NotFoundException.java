package by.karpov.rent_cars_final_project.exception;


public class NotFoundException extends Exception {
    private final long id;

    public NotFoundException(long id) {
        this.id = id;
    } public NotFoundException(long id ,String msg) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }
}

