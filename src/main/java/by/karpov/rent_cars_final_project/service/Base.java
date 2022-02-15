package by.karpov.rent_cars_final_project.service;

import java.util.concurrent.Callable;
/**
 *  Base interface
 */
interface Base {
    /**
     *  default method for change check Exception to uncheck Exception
     * @param callable
     * @param <T>
     * @return
     */
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
