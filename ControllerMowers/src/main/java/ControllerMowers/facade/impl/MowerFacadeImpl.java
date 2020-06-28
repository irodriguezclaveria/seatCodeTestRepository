package ControllerMowers.facade.impl;

import static ControllerMowers.facade.impl.CardinalPointEnum.NORTH;
import static ControllerMowers.facade.impl.CardinalPointEnum.WEST;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ControllerMowers.domain.Mower;
import ControllerMowers.domain.Plateau;
import ControllerMowers.domain.Position;
import ControllerMowers.error.ControllerMowerException;
import ControllerMowers.facade.MowerFacade;
import ControllerMowers.service.MowerService;
import ControllerMowers.service.PositionService;


@Component
public class MowerFacadeImpl implements MowerFacade
{
    private static final String MSG_ERROR_NO_PLATEAU_AVAILABLE = "ERROR!!! IN THIS MOMENT WE DON'T HAVE ANY ";
    private static final String MSG_ERROR_ANY_MOWER_AVAILABLE = "ERROR!!! IN THIS MOMENT WE DON'T HAVE ANY ";

    private static final int MIN_POSITION_X_Y = -1;

    @Autowired
    private MowerService mowerService;

    @Autowired
    private PositionService positionService;

    @Override
    public void sendCommandMower(final String moveDirection) throws ControllerMowerException
    {
        final Mower mower = mowerService.getAvailableMower().orElseThrow(() -> new ControllerMowerException(MSG_ERROR_ANY_MOWER_AVAILABLE));

        final Plateau plateau =
            mower.getPlateau().orElseThrow(() -> new ControllerMowerException(MSG_ERROR_NO_PLATEAU_AVAILABLE));

        final Position currentPosition = mower.getPosition() != null ? mower.getPosition() : initPositionMower(mower);

        final Optional<MovementsEnum> optionalMoveEnums = MovementsEnum.valueOfByCodeMovement(moveDirection);

        if (optionalMoveEnums.isPresent())
        {
            switch (optionalMoveEnums.get())
            {
                case MOVER_FORWARD:

                    moveMower(currentPosition, plateau);
                    break;

                case TURN_RIGHT:
                    turnRight(currentPosition);
                    break;

                case TURN_LEFT:
                    turnLeft(currentPosition);
                    break;
            }

            positionService.save(currentPosition);
            mowerService.checkStatusAndSaveMower(mower);

            System.out
                .println(StringUtils.join("OUTPUT --> The end position of mower: ", mower.getCodMower(), ";", currentPosition.getPositionX(), ";",
                    currentPosition.getPositionY(), ";", currentPosition.getCadinalPoint()));
        }
        else
        {
            System.out.println(StringUtils.join("ERROR: THE KEYWORD INTRODUCED ", moveDirection,
                " IS INCORRECT, CORRECT KEYWORDS -> M|L|R or Number Number N|S|E|W"));
        }
    }

    @Override
    public void sendPositionMower(final Integer x, final Integer y, final String cardinalPoint) throws ControllerMowerException
    {
        final Mower mower = mowerService.getAvailableMower().orElseThrow(() -> new ControllerMowerException(MSG_ERROR_ANY_MOWER_AVAILABLE));

        final Plateau plateau =
            mower.getPlateau().orElseThrow(() -> new ControllerMowerException(MSG_ERROR_NO_PLATEAU_AVAILABLE));
        final int maxPositionX = plateau.getSizeX();
        final int maxPositionY = plateau.getSizeY();

        final Optional<CardinalPointEnum> cardinalPointEnumOptional = CardinalPointEnum.valueOfByCodCardinalPoint(cardinalPoint);

        if (x < maxPositionX && (y == null || y < maxPositionY))
        {
            final Position position =
                mower.getPosition() != null ? mower.getPosition() : initPositionMower(mower);
            position.setPositionX(x);

            if (y != null)
            {
                position.setPositionY(y);
            }

            if (cardinalPointEnumOptional.isPresent())
            {
                position.setCadinalPoint(cardinalPointEnumOptional.get().toString());
            }
            positionService.save(position);
            mowerService.checkStatusAndSaveMower(mower);

            System.out
                .println(
                    StringUtils.join("OUTPUT --> The end position of mower: ", mower.getCodMower(), ";", position.getPositionX(), ";",
                        position.getPositionY(), ";", position.getCadinalPoint()));

        }
        else
        {
            System.out.println("THE MOWER CAN NOT MOVE IN THAT DIRECTION");
        }

    }

    private void moveMower(final Position position, final Plateau plateau)
    {
        final int maxPositionX = plateau.getSizeX();
        final int maxPositionY = plateau.getSizeY();

        switch (CardinalPointEnum.valueOf(position.getCadinalPoint()))
        {
            case EAST:
                moveForwardX(position, maxPositionX, +1);
                break;

            case NORTH:
                moveForwardY(position, maxPositionY, +1);
                break;
            case WEST:
                moveForwardX(position, MIN_POSITION_X_Y, -1);
                break;
            case SOUTH:
                moveForwardY(position, MIN_POSITION_X_Y, -1);
                break;
        }
    }

    private void moveForwardX(final Position position, final Integer maxPosX, final int movement)
    {
        final Integer currentPositionX = position.getPositionX() + movement;
        if (currentPositionX == maxPosX)
        {
            System.out.println("THE MOWER CAN NOT MOVE IN THAT DIRECTION");
        }
        else
        {
            position.setPositionX(currentPositionX);
        }
    }

    private void moveForwardY(final Position position, final Integer maxPosY, final int movement)
    {
        final Integer currentPositionY = position.getPositionY() + movement;

        if (currentPositionY == maxPosY)
        {
            System.out.println("THE MOWER CAN NOT MOVE IN THAT DIRECTION");
        }
        else
        {
            position.setPositionY(currentPositionY);
        }

    }

    private void turnRight(final Position position) throws ControllerMowerException
    {
        final CardinalPointEnum currentCardinalPoint = CardinalPointEnum.valueOf(position.getCadinalPoint());

        if (currentCardinalPoint.equals(WEST))
        {
            position.setCadinalPoint(NORTH.toString());
        }
        else
        {
            position.setCadinalPoint(getNextCardinalPoint(currentCardinalPoint, +1).toString());

        }
    }

    private void turnLeft(final Position position) throws ControllerMowerException
    {
        final CardinalPointEnum currentCardinalPoint = CardinalPointEnum.valueOf(position.getCadinalPoint());

        if (currentCardinalPoint.equals(NORTH))
        {
            position.setCadinalPoint(WEST.toString());
        }
        else
        {

            position.setCadinalPoint(getNextCardinalPoint(currentCardinalPoint, -1).toString());

        }
    }

    private CardinalPointEnum getNextCardinalPoint(final CardinalPointEnum currentCardinalPoint, final int movement) throws ControllerMowerException
    {

        return CardinalPointEnum.valueOf(currentCardinalPoint.getNumberCardinalPoint() + movement)
            .orElseThrow(() -> new ControllerMowerException(MSG_ERROR_ANY_MOWER_AVAILABLE));
    }

    private Position initPositionMower(final Mower mower)
    {

        final Position position = new Position();
        position.setPositionX(0);
        position.setPositionY(0);
        position.setCadinalPoint(NORTH.toString());
        position.setMower(mower);

        return position;
    }

}
