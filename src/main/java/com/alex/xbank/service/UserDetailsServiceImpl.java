package com.alex.xbank.service;


import com.alex.xbank.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;



//класс-переходник служит для того чтобы состыковать представление учетной записи  с учетной записью, понятной для Spring Security
@Service
public class UserDetailsServiceImpl implements UserDetailsService {//сервис который реализует спринговый интерфейс UserDetailsService
    @Autowired
    private UserService userService;//инжектим наш сервис

    @Override
    public UserDetails loadUserByUsername(String login)//этот метод будет вызыватся каждый раз когда спрингу нужно узнать по логину информацию о какомто пользователе
            throws UsernameNotFoundException {
        CustomUser customUser = userService.findByLogin(login);//нужно по логину найти пользователя
        if (customUser == null)//если такого пользователя нет - спринг сам обрабатывает UsernameNotFoundException
            throw new UsernameNotFoundException(login + " not found");
//создаем список ролей юзера:
        List<GrantedAuthority> roles =
                Arrays.asList(new SimpleGrantedAuthority(customUser.getRole().toString()));//создаем список типа GrantedAuthority(это спринговый интерфейс), в этот список ложим обьект SimpleGrantedAuthority(реализация интерфейса)
                //параметром которого являтся строковое представление енюморейшн роли юзера

//конвертим юзера в понятный для спринга тип и возвращаем юзера с логином, паролем и списком ролей
        return new User(customUser.getLogin(),
                customUser.getPassword(), roles);
    }
}