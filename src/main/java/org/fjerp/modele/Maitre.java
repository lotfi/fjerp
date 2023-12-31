package org.fjerp.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "maitre")
@Inheritance(strategy = InheritanceType.JOINED)
public class Maitre
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", length = 20, nullable = false)
    private String code = "";

    @Column(name = "label", length = 50, nullable = false)
    private String label = "";

    @OneToOne
    @JoinColumn(name = "id_parent", nullable = true)
    private Maitre parent;

    @Column(name = "categ", length = 3, nullable = false)
    private String categ = "";

    public String getCateg()
    {
        return categ;
    }

    public void setCategorie(String categ)
    {
        this.categ = categ;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Maitre getParent()
    {
        return parent;
    }

    public void setParent(Maitre parent)
    {
        this.parent = parent;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setCateg(String categ)
    {
        this.categ = categ;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

}
