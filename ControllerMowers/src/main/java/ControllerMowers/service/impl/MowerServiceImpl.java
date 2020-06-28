package ControllerMowers.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ControllerMowers.domain.Mower;
import ControllerMowers.facade.impl.MowerStatusEnum;
import ControllerMowers.repository.MowerRepository;
import ControllerMowers.service.MowerService;


@Service
public class MowerServiceImpl implements MowerService
{
    @Autowired
    private MowerRepository mowerRepository;

    @Override
    public Optional<Mower> getAvailableMower()
    {
        final List<Mower> listStartedMower = mowerRepository.findMowersByStatus(MowerStatusEnum.STARTED.toString());

        if (listStartedMower.isEmpty())
        {
            final List<Mower> listStoppetMowers = mowerRepository.findMowersByStatus(MowerStatusEnum.STOPPED.toString());

            return listStoppetMowers.isEmpty() ? Optional.empty() : startMower(listStoppetMowers.get(0));
        }
        else
        {
            return Optional.of(listStartedMower.get(0));
        }

    }

    @Override
    public void checkStatusAndSaveMower(final Mower mower)
    {
        final int restOfLives = mower.getLiveTime() - 1;

        if (restOfLives == 0)
        {
            System.out.println("THIS HAS BEEN THE LAST TRIP OF THAT MOWER");
            mower.setStatus(MowerStatusEnum.FINISHED.toString());
        }

        mower.setLiveTime(restOfLives);
        mowerRepository.save(mower);
    }

    private Optional<Mower> startMower(final Mower mower)
    {
        mower.setStatus(MowerStatusEnum.STARTED.toString());
        return Optional.of(mower);
    }
}
