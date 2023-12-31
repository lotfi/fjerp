package org.fjerp.modele;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unite")
@AttributeOverrides(
{ @AttributeOverride(name = "code", column = @Column(name = "code")),
        @AttributeOverride(name = "label", column = @Column(name = "label")),
        @AttributeOverride(name = "parent", column = @Column(name = "parent")) })
public class Unite extends Maitre
{
    @Id
    private String code;

    @Column(name = "type_unite", length = 1, nullable = false)
    private String typeUnite;

    public String getTypeUnite()
    {
        return typeUnite;
    }

    public void setTypeUnite(String typeUnite)
    {
        this.typeUnite = typeUnite;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

}
