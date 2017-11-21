package com.singletonapps;


import com.singletonapps.pricefinder.BestPriceFinder;

public class BasicRunner {

    public static void main(String[] args) {

        new BasicFutureExample().executeSimpleFuture();
        new BestPriceFinder().findBestPrice("PlayStation");


    }
}
