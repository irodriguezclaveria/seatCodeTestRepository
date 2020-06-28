package ControllerMowers.service;

import java.util.Optional;

import ControllerMowers.domain.Mower;


public interface MowerService
{
    Optional<Mower> getAvailableMower();

    void checkStatusAndSaveMower(Mower mower);
}
