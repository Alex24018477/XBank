package com.alex.xbank.controllers;

import com.alex.xbank.UserRole;
import com.alex.xbank.service.MailSender;
import com.alex.xbank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private Logger logger =  LoggerFactory.getLogger(MyController.class);


    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/registration")
    public String registrationForm() {
        System.out.println("-----------------------------------------------------");
        System.out.println(userService.getAllUsers());
        System.out.println("-----------------------------------------------------");
        return "registration.html";
    }

    @RequestMapping("/registerNewUser")
    public String input(@RequestParam String firstName,
                        @RequestParam String lastName,
                        @RequestParam String login,
                        @RequestParam String password,
                        @RequestParam String email,
                        @RequestParam String phone) {
        String passHash = passwordEncoder.encode(password);//считаем хеш от пароля

        if (!userService.addUser(firstName, lastName, login, passHash, UserRole.USER, email, phone)) {//пытаемся добавить нового юзера по параметрам которые прилетели
            logger.info("user " + firstName + " wasn't create");
            return "registration.html";//загружаем страницу register.jsp, на которую передаем атрибуты
        }

        userService.addUser(firstName, lastName, login, passHash, UserRole.USER, email, phone);//добавляем нового юзера(нужно сделать проверку)

        System.out.println("-----------------------------------------------------");
        System.out.println(userService.getAllUsers());
        System.out.println("-----------------------------------------------------");

//        mailSender.send(email, "Код підтвердження", "1234");
        return "login_confirmation.html";
    }


//    User user = getCurrentUser();//юзер, которого мы создавали в сервисе-переходнике
//
//    String login = user.getUsername();//определяем логин
//    CustomUser dbUser = userService.findByLogin(login);//по логину извлекаем из базы пользователя вцелом


    @GetMapping("/login")
    public String input(@RequestParam String login,
                        @RequestParam String password) {
        String passHash = passwordEncoder.encode(password);//считаем хеш от пароля
        if (userService.findByLogin(login) != null) {
            System.out.println(userService.findByLogin(login));
            if (userService.findByLogin(login).getPassword().equals(passHash)) {
                System.out.println("passHashpassHashpassHashpassHashpassHashpassHashpassHash");
                return "user_page.html";
            }
//            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//            System.out.println(userService.findByLogin(login).getPassword());
//            System.out.println(passHash);
//            System.out.println(password);
//            System.out.println("passwordpasswordpasswordpasswordpasswordpasswordpassword");
//            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        }
        return "index.html";
    }

    @GetMapping("/confirmationСode")
    public String verification(@RequestParam String code) {
        if (code.length() < 4) {
            return "login_confirmation.html";
        }
        return "index.html";
    }

//    @RequestMapping(value = "/newuser", method = RequestMethod.POST)//регистрация нового пользователя по параметрам которые прилетают
//    public String update(@RequestParam String login,
//                         @RequestParam String password,
//                         @RequestParam(required = false) String email,//required = false - не обязательные параметры
//                         @RequestParam(required = false) String phone,
//                         Model model) {
//        String passHash = passwordEncoder.encode(password);//считаем хеш от пароля
//
//        if (!userService.addUser(login, passHash, UserRole.USER, email, phone)) {//пытаемся добавить нового юзера по параметрам которые прилетели
//            model.addAttribute("exists", true);//если условие false, добавляем атрибуты
//            model.addAttribute("login", login);
//            model.addAttribute("password", passHash);
//            return "register";//загружаем страницу register.jsp, на которую передаем атрибуты
//        }
//
//        //если условие true:
//        return "redirect:/";//перезагружаем страницу register.jsp, которая отрисует  <p>User already exists!</p>
//    }


    private User getCurrentUser() {//возвращает юзера под которым мы зашли в приложение, которого мы создавали в сервисе-переходнике
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
