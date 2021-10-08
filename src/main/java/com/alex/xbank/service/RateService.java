package com.alex.xbank.service;

import com.alex.xbank.json.Rate;
import com.alex.xbank.json.Rates;
import com.alex.xbank.retrievers.RateRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService {

    @Autowired
    private RateRetriever rateRetriever;

//    private Rates rates = (Rates) rateRetriever.getRate();

    public Rate rateUSD(){
        return rateRetriever.getRate()[0];
    }

    public  Rate rateEUR(){
        return rateRetriever.getRate()[1];
    }

//    public double findBuy(String ccy) {
//        for (Rate rate : rates.getRates())
//            if (ccy.equalsIgnoreCase(rate.getCcy())) {
//                return rate.getBuy();
//            }
//        return 0;
//    }
//
//    public double findSale(String ccy) {
//        for (Rate rate : rates.getRates())
//            if (ccy.equalsIgnoreCase(rate.getCcy())) {
//                return rate.getSale();
//            }
//        return 0;
//    }

    public String findBaseCcy() {
        return "UAH";
    }

}
