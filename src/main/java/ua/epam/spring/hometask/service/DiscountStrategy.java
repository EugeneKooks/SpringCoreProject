package ua.epam.spring.hometask.service;

import java.time.LocalDateTime;
import java.util.NavigableSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Discount;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;

public interface DiscountStrategy {

    /**
     * Calculate discount based on input parameters
     * @param user
     *            User that buys tickets. Can be <code>null</code>
     * @param event
     *            Event that tickets are bought for
     * @param time
     *            The date and time event will be aired
     * @param seat
     *            Seat that user buys
     * @param tickets
     *            Tickets that user buys
     * @return discount value in % or null if discount can't be applied
     */
    @Nullable Discount calculateDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime time, long seat, NavigableSet<Long> tickets);
}
