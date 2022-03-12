package dev.xeo.cart.service;

import dev.xeo.cart.model.CartItem;
import dev.xeo.cart.model.CartTotals;
import dev.xeo.cart.model.User;
import dev.xeo.cart.model.UserType;

import java.util.List;

public class TotalsWithDiscountCalculator extends TotalsCalculator {

	private final User user;

	public TotalsWithDiscountCalculator(User user) {
		this.user = user;
	}

	@Override
	public CartTotals getTotals(List<CartItem> items) {
		CartTotals totals = super.getTotals(items);

		if (user.getUserType() == UserType.PLATINUM) {
			totals = new CartTotals(totals.getSubtotal() * 0.9, totals.getTaxes());
		}

		double credit = user.getCredit();
		if (credit > 0) {
			if (credit > totals.getSubtotal()) {
				user.setCredit(credit - totals.getSubtotal());
				totals = new CartTotals(0, totals.getTaxes());
			} else {
				user.setCredit(0);
				totals = new CartTotals(totals.getSubtotal() - credit, totals.getTaxes());
			}
		}

		return totals;
	}
}
