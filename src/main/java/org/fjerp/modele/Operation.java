package org.fjerp.modele;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "operation")
public class Operation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code_unite", length = 20, nullable = false)
    private String codeUnite = "";

    @Column(name = "annee")
    private short annee;

    @Column(name = "mois")
    private byte mois;

    @Column(name = "jour")
    private byte jour;

    @Column(name = "piece", length = 10, nullable = false)
    private String piece = "";

    @Column(name = "numero")
    private short numero;

    @Column(name = "code_produit", length = 20, nullable = false)
    private String codeProduit = "";

    @Column(name = "code_tiers", length = 20, nullable = false)
    private String codeTiers = "";

    @Column(name = "quantite")
    private short quantite;

    @Column(name = "compte1", length = 20, nullable = false)
    private String compte1 = "";

    @Column(name = "compte2", length = 20)
    private String compte2 = "";

    @Column(name = "label", length = 50, nullable = false)
    private String label = "";

    @Column(name = "type_mouv", length = 20, nullable = false)
    private String typeMouv = "";

    @Column(name = "date_mouv")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateMouv;

    @Column(name = "date_compt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCompt;

    @Column(name = "codif", length = 20, nullable = false)
    private String codif = "";

    @Column(name = "debit")
    private double debit;

    @Column(name = "credit")
    private double credit;

    @Column(name = "valeur")
    private double valeur;

    @Column(name = "etat", length = 10)
    private String etat = "";

    @Column(name = "sens", length = 1)
    private String sens = "";

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCodeUnite()
    {
        return codeUnite;
    }

    public void setCodeUnite(String codeUnite)
    {
        this.codeUnite = codeUnite;
    }

    public short getAnnee()
    {
        return annee;
    }

    public void setAnnee(short annee)
    {
        this.annee = annee;
    }

    public byte getMois()
    {
        return mois;
    }

    public void setMois(byte mois)
    {
        this.mois = mois;
    }

    public byte getJour()
    {
        return jour;
    }

    public void setJour(byte jour)
    {
        this.jour = jour;
    }

    public String getPiece()
    {
        return piece;
    }

    public void setPiece(String piece)
    {
        this.piece = piece;
    }

    public short getNumero()
    {
        return numero;
    }

    public void setNumero(short numero)
    {
        this.numero = numero;
    }

    public String getCodeProduit()
    {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit)
    {
        this.codeProduit = codeProduit;
    }

    public String getCodeTiers()
    {
        return codeTiers;
    }

    public void setCodeTiers(String codeTiers)
    {
        this.codeTiers = codeTiers;
    }

    public short getQuantite()
    {
        return quantite;
    }

    public void setQuantite(short quantite)
    {
        this.quantite = quantite;
    }

    public String getCompte1()
    {
        return compte1;
    }

    public void setCompte1(String compte1)
    {
        this.compte1 = compte1;
    }

    public String getCompte2()
    {
        return compte2;
    }

    public void setCompte2(String compte2)
    {
        this.compte2 = compte2;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getTypeMouv()
    {
        return typeMouv;
    }

    public void setTypeMouv(String typeMouv)
    {
        this.typeMouv = typeMouv;
    }

    public Date getDateMouv()
    {
        return dateMouv;
    }

    public void setDateMouv(Date dateMouv)
    {
        this.dateMouv = dateMouv;
    }

    public Date getDateCompt()
    {
        return dateCompt;
    }

    public void setDateCompt(Date dateCompt)
    {
        this.dateCompt = dateCompt;
    }

    public String getCodif()
    {
        return codif;
    }

    public void setCodif(String codif)
    {
        this.codif = codif;
    }

    public double getDebit()
    {
        return debit;
    }

    public void setDebit(double debit)
    {
        this.debit = debit;
    }

    public double getCredit()
    {
        return credit;
    }

    public void setCredit(double credit)
    {
        this.credit = credit;
    }

    public double getValeur()
    {
        return valeur;
    }

    public void setValeur(double valeur)
    {
        this.valeur = valeur;
    }

    public String getEtat()
    {
        return etat;
    }

    public void setEtat(String etat)
    {
        this.etat = etat;
    }

    public String getSens()
    {
        return sens;
    }

    public void setSens(String sens)
    {
        this.sens = sens;
    }

}
