package ControllerMowers.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ControllerMowers.domain.Position;
import ControllerMowers.repository.PositionRepository;
import ControllerMowers.service.PositionService;


@Service
public class PositionServiceImpl implements PositionService
{
    @Autowired
    private PositionRepository positionRepository;

    @Override
    public void save(final Position position)
    {
        positionRepository.save(position);
    }

}
