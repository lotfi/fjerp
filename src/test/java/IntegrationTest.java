import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.vlinder.core.RefDataBackEnd;
import org.vlinder.core.model.RefData;
import org.vlinder.core.service.Persistance;

public class IntegrationTest
{
    private static final Logger log = LogManager.getLogger(IntegrationTest.class);

    private static RefDataBackEnd refdataBackEnd = null;

    @BeforeClass
    public static void setUp()
    {
        log.debug("setUp");

        Persistance.setup();

        refdataBackEnd = new RefDataBackEnd();
    }

    @Ignore
    public void storeRefData()
    {
        log.debug("storeRefData");

        RefData refdata = new RefData();

        refdata.setId(1);

        refdata.setCode("code1");

        IntegrationTest.refdataBackEnd.store(refdata);
    }

    @Ignore
    public void findRefData()
    {
        log.debug("storeRefData");

        RefData refdata = new RefData();

        refdata.setId(1);

        refdataBackEnd.find(refdata);

        assertNotNull(refdata.getCode());
    }

    @Test
    public void readFile()
    {

    }

}