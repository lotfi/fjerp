package org.forma.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "codif")
public class Codif implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    private String code = "";

    @Column(name = "categorie", length = 20)
    private String categorie = "";

    @Column(name = "label", length = 50)
    private String label = "";

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_parent")
    private Codif parent;

    @Column(name = "etat", length = 10)
    private String etat = "";

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnreg;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCategorie()
    {
        return categorie;
    }

    public void setCategorie(String categorie)
    {
        this.categorie = categorie;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Codif getParent()
    {
        return parent;
    }

    public void setParent(Codif parent)
    {
        this.parent = parent;
    }

    public String getEtat()
    {
        return etat;
    }

    public void setEtat(String etat)
    {
        this.etat = etat;
    }

    public Date getDateEnreg()
    {
        return dateEnreg;
    }

    public void setDateEnreg(Date dateEnreg)
    {
        this.dateEnreg = dateEnreg;
    }

}
