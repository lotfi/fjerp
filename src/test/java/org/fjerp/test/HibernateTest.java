package org.fjerp.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fjerp.service.MigrationService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class HibernateTest
{
    private static final Logger log = LogManager.getLogger(HibernateTest.class);

    private static SessionFactory sessionFactory = null;

    private static Session session = null;

    @BeforeClass
    public static void setUp()
    {
        log.debug("setUp");

        try
        {
            Configuration config = new Configuration();

            config.configure();

            // local SessionFactory bean created
            sessionFactory = config.buildSessionFactory();

            session = sessionFactory.getCurrentSession();
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }

    @Test
    public void testMigrationGrandLivre()
    {
        try
        {
            session = sessionFactory.openSession();

            MigrationService service = new MigrationService();

            service.migrerEcritures(session);

            session.close();
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }

    @Ignore
    public void testSessionObjects()
    {
        log.debug("testSessionObjects");

        Assert.assertNotNull(sessionFactory);

        try
        {
            session = sessionFactory.openSession();

            /*
             * Transaction trans = session.beginTransaction();
             * 
             * Compte compte = new Compte();
             * 
             * compte.setCategorie("Gen");
             * 
             * compte.setLabel("Invest");
             * 
             * compte.setCode("24");
             * 
             * session.save(compte);
             * 
             * trans.commit();
             */

            session.close();
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }

}
