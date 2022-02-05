package by.karpov.rent_cars_final_project.service;

import java.util.concurrent.Callable;
interface Base {
    default  <T> T uncheckCall(Callable<T> callable) {
        try {
            return callable.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
