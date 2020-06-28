package ControllerMowers.facade.impl;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;


public enum CardinalPointEnum
{
    NORTH(1, "N"),
    EAST(2, "E"),
    SOUTH(3, "S"),
    WEST(4, "W");

    private int numberCardinalPoint;
    private String codCardinalPoint;

    private CardinalPointEnum(final int numberCardinalPoint, final String codCardinalPoint)
    {
        this.numberCardinalPoint = numberCardinalPoint;
        setCodCardinalPoint(codCardinalPoint);
    }

    public int getNumberCardinalPoint()
    {
        return numberCardinalPoint;
    }

    public static Optional<CardinalPointEnum> valueOf(final int value)
    {
        return Arrays.stream(values())
            .filter(cardinalPoint -> cardinalPoint.numberCardinalPoint == value)
            .findFirst();
    }

    public static Optional<CardinalPointEnum> valueOfByCodCardinalPoint(final String value)
    {
        return Arrays.stream(values())
            .filter(cardinalPoint -> StringUtils.equals(cardinalPoint.codCardinalPoint, value))
            .findFirst();
    }

    public String getCodCardinalPoint()
    {
        return codCardinalPoint;
    }

    public void setCodCardinalPoint(final String codCardinalPoint)
    {
        this.codCardinalPoint = codCardinalPoint;
    }
}
