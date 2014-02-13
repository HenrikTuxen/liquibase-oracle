package liquibase.ext.ora.adddeferredprimarykey;

import liquibase.ext.ora.testing.BaseTestCase;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Before;
import org.junit.Test;

public class AddDeferredPrimaryKeyDBTest extends BaseTestCase {

    private IDataSet loadedDataSet;
    private final String TABLE_NAME = "USER_CONSTRAINTS";

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/ora/adddeferredprimarykey/changelog.test.xml";
        connectToDB();
        cleanDB();
    }

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(connection);
    }

    protected IDataSet getDataSet() throws Exception {
        loadedDataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream(
                "liquibase/ext/ora/adddeferredprimarykey/input.xml"));
        return loadedDataSet;
    }

    // TODO: Remove Errors!
    @Test
    public void testCompare() throws Exception {
        QueryDataSet actualDataSet = new QueryDataSet(getConnection());

        liquiBase.update((String) null);

        actualDataSet.addTable(TABLE_NAME, "select constraint_name as primary_key_name from " + TABLE_NAME
                + " WHERE constraint_type = 'P' and constraint_name = 'PK_ADDDEFERREDPRIMARYKEYTEST'");
        loadedDataSet = getDataSet();

        Assertion.assertEquals(loadedDataSet, actualDataSet);
    }
}
