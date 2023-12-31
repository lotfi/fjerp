package org.fjerp.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "compte")
@PrimaryKeyJoinColumn(name = "id")
public class Compte extends Maitre
{
    @Column(name = "type_compte", length = 1, nullable = false)
    private String typeCompte = ""; // general, analytique

    @Column(name = "flag_mouv", length = 1, nullable = false)
    private String flagMouv; // chapeau/groupe, detail

    public String getTypeCompte()
    {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte)
    {
        this.typeCompte = typeCompte;
    }

    public String getFlagMouv()
    {
        return flagMouv;
    }

    public void setFlagMouv(String flagMouv)
    {
        this.flagMouv = flagMouv;
    }

}
