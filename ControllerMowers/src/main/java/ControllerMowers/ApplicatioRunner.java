package ControllerMowers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ControllerMowers.error.ControllerMowerException;
import ControllerMowers.facade.MowerFacade;


@Component
@Profile("!test")
public class ApplicatioRunner implements CommandLineRunner
{

    @Autowired
    private MowerFacade mowerFacade;

    @Override
    public void run(final String... args) throws Exception
    {
        System.out.println("INTRODUCE THE COMMAND THAT YOU WANT TO SEND TO MOWER -> M|L|R or Number Number N|S|E|W");

        final Scanner scanner = new Scanner(System.in);

        String line = "";

        while ((line = scanner.nextLine()) != null)
        {
            if (!checkCommand(line))
            {
                System.out.println("ERROR: THE KEYWORD INTRODUCED IS INCORRECT, CORRECT KEYWORDS -> M|L|R or Number Number N|S|E|W");

            }
        }

        scanner.close();

    }

    private boolean checkCommand(final String line) throws ControllerMowerException
    {

        if (!StringUtils.isEmpty(line))
        {
            if (StringUtils.contains(line, " ") || (line.length() == 1 && NumberUtils.isParsable(line)))
            {
                final List<String> listWithPositions = Arrays.asList(line.split("")).stream().filter(x -> !StringUtils.equals(x, " "))
                    .collect(Collectors.toList());

                if (listWithPositions.size() > 3)
                {
                    return false;

                }
                else
                {
                    final Integer positionY = listWithPositions.size() > 1 ? NumberUtils.toInt(listWithPositions.get(1)) : null;
                    final String cardinalPoint = listWithPositions.size() > 2 ? listWithPositions.get(1) : null;

                    mowerFacade.sendPositionMower(NumberUtils.toInt(listWithPositions.get(0)), positionY, cardinalPoint);
                }
            }
            else
            {
                Arrays.asList(line.split(""))
                    .forEach(w -> {
                        try
                        {
                            mowerFacade.sendCommandMower(w);
                        }
                        catch (final ControllerMowerException e)
                        {
                            throw new RuntimeException(e);
                        }
                    });

            }

        }
        else
        {
            return false;

        }

        return true;
    }

}
