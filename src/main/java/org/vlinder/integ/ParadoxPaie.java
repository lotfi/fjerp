package org.vlinder.integ;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParadoxPaie
{
    private static final Logger log = LogManager.getLogger(ParadoxPaie.class);

    public static void main(String[] args)
    {
        try
        {
            Class.forName("com.googlecode.paradox.Driver");

            java.sql.Connection cnt = DriverManager.getConnection("jdbc:paradox:C:/Travail/Kahrakib/NextPaie/data");

            Statement stm = cnt.createStatement();

            ResultSet set = stm.executeQuery("select * from employe ");

            ResultSetMetaData metadata = set.getMetaData();

            int number = metadata.getColumnCount();

            for (int i = 1; i <= number; i++)
            {
                System.out.println("Column=" + i);

                System.out.println("Column name=" + metadata.getColumnName(i));

                System.out.println("Column type=" + metadata.getColumnTypeName(i));

                System.out.println("---");
            }

            set.close();

            stm.close();

            cnt.close();
        }

        catch (Exception e)
        {
            log.error(e);
        }
    }
}
