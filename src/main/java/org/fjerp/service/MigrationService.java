package org.fjerp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fjerp.modele.Operation;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MigrationService
{
    private static final Logger log = LogManager.getLogger(MigrationService.class);

    String url = "jdbc:firebirdsql://localhost:3050/c:/Travail/Kahrakib/Donnees/Gbk/comptakah.gdb";

    String util = "sysdba";

    String mdp = "masterkey";

    String driverName = "org.firebirdsql.jdbc.FBDriver";

    public void migrerEcritures(Session session)
    {
        log.debug("migrerEcritures");

        Connection connection = null;

        Statement statement = null;

        ResultSet resultSet = null;

        try
        {
            Transaction trans = session.beginTransaction();

            Class.forName("org.firebirdsql.jdbc.FBDriver");

            connection = DriverManager.getConnection(url, util, mdp);

            statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    "select cc, mois, codif, piece, type_ecrit, montant_d, montant_c, libel, exer, num_lig, cptem, cptea, journee, inda, indm from validation order by cc, exer, mois, piece, num_lig ");

            int i = 0;

            while (
                resultSet.next() && i < 4000
            )
            {
                Operation oper = new Operation();

                oper.setCodeUnite(resultSet.getString("cc"));

                oper.setMois(Byte.parseByte(resultSet.getString("mois")));

                oper.setCodif(resultSet.getString("codif"));

                oper.setPiece(resultSet.getString("piece"));

                oper.setDebit(resultSet.getDouble("montant_d"));

                oper.setCredit(resultSet.getDouble("montant_c"));

                oper.setLabel(resultSet.getString("libel"));

                oper.setAnnee(Short.parseShort(resultSet.getString("exer")));

                oper.setNumero((short) resultSet.getInt("num_lig"));

                oper.setJour(Byte.parseByte(resultSet.getString("journee")));

                oper.setCompte1(resultSet.getString("cptem"));

                if (
                    oper.getCompte1() == null || oper.getCompte1().isEmpty()
                )
                {
                    oper.setCompte1("");
                }

                oper.setCompte2(resultSet.getString("cptea"));

                session.save(oper);

                i++;
            }

            trans.commit();
        }

        catch (Exception e)
        {
            log.error(e);
        }

        finally
        {
            try
            {
                if (
                    resultSet != null
                )
                {
                    resultSet.close();

                    resultSet = null;
                }

                if (
                    statement != null
                )
                {
                    statement.close();

                    statement = null;
                }

                if (
                    connection != null
                )
                {
                    connection.close();

                    connection = null;
                }
            }

            catch (Exception e)
            {
                log.error(e);
            }
        }
    }
}
