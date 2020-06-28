package ControllerMowers.facade;

import static ControllerMowers.facade.impl.CardinalPointEnum.EAST;
import static ControllerMowers.facade.impl.CardinalPointEnum.NORTH;
import static ControllerMowers.facade.impl.MovementsEnum.MOVER_FORWARD;
import static ControllerMowers.facade.impl.MovementsEnum.TURN_LEFT;
import static ControllerMowers.facade.impl.MovementsEnum.TURN_RIGHT;
import static ControllerMowers.facade.impl.MowerStatusEnum.STARTED;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ControllerMowers.Application;
import ControllerMowers.domain.Mower;
import ControllerMowers.domain.Position;
import ControllerMowers.repository.MowerRepository;
import ControllerMowers.service.MowerService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
public class MowerFacadeTest
{

    private static final int MAX_NUMBER_MOWERS_WORKING = 1;
    private static final int INITIAL_POSITION_X = 0;
    private static final int INITIAL_POSITION_Y = 0;

    @Autowired
    private MowerFacade mowerFacade;

    @Autowired
    private MowerService mowerService;

    @Autowired
    private MowerRepository mowerRepository;

    @Test
    @Order(1)
    public void moveMowerForward() throws Exception
    {
        mowerFacade.sendCommandMower(MOVER_FORWARD.getCodeMovement());

        final List<Mower> mowerList = mowerRepository.findMowersByStatus(STARTED.toString());
        assertEquals(mowerList.size(), MAX_NUMBER_MOWERS_WORKING);

        final Position position = mowerList.get(0).getPosition();

        assertEquals(position.getCadinalPoint(), NORTH.toString());
        assertEquals(position.getPositionX(), INITIAL_POSITION_X);
        assertEquals(position.getPositionY(), INITIAL_POSITION_Y + 1);

    }

    @Test
    @Order(2)
    public void moveMowerRight() throws Exception
    {
        mowerFacade.sendCommandMower(TURN_RIGHT.getCodeMovement());

        final List<Mower> mowerList = mowerRepository.findMowersByStatus(STARTED.toString());

        assertEquals(mowerList.size(), MAX_NUMBER_MOWERS_WORKING);

        final Position position = mowerList.get(0).getPosition();

        assertEquals(position.getCadinalPoint(), EAST.toString());
        assertEquals(position.getPositionX(), INITIAL_POSITION_X);
        assertEquals(position.getPositionY(), INITIAL_POSITION_Y + 1);

    }

    @Test
    @Order(3)
    public void moveMowerLeft() throws Exception
    {
        mowerFacade.sendCommandMower(TURN_LEFT.getCodeMovement());

        final List<Mower> mowerList = mowerRepository.findMowersByStatus(STARTED.toString());

        assertEquals(mowerList.size(), MAX_NUMBER_MOWERS_WORKING);

        final Position position = mowerList.get(0).getPosition();

        assertEquals(position.getCadinalPoint(), NORTH.toString());
        assertEquals(position.getPositionX(), INITIAL_POSITION_X);
        assertEquals(position.getPositionY(), INITIAL_POSITION_Y + 1);
    }

    @Test
    @Order(4)
    public void canNotMowerForward() throws Exception
    {
        final Optional<Mower> mowerOptional = mowerService.getAvailableMower();

        Mower mower = mowerOptional.get();

        final int currentPositionY = mower.getPosition().getPositionY();
        final int sizeYField = mower.getPlateau().get().getSizeX();

        for (int i = currentPositionY; i < sizeYField; i++)
        {
            mowerFacade.sendCommandMower(MOVER_FORWARD.getCodeMovement());
        }

        final List<Mower> mowerList = mowerRepository.findMowersByStatus(STARTED.toString());
        mower = mowerList.get(0);

        assertEquals(mower.getPosition().getPositionY(), mower.getPlateau().get().getSizeY() - 1);
    }

    @Test
    public void moveCorrectPosition() throws Exception
    {
        final Optional<Mower> mowerOptional = mowerService.getAvailableMower();

        Mower mower = mowerOptional.get();

        final int sizeYField = mower.getPlateau().get().getSizeY() - 1;
        final int sizeXField = mower.getPlateau().get().getSizeX() - 1;

        mowerFacade.sendPositionMower(sizeYField, sizeXField, EAST.getCodCardinalPoint());

        final List<Mower> mowerList = mowerRepository.findMowersByStatus(STARTED.toString());
        mower = mowerList.get(0);

        assertEquals(sizeYField, mower.getPosition().getPositionX());
        assertEquals(sizeXField, mower.getPosition().getPositionY());
        assertEquals(EAST.toString(), mower.getPosition().getCadinalPoint());
    }

    @Test
    public void moveIncorrectPositionX() throws Exception
    {
        mowerFacade.sendPositionMower(INITIAL_POSITION_X, INITIAL_POSITION_Y, NORTH.getCodCardinalPoint());

        final Optional<Mower> mowerOptional = mowerService.getAvailableMower();

        Mower mower = mowerOptional.get();

        final int sizeYField = mower.getPlateau().get().getSizeY() - 1;
        final int sizeXField = mower.getPlateau().get().getSizeX() + 1;

        mowerFacade.sendPositionMower(sizeYField, sizeXField, EAST.toString());

        final List<Mower> mowerList = mowerRepository.findMowersByStatus(STARTED.toString());
        mower = mowerList.get(0);

        assertEquals(INITIAL_POSITION_X, mower.getPosition().getPositionX());
        assertEquals(INITIAL_POSITION_Y, mower.getPosition().getPositionY());
        assertEquals(NORTH.toString(), mower.getPosition().getCadinalPoint());

    }

    @Test
    public void moveIncorrectCardinalPoint() throws Exception
    {
        mowerFacade.sendPositionMower(INITIAL_POSITION_X, INITIAL_POSITION_Y, NORTH.getCodCardinalPoint());

        final Optional<Mower> mowerOptional = mowerService.getAvailableMower();

        Mower mower = mowerOptional.get();

        final int sizeYField = mower.getPlateau().get().getSizeY() - 1;
        final int sizeXField = mower.getPlateau().get().getSizeX() + 1;

        mowerFacade.sendPositionMower(sizeYField, sizeXField, "INCORRECT_CARDINAL_POINT");

        final List<Mower> mowerList = mowerRepository.findMowersByStatus(STARTED.toString());
        mower = mowerList.get(0);

        assertEquals(INITIAL_POSITION_X, mower.getPosition().getPositionX());
        assertEquals(INITIAL_POSITION_Y, mower.getPosition().getPositionY());
        assertEquals(NORTH.toString(), mower.getPosition().getCadinalPoint());

    }
}
