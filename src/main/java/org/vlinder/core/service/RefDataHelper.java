package org.vlinder.core.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vlinder.core.model.RefData;

public class RefDataHelper
{
    private final static Logger log = LogManager.getLogger(RefDataHelper.class);

    public static final RefData REFDATA_NONE = new RefData("none");

    private static final Map<Integer, List<RefData>> ENTRIES = new HashMap<Integer, List<RefData>>();

    private static void addEntries(int classId, String[] codes, Object[] values)
    {
        log.debug("addEntries");

        List<RefData> dataset = new LinkedList<RefData>();

        for (int i = 0; i < codes.length && i < values.length; i++)
        {
            RefData refdata = new RefData();

            refdata.setCode(codes[i]);
            refdata.setClassId(classId);

            dataset.add(refdata);
        }

        ENTRIES.put(classId, dataset);
    }

    static
    {

        /*
         * // states addEntries(RefData.CLASSID_STATE, new String[]
         * {"None","Active","Inactive","Deleted"}, new String[]
         * {"None","Active","Inactive","Deleted"} );
         * 
         * // actions addEntries(RefData.CLASSID_ACTION, new String[]
         * {"Login","Logout","Store","Modify", "Delete", "Search",
         * "Export","Import","View","Grant","Revoke","Admin"}, new String[]
         * {"Login","Logout","Store","Modify", "Delete", "Search",
         * "Export","Import","View","Grant","Revoke","Admin"} );
         * 
         * // partners addEntries(RefData.CLASSID_PARTNER, new String[]
         * {"UN","CI","VD","CO","EM"}, new String[]
         * {"undefined","client","vendor","consultant", "employee"});
         * 
         * // events addEntries(RefData.CLASSID_EVENT, new String[]
         * {"None","Information","Quote","Order","Shipment","Delivery","Payment",
         * "Invoicing","Billing","Claim","Closing"}, new String[] {""});
         * 
         * // classId/master // TODO addEntries(RefData.CLASSID_MASTER, new String[]
         * {"None","Time","Period","Measure Unit","Currency","Distance","State","Event",
         * "Partner","Action","Message","Profile","Stakeholder"}, new String[]
         * {"None","Time","Period","Measure Unit","Currency","Distance","State","Event",
         * "Partner","Action","Message"}); addEntries(RefData.CLASSID_MASTER, new
         * String[] {"Aucun","Temps","Monnaie"}, new String[]
         * {"Aucun","Temps","Monnaie"});
         */
    }

    public static RefData findRefData(int classId, String code)
    {
        log.debug("findRefData");

        RefData refdata = REFDATA_NONE;

        if (
            code != null
        )
        {
            List<RefData> dataset = findRefData(classId);

            for (Iterator<RefData> codeIterator = dataset.iterator(); codeIterator.hasNext();)
            {
                refdata = codeIterator.next();

                if (
                    refdata.getCode().equals(code)
                )
                {
                    return refdata;
                }
            }
        }

        return refdata;
    }

    public static List<RefData> findRefData(int classId)
    {
        log.debug("findCodes");

        List<RefData> dataset = ENTRIES.get(classId);

        return dataset;
    }

    public static RefData findRefData(int classId, int id)
    {
        log.debug("findRefData");

        RefData found = null;

        List<RefData> entries = ENTRIES.get(classId);

        for (Iterator<RefData> refdataIterator = entries.iterator(); refdataIterator.hasNext();)
        {
            RefData refdata = refdataIterator.next();

            if (
                refdata.getId() == id
            )
            {
                return refdata;
            }
        }

        return found;
    }

    public static RefData[] getMasters()
    {
        log.debug("getMasters");

        List<RefData> dataset = null;

        // findRefData(RefData.CLASSID_MASTER);

        RefData[] refdataArray = new RefData[dataset.size()];

        dataset.toArray(refdataArray);

        return refdataArray;

    }
}
