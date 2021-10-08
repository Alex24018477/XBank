package com.alex.xbank.controllers;

import com.alex.xbank.entity.CustomUser;
import com.alex.xbank.json.Rate;
import com.alex.xbank.retrievers.RateRetriever;
import com.alex.xbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RateController {

    @Autowired
    private RateRetriever rateRetriever;
    @Autowired
    private UserService userService;


    @RequestMapping("/rate_USD")
    public Rate buyUSD() {
        return rateRetriever.getRate()[0];
    }

    @RequestMapping("/rate_EUR")
    public Rate buyEUR() {
        return rateRetriever.getRate()[1];
    }

    @RequestMapping("/name_user")
    public CustomUser customUser() {
        User user = getCurrentUser();//юзер, которого мы создавали в сервисе-переходнике

        String login = user.getUsername();//определяем логин
        CustomUser dbUser = userService.findByLogin(login);//по логину извлекаем из базы пользователя вцелом
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println(SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal());
//        System.out.println(userService.findByLogin("user4"));
//        System.out.println("-----------------------------------------------------------------");

        return dbUser;

    }

    private User getCurrentUser () {//возвращает юзера под которым мы зашли в приложение, которого мы создавали в сервисе-переходнике
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
