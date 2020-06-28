package ControllerMowers.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Plateau
{
    @Id
    @Column(name = "COD_PLATEAU")
    private String codPlateau;

    @Column(name = "SIZE_X")
    private int sizeX;

    @Column(name = "SIZE_Y")
    private int sizeY;

    @Column(name = "ACTIVE")
    private Boolean active;

    public String getCodPlateau()
    {
        return codPlateau;
    }

    public void setCodPlateau(final String codPlateau)
    {
        this.codPlateau = codPlateau;
    }

    public int getSizeX()
    {
        return sizeX;
    }

    public void setSizeX(final int sizeX)
    {
        this.sizeX = sizeX;
    }

    public int getSizeY()
    {
        return sizeY;
    }

    public void setSizeY(final int sizeY)
    {
        this.sizeY = sizeY;
    }

    //is
    public Boolean getActive()
    {
        return active;
    }

    public void setActive(final Boolean active)
    {
        this.active = active;
    }
}
