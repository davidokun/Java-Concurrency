package com.singletonapps.pricefinder;

import java.util.concurrent.Future;

public interface Shopping {

    double getPrice(String product);
    Future<Double> getPriceAsync(String product);
    Future<Double> getPriceSupplyAsync(String product);
    double calculatePrice(String product);
    void delay();
}
