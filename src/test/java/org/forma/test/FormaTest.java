package org.forma.test;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.forma.model.Codif;
import org.forma.repo.CodifRepository;
import org.forma.service.CodifService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FormaTest
{

    private static final Logger log = LogManager.getLogger(FormaTest.class);

    @Autowired
    private CodifRepository codifRepository;

    @Autowired
    private CodifService codifService;

    @Autowired
    private DataSource dataSource;

    @Test
    public void testCase1()
    {
        log.debug("testCase1");

        Assert.assertNotNull(codifRepository);

        // int n = codifService.findAll().size();

        // Assert.assertEquals(n, 0);
    }

    @Test
    public void testCase2()
    {
        log.debug("testCase2");

        Assert.assertNotNull(codifService);
    }

    @Test
    public void testCase3()
    {
        log.debug("testCase3");

        Assert.assertNotNull(dataSource);
    }

    @Test
    public void testCase4()
    {
        log.debug("testCase4");

        Codif codif = new Codif();

        codif.setCode("dip1");
        codif.setCategorie("diplome");
        codif.setLabel("licence bac+3");
        codif.setEtat("1");

        /*
         * try { codifService.storeCodif(codif); }
         * 
         * catch (Exception e) { log.error(e); }
         */

    }
}
