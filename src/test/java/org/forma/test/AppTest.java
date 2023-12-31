package org.forma.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.forma.FormaApp;
import org.forma.model.Codif;
import org.forma.repo.CodifRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FormaApp.class)
public class AppTest
{

    private final static Logger log = LogManager.getLogger(AppTest.class);

    /*
     * We will be using mysql databases we configured in our properties file for our
     * tests Make sure your datasource connections are correct otherwise the test
     * will fail
     */

    @Autowired
    private CodifRepository codifRepository;

    private Codif codif;

    @Before
    public void initializeDataObjects()
    {
        log.debug("initializeDataObjects");

        codif = new Codif();

    }

    @Test
    public void codifRepositoryTest()
    {
        log.debug("codifRepositoryTest");

        Assert.assertNotNull(codifRepository);
    }

    @Test
    public void storeCodif()
    {
        log.debug("storeCodif");

        Assert.assertNotNull(codif);
    }

}