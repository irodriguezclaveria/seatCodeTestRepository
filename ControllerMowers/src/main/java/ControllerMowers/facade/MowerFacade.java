package ControllerMowers.facade;

import ControllerMowers.error.ControllerMowerException;


public interface MowerFacade
{
    void sendCommandMower(String moveDirection) throws ControllerMowerException;

    void sendPositionMower(Integer x, Integer y, String cardinalPoint) throws ControllerMowerException;
}
