package Connection;

import by.karpov.rent_cars_final_project.exception.DaoException;
import by.karpov.rent_cars_final_project.pool.ConnectionPool;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.sql.Connection;

public class ConnectionPoolTest {
    private ConnectionPool connectionPool;

    @BeforeClass
    public void beforeClass() {
        connectionPool = ConnectionPool.pool();
    }

    @Test(description = "Testing method getConnection")
    public void getConnectionTest() {
        Connection connection = connectionPool.getConnection();
        Assert.assertNotNull(connection);
    }

    @Test(description = "Testing method destroyPool")
    public void destroyPoolTest() throws DaoException {
        Assert.assertTrue(connectionPool.closePool());
    }

    }
