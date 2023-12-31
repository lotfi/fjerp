package org.forma.service;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fjerp.service.Dao;
import org.forma.model.Codif;
import org.forma.repo.CodifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vlinder.core.service.Persistance;

@Component(value = "codifService")
public class CodifService
{
    private static final Logger log = LogManager.getLogger(CodifService.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CodifRepository codifRepository;

    public CodifRepository getCodifRepository()
    {
        return codifRepository;
    }

    public void setCodifRepository(CodifRepository codifRepository)
    {
        this.codifRepository = codifRepository;
    }

    public DataSource getDataSource()
    {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public List<Codif> findAll()
    {
        log.debug("findAll");

        return codifRepository.findAll();
    }

    public void storeCodif(Codif codif) throws Exception
    {
        log.debug("storeCodif");

        codifRepository.save(codif);
    }

    public void store(Codif codif) throws Exception
    {
        log.debug("storeCodif");

        codifRepository.save(codif);
    }

    public void modify(Codif codif) throws Exception
    {
        log.debug("modify");

        codifRepository.save(codif);
    }

    public List<Codif> search(Codif codif) throws Exception
    {
        log.debug("search");

        List<Codif> codifDataSet = new LinkedList<Codif>();

        List<Object> paramList = new LinkedList<Object>();

        // conserver parametres

        // init requete
        // setInput("");

        try
        {
            // comptage
            // TODO count();

            // init requete
            String req = "select c.code, c.categorie, c.etat, c.label, c.etat, c.ref_parent ";
            req = req + " from codif c ";
            req = req + " left outer join codif p on p.code=c.ref_parent ";
            req = req + " where (1=1) ";

            // code
            if (
                codif.getCode() != null && codif.getCode().isEmpty() == false
            )
            {
                req = req + " and (c.code=?) ";

                paramList.add(codif.getCode());
            }

            // label
            codif.setLabel(Persistance.oterBlancs(codif.getLabel()));

            if (
                codif.getLabel().equals("") == false
            )
            {
                req = req + " and (c.label=?) ";

                paramList.add(codif.getLabel());
            }

            // ref_parent
            if (
                codif.getParent() != null && codif.getParent().getCode() != null
            )
            {
                req = req + " and (c.ref_parent=?) ";

                paramList.add(codif.getParent().getCode());
            }

            // tri
            req = req + " order by c.code ";

            // conserver requete sql (sans pagination)
            // setInput(req);

            Dao dao = Persistance.getDao();

            // ouvrir connexion
            Connection connexion = dao.openConnection(Persistance.getDataSource());

            // lancer requete
            Object[] params = new Object[paramList.size()];

            paramList.toArray(params);

            dao.read(connexion, req, params);

            // fermer connexion
            dao.closeConnection(connexion);
        }

        catch (Exception e)
        {
            log.error(e);

        }

        return codifDataSet;
    }

    public void delete(Codif codif) throws Exception
    {

    }

    public void load(Codif codif) throws Exception
    {

    }

    public void goFirst() throws Exception
    {

    }

    public void goLast() throws Exception
    {

    }

    public void goNext() throws Exception
    {

    }

    public void goPrevious() throws Exception
    {

    }
}
