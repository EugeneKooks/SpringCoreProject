package ua.epam.spring.hometask.service;

import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Discount;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

public interface DiscountService {

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     * 
     * @param user
     *            User that buys tickets. Can be <code>null</code>
     * @param event
     *            Event that tickets are bought for
     * @param airDateTime
     *            The date and time event will be aired
     * @param seat
     *            Seat that user buys
     * @param tickets
     *            Tickets that user buys
     * @return discount object
     */
    Discount getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long seat, NavigableSet<Long> tickets);

    /**
     * Get all registered discount strategies
     * @return set of discount strategies
     */
    Set<DiscountStrategy> getDiscountStrategies();

    /**
     * Register discount strategies
     * @param discountStrategies set of discount strategies
     */
    void setDiscountStrategies(@Nonnull Set<DiscountStrategy> discountStrategies);
}
