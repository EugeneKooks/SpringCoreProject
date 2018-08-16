package ua.epam.spring.hometask.service.impl.discountstrategies;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ua.epam.spring.hometask.domain.Discount;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.DiscountStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

public class MultipleDiscount implements DiscountStrategy {

    private int discountMultiplier;
    private byte discountRate;
    private String discountReason;

    public MultipleDiscount() {
        this(10, (byte) 50);
    }

    public MultipleDiscount(int discountMultiplier, byte discountRate) {
        this.discountMultiplier = discountMultiplier;
        this.discountRate = discountRate;
        discountReason = String.format("User bought %ds ticket", discountMultiplier);
    }

    @Nullable
    @Override
    public Discount calculateDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime time, long seat, NavigableSet<Long> tickets) {
        int notDiscountedUserTickets = user != null
                ? user.getTickets().size() % discountMultiplier
                : 0;
        List<Long> ticketList = new ArrayList<>(tickets);
        int startIndex = discountMultiplier - notDiscountedUserTickets - 1;
        for (int i = startIndex; i < ticketList.size(); i += discountMultiplier) {
            if (ticketList.get(i).equals(seat)) {
                return new Discount(discountReason, discountRate);
            }
        }
        return null;
    }
}
