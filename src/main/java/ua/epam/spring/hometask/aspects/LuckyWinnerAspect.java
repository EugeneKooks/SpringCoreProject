package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Ticket;
import java.util.Random;
import java.util.Set;

@Aspect
@Component
public class LuckyWinnerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LuckyWinnerAspect.class);
    private static final double LUCKY_RATE = 0.8;

    private Random random;

    public LuckyWinnerAspect(Random random) {
        this.random = random;
    }

    public LuckyWinnerAspect() {
        random = new Random();
    }

    @Before("execution(* ua.epam.spring.hometask.service.BookingService.bookTickets(..)) && args(tickets)")
    public void checkLucky(Set<Ticket> tickets) {
        tickets.stream()
                .filter(t -> t.getUser() != null)
                .filter(t -> random.nextDouble() > LUCKY_RATE)
                .forEach(ticket -> {
                            ticket.getUser()
                                    .addAdditionalInformation(String.format("Lucky ticket %d. Free of charge.", ticket.getSeat()));
                            logger.info("User {} bought lucky ticket {}", ticket.getUser(), ticket.getSeat());
                        });
    }

}