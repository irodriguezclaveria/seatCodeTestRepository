package ControllerMowers.facade.impl;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;


public enum MovementsEnum
{
    MOVER_FORWARD("M"),
    TURN_LEFT("L"),
    TURN_RIGHT("R");

    private final String codeMovement;

    MovementsEnum(final String codeMovement)
    {
        this.codeMovement = codeMovement;
    }

    public String getCodeMovement()
    {
        return codeMovement;
    }

    public static Optional<MovementsEnum> valueOfByCodeMovement(final String value)
    {
        return Arrays.stream(values())
            .filter(code -> StringUtils.equals(code.getCodeMovement(), value))
            .findFirst();
    }
}
