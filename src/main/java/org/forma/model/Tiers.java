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
@Table(name = "tiers")
public class Tiers implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    private String code;

    @Column(name = "nom_rs", length = 40)
    private String nom = "";

    @Column(name = "prenom", length = 40)
    private String prenom;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_nomen", insertable = false, updatable = false)
    private Codif nomen;

    @Column(name = "tel1", length = 20)
    private String tel1;

    @Column(name = "tel2", length = 20)
    private String tel2;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "adresse", length = 50)
    private String adresse;

    @Column(name = "web", length = 50)
    private String web;

    @Column(name = "age")
    private int age;

    @Column(name = "sexe")
    private boolean sexe;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_nomen")
    private Tiers parent;

    @Column(name = "comment")
    private String comment;

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

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public Codif getNomen()
    {
        return nomen;
    }

    public void setNomen(Codif nomen)
    {
        this.nomen = nomen;
    }

    public String getTel1()
    {
        return tel1;
    }

    public void setTelephone1(String tel1)
    {
        this.tel1 = tel1;
    }

    public String getTel2()
    {
        return tel2;
    }

    public void setTel2(String tel2)
    {
        this.tel2 = tel2;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public String getWeb()
    {
        return web;
    }

    public void setWeb(String web)
    {
        this.web = web;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public boolean isSexe()
    {
        return sexe;
    }

    public void setSexe(boolean sexe)
    {
        this.sexe = sexe;
    }

    public Tiers getParent()
    {
        return parent;
    }

    public void setParent(Tiers parent)
    {
        this.parent = parent;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
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
