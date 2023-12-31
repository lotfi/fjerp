package org.vlinder.core;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fjerp.service.Dao;
import org.vlinder.core.common.BackEnd;
import org.vlinder.core.model.Brick;
import org.vlinder.core.model.Model;
import org.vlinder.core.model.RefData;
import org.vlinder.core.model.WorkUnit;
import org.vlinder.core.service.Persistance;

public class RefDataBackEnd extends BackEnd
{
    private static final Logger log = LogManager.getLogger(RefDataBackEnd.class);

    private List<RefData> states = new LinkedList<RefData>();

    public RefDataBackEnd()
    {
        log.debug("PersonneBackEnd");

        setLimit(10);
    }

    public List<RefData> findEntriesByParentId(String parentId)
    {
        log.debug("findEntriesByParentId");

        List<RefData> entries = new LinkedList<RefData>();

        Connection connexion = null;

        Dao dao = null;

        try
        {
            dao = Persistance.getDao();

            // ouvrir connexion
            connexion = dao.openConnection(Persistance.getDataSource());

            // lancer requete
            WorkUnit workUnit = dao.read(connexion,
                    "select id,code,state_code from refdata where parent_id=? order by id ", new Object[]
                    { parentId });

        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

        finally
        {
            // fermer connexion
            if (
                connexion != null
            )
            {
                try
                {
                    dao.closeConnection(connexion);
                }

                catch (Exception e)
                {
                    log.error(e);
                }
            }

            connexion = null;
        }

        return entries;
    }

    // TODO
    public List<RefData> getStates()
    {
        // return RefDataHelper.findRefData(RefData.CLASSID_STATE);
        return null;
    }

    private Map<String, Object> populateStoreParams(RefData refdata)
    {
        log.debug("generateStoreParams");

        Map<String, Object> params = new HashMap<String, Object>();

        // code
        String code = Persistance.oterBlancs(refdata.getCode());

        if (
            code.equals("") == false
        )
        {
            params.put("code", code);
        }

        // class_id
        if (
            refdata.getClassId() != (byte) 0
        )
        {
            params.put("class_id", refdata.getClassId());
        }

        // state_code
        if (
            refdata.getStateCode() != null
        )
        {
            params.put("state_code", refdata.getStateCode());
        }

        // act_id
        if (
            refdata.getActId() != 0
        )
        {
            params.put("act_id", refdata.getActId());
        }

        // parent_id
        if (
            refdata.getParentId() != 0
        )
        {
            params.put("parent_id", refdata.getParentId());
        }

        return params;
    }

    private Map<String, Object> convertRefdataToMap(RefData refdata)
    {
        log.debug("convertRefdataToMap");

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("code", refdata.getCode());

        map.put("class_id", refdata.getClassId());

        if (
            refdata.getParentId() != 0
        )
        {
            map.put("parent_id", refdata.getParentId());
        }

        if (
            refdata.getStateCode() != null && refdata.getStateCode().equals("") == false
        )
        {
            map.put("state_code", refdata.getStateCode());
        }

        if (
            refdata.getActId() != 0
        )
        {
            map.put("act_id", refdata.getActId());
        }

        return map;
    }

    public void store(RefData refdata)
    {
        log.debug("store");

        try
        {
            validateStore(null);

            if (
                peekNotice().getType() == Brick.BRICK_ERROR
            )
            {

                return;
            }

            Dao dao = Persistance.getDao();

            // ouvrir connexion
            Connection connection = dao.openConnection(Persistance.getDataSource());

            Map<String, Object> map = convertRefdataToMap(refdata);

            Object[] sql = generateInsertSQL("refdata", map);

            // enregistrer refdata
            Object[] output = dao.write(connection, (String) sql[0], (Object[]) sql[1]);

            // recuper id refdata
            int id = (Integer) output[0];

            // fermer connexion
            dao.closeConnection(connection);

            // afficher message confirmation
            pushMessage("Refdata enregistrée avec id=" + id, Brick.BRICK_MSG);
        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

    }

    protected void count(RefData refdata)
    {
        log.debug("count");

        Dao dao = null;

        Connection connexion = null;

        setSize(0);

        List<Object> paramList = new LinkedList<Object>();

        try
        {

            // init requete
            String req = "select count(*) ";

            req = req + " from refdata d where (1=1) ";

            // id
            if (
                refdata.getId() != 0
            )
            {
                req = req + " and (d.id=?) ";

                paramList.add(refdata.getId());
            }

            // code
            refdata.setCode(Persistance.oterBlancs(refdata.getCode()));

            if (
                refdata.getCode().equals("") == false
            )
            {
                req = req + " and (d.code=?) ";

                paramList.add(refdata.getCode());
            }

            // parent_id
            if (
                refdata.getParentId() != 0
            )
            {
                req = req + " and (d.parent_id=?) ";

                paramList.add(refdata.getParentId());
            }

            // obtenir une reference dao
            dao = Persistance.getDao();

            // ouvrir connexion
            connexion = dao.openConnection(Persistance.getDataSource());

            // lancer requete
            Object[] params = new Object[paramList.size()];

            paramList.toArray(params);

            WorkUnit workunit = dao.read(connexion, req, params);

        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

        finally
        {
            if (
                dao != null
            )
            {
                if (
                    connexion != null
                )
                {
                    try
                    {
                        // fermer connexion
                        dao.closeConnection(connexion);
                    }

                    catch (Exception e)
                    {
                        log.error(e);
                    }

                }
            }
        }
    }

    public void find(RefData refdata)
    {
        log.debug("find");

        // clear refdata fields (except id)
        refdata.setActId(0);

        refdata.setCode("");

        refdata.setName("");

        refdata.setParentId(0);

        refdata.setStateCode("");

        // db query setup
        Connection connexion = null;

        Dao dao = Persistance.getDao();

        try
        {

            // init requete
            String sql = "select id, code, state_code, parent_id, act_id, state_time from refdata where id=? ";

            // ouvrir connexion
            connexion = dao.openConnection(Persistance.getDataSource());

            // lancer requete
            WorkUnit workUnit = dao.read(connexion, sql, new Object[]
            { refdata.getId() });

            // pacourir resultats
            for (Iterator<Model> modelIter = workUnit.getRows().iterator(); modelIter.hasNext();)
            {
                Model row = modelIter.next();

                extractRefData(refdata, workUnit, row);

            }

        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

        finally
        {
            if (
                dao != null
            )
            {
                if (
                    connexion != null
                )
                {
                    try
                    {
                        // fermer connexion
                        dao.closeConnection(connexion);
                    }

                    catch (Exception e)
                    {
                        log.error(e);
                    }

                }
            }
        }

    }

    public List<RefData> fetchRefData(String sql, Object[] params)
    {
        log.debug("fetch");

        List<RefData> refdataSet = new LinkedList<RefData>();

        try
        {

            Dao dao = Persistance.getDao();

            // ouvrir connexion
            Connection connexion = dao.openConnection(Persistance.getDataSource());

            // lancer requete
            WorkUnit workUnit = dao.read(connexion,
                    "select id, code, state_code, parent_id, act_id, state_time from refdata order by id", new Object[]
                    {});

            // pacourir resultats
            for (Iterator<Model> modelIter = workUnit.getRows().iterator(); modelIter.hasNext();)
            {
                Model row = modelIter.next();

                RefData refdata = new RefData();

                extractRefData(refdata, workUnit, row);

                refdataSet.add(refdata);
            }

            // fermer connexion
            dao.closeConnection(connexion);
        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

        return refdataSet;
    }

    public List<RefData> search(RefData refdata)
    {
        log.debug("search");

        List<RefData> refdataSet = new LinkedList<RefData>();

        List<Object> paramList = new LinkedList<Object>();

        // conserver parametres

        // init requete
        // setInput("");

        try
        {
            // comptage
            // TODO count();

            // init requete
            String req = "select r.id, r.code, r.state_code, r.class_id, ";
            req = req + " r.parent_id, r.act_id, p.id ";
            req = req + " from refdata r ";
            req = req + " left outer join refdata p on p.id=r.parent_id ";
            req = req + " left outer join act a on a.id=r.act_id ";
            req = req + " where (1=1) ";

            // id
            if (
                refdata.getId() != 0
            )
            {
                req = req + " and (r.id=?) ";

                paramList.add(refdata.getId());
            }

            // code
            refdata.setCode(Persistance.oterBlancs(refdata.getCode()));

            if (
                refdata.getCode().equals("") == false
            )
            {
                req = req + " and (p.code=?) ";

                paramList.add(refdata.getCode());
            }

            // parent_id
            if (
                refdata.getParentId() != 0
            )
            {
                req = req + " and (r.parent_id=?) ";

                paramList.add(refdata.getParentId());
            }

            // tri
            req = req + " order by r.id ";

            // conserver requete sql (sans pagination)
            // setInput(req);

            Dao dao = Persistance.getDao();

            // ouvrir connexion
            Connection connexion = dao.openConnection(Persistance.getDataSource());

            // lancer requete
            Object[] params = new Object[paramList.size()];

            paramList.toArray(params);

            WorkUnit workUnit = dao.read(connexion, req + " limit " + getLimit() + " offset " + getOffset(), params);

            // pacourir resultats
            for (Iterator<Model> modelIter = workUnit.getRows().iterator(); modelIter.hasNext();)
            {
                Model row = modelIter.next();

                RefData instance = new RefData();

                extractRefData(instance, workUnit, row);

                refdataSet.add(refdata);
            }

            // fermer connexion
            dao.closeConnection(connexion);
        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

        return refdataSet;
    }

    public void delete(RefData refdata)
    {
        log.debug("delete");

        try
        {
            validateDelete(null);

            if (
                peekNotice().getName() != null
            )
            {
                return;
            }

            Dao dao = Persistance.getDao();

            // ouvrir connexion
            Connection connection = dao.openConnection(Persistance.getDataSource());

            // supprimer personne
            dao.write(connection, "delete from refdata r where id=? ", new Object[]
            { refdata.getId() });

            // fermer connexion
            dao.closeConnection(connection);

            // afficher message confirmation
            pushMessage("Refdata supprimée", Brick.BRICK_MSG);
        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

    }

    public void modify(RefData refdata)
    {
        log.debug("modify");

        try
        {
            validateModify(null);

            if (
                peekNotice().getName() != null
            )
            {
                return;
            }

            Dao dao = Persistance.getDao();

            // ouvrir connexion
            Connection connection = dao.openConnection(Persistance.getDataSource());

            StringBuffer sql = new StringBuffer("update refdata set ");

            sql.append("code=?, class_id=? where id=? ");

            Object[] inputs = new Object[]
            { refdata.getCode(), refdata.getClassId(), refdata.getId() };

            // enregistrer refdata

            dao.write(connection, sql.toString(), inputs);

            // fermer connexion
            dao.closeConnection(connection);

            // afficher message confirmation
            pushMessage("Refdata modifiée", Brick.BRICK_MSG);
        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

    }

    public void reset()
    {
        log.debug("reset");

    }

    public void refresh()
    {
        log.debug("refresh");
    }

    @Override
    public void goFirst()
    {
        log.debug("goFirst");

        setOffset(0);

        // TODO fetch();
    }

    @Override
    public void goLast()
    {
        log.debug("goLast");

        if (
            (getSize() - getLimit()) >= 0
        )
        {
            setOffset(getSize() - getLimit());

            // TODO fetch();
        }
    }

    @Override
    public void goNext()
    {
        log.debug("goNext");

        if (
            getOffset() + getLimit() <= getSize()
        )
        {
            setOffset(getOffset() + getLimit());
        } else
        {
            setOffset(getOffset() - getLimit());

            if (
                getOffset() < 0
            )
            {
                setOffset(0);
            }
        }

        // TODO fetch();
    }

    @Override
    public void goPrevious()
    {
        log.debug("goPrevious");

        if (
            getOffset() - getLimit() >= 0
        )
        {
            setOffset(getOffset() - getLimit());
        } else
        {
            setOffset(0);
        }

        // TODO fetch();
    }

    public void load(RefData refdata)
    {
        log.debug("load");

        Connection connexion = null;

        Dao dao = Persistance.getDao();

        RefData instance = null;

        Object[] params = new Object[1];

        try
        {
            params[0] = refdata.getId();

            // init requete
            String req = "select r.id, r.code, r.class_id, r.state_code from refdata r where r.id=? ";

            dao = Persistance.getDao();

            // ouvrir connexion
            connexion = dao.openConnection(Persistance.getDataSource());

            // lancer requete
            WorkUnit workUnit = dao.read(connexion, req, params);

            // parcourir resultats
            for (Iterator<Model> modelIter = workUnit.getRows().iterator(); modelIter.hasNext();)
            {
                Model row = modelIter.next();

                extractRefData(refdata, workUnit, row);
            }

        }

        catch (Exception e)
        {
            log.error(e);

            pushMessage(e);
        }

        finally
        {
            // fermer connexion
            if (
                connexion != null
            )
            {
                try
                {
                    dao.closeConnection(connexion);
                }

                catch (Exception e)
                {
                    log.error(e);
                }
            }

            connexion = null;
        }

    }

    public void extractRefData(RefData refdata, WorkUnit workUnit, Model row)
    {
        log.debug("extractRefData");

        // id
        int index = workUnit.getNameIndex("id");

        Object value = null;

        value = row.getValue(index);

        if (
            value != null
        )
        {
            refdata.setId((Integer) value);
        }

        // code

        index = workUnit.getNameIndex("code");

        value = row.getValue(index);

        if (
            value != null
        )
        {
            refdata.setCode((String) value);
        }

        // state_code
        index = workUnit.getNameIndex("state_code");

        value = row.getValue(index);

        if (
            value != null
        )
        {
            refdata.setStateCode((String) value);
        }

        // parent_id
        index = workUnit.getNameIndex("parent_id");

        value = row.getValue(index);

        if (
            value != null
        )
        {
            refdata.setParentId((Integer) value);
        }

    }

    public void validateStore(Object in)
    {
        log.debug("validateStore");

        boolean error = false;

        RefData refdata = (RefData) in;

        // contrôle code
        if (
            refdata.getCode() == null || refdata.getCode().equals("")
        )
        {
            Brick notice = new Brick("Le nom doit être renseigné", Brick.BRICK_ERROR);

            notices.push(notice);

            error = true;
        }

        // TODO contrôle classe

        // empiler message d'erreur (non enregistrement)
        if (
            error == true
        )
        {
            Brick notice = new Brick("Refdata cannot be stored", Brick.BRICK_ERROR);

            notices.push(notice);
        }

    }

    @Override
    public void validateModify(Object in)
    {
        log.debug("validateModify");

        validateStore(null);
    }

    @Override
    public void validateDelete(Object in)
    {
        log.debug("validateDelete");

        // TODO
    }

}
