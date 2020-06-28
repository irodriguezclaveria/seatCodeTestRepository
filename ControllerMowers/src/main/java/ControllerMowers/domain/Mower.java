package ControllerMowers.domain;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MOWER")
public class Mower
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "COD_MOWER")
    private String codMower;

    @Column(name = "LIVE_TIME")
    private Integer liveTime;

    @OneToOne(mappedBy = "mower")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "COD_PLATEAU")
    private Plateau plateau;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(final String status)
    {
        this.status = status;
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(final Position position)
    {
        this.position = position;
    }

    public Optional<Plateau> getPlateau()
    {
        return Optional.ofNullable(plateau);
    }

    public void setPlateau(final Plateau plateau)
    {
        this.plateau = plateau;
    }

    public String getCodMower()
    {
        return codMower;
    }

    public void setCodMower(final String codMower)
    {
        this.codMower = codMower;
    }

    public Integer getLiveTime()
    {
        return liveTime;
    }

    public void setLiveTime(final Integer liveTime)
    {
        this.liveTime = liveTime;
    }

}
