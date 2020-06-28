package ControllerMowers;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class Application
{
    private static ApplicationContext context;

    public static void main(final String[] args)
    {
        System.out.println("INFO: STARTING CONTROLLER MOWERS APPLICATION.");

        runSpringBootApplication(args);

        System.out.println("INFO: END STARTING CONTROLLER MOWERS APPLICATION.");

    }

    private static void runSpringBootApplication(final String[] args)
    {
        try
        {
            // load additional static YML file so we can split the configuration
            final String[] newArgs = Arrays.copyOf(args, args.length + 1);
            newArgs[args.length] = "--spring.config.additional-location=classpath:/static.yml";

            context = SpringApplication.run(Application.class, args);
        }
        catch (final Exception exception)
        {
            exitApplicationMowerWithErrors();
        }
    }

    private static void exitApplicationMowerWithErrors()
    {
        System.out.println("ERROR: CONTROLLER MOWERS APPLICATION HAS BEEN FINISHED WITH ERRORS");
        SpringApplication.exit(context);
    }

}
