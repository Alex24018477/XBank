package com.alex.xbank.retrievers;

import com.alex.xbank.json.Rates;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.alex.xbank.json.Rate;

//класс предназначен для получения данных со сторонних серверов

@Component
public class RateRetriever {

    private static final String URL = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";//сервис для получения данных в формате json

    @Cacheable("rates")// @Cacheable кеширует на какоето время полученные данные в Map, и ключом к этим данным является параметр "rates"
    public Rate[] getRate() {//метод десиарилизирует обьект Json в обьект Java
        RestTemplate restTemplate = new RestTemplate();//спринговый http клиент который позволяет очень легко делать запросы на сторонние сервисы и десиарилизирует полученый обьект Json в обьект Java
        ResponseEntity<Rate[]> response = restTemplate.getForEntity(URL, Rate[].class);//создаем обьект в который ложим ответ запроса на URL, преобразованный в обьект типа Rate
        return response.getBody();//из response получаем обьект типа Rate в виде json
    }
}
