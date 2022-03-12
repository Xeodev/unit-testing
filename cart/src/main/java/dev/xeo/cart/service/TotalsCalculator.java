package dev.xeo.cart.service;

import dev.xeo.cart.model.CartItem;
import dev.xeo.cart.model.CartTotals;

import java.util.List;
import java.util.stream.Collector;

public class TotalsCalculator {

	public CartTotals getTotals(List<CartItem> items) {
		return items.stream()
			.collect(
				Collector.of(
					CartTotals::new,
					CartTotals::sumItem,
					CartTotals::combine
				)
			);
	}
}
