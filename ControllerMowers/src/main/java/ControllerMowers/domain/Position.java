package ControllerMowers.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "POSITION")
public class Position
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "POSITION_X")
    private Integer positionX;

    @Column(name = "POSTION_Y")
    private Integer positionY;

    @Column(name = "CARDINAL_POINT")
    private String cadinalPoint;

    @OneToOne()
    @JoinColumn(name = "ID_MOWER", referencedColumnName = "ID")
    private Mower mower;

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public Integer getPositionX()
    {
        return positionX;
    }

    public void setPositionX(final Integer positionX)
    {
        this.positionX = positionX;
    }

    public Integer getPositionY()
    {
        return positionY;
    }

    public void setPositionY(final Integer positionY)
    {
        this.positionY = positionY;
    }

    public String getCadinalPoint()
    {
        return cadinalPoint;
    }

    public void setCadinalPoint(final String cadinalPoint)
    {
        this.cadinalPoint = cadinalPoint;
    }

    public Mower getMower()
    {
        return mower;
    }

    public void setMower(final Mower mower)
    {
        this.mower = mower;
    }

}
