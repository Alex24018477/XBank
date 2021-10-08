package com.alex.xbank.service;
//сервис работает с репозиторием

import com.alex.xbank.UserRole;
import com.alex.xbank.controllers.MyController;
import com.alex.xbank.entity.CustomUser;
import com.alex.xbank.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired//инжектим репозитоорий
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }//получить всех пользователей из таблици

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {//получить всех пользователей из таблици по логину
        return userRepository.findByLogin(login);
    }


//    @Transactional
//    public void deleteUsers(List<Long> ids) {//удаляем пользователей при этом в параметр передаем список Id-шников тех пользователей которых удаляем
//        ids.forEach(id -> {//проходим циклом по Id-шникам
//            Optional<CustomUser> user = userRepository.findById(id);//findById(id) - метод который по Id-шнику достает ентити из репозитория, "Optional"- специальная заащита от NullPointerExeption
//            user.ifPresent(u -> {//если user не null->
//                if ( ! AppConfig.ADMIN.equals(u.getLogin())) {//проверяем не является ли он админом по логину(чтобы не удалить самого себя)
//                    userRepository.deleteById(u.getId());//удаляем юзера по id
//                }
//            });
//        });
//    }

    @Transactional//добавляем нового пользователя
    public boolean addUser(String firstName, String lastName, String login, String passHash,
                           UserRole role, String email,
                           String phone) {
        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
            return false;
        }
        if (!Utils.isValidEmailAddress(email)) {//если валидация прошла упешно
            return false;
        }
        //проверяем нет ли уже такого юзера в базе по логину
        if (userRepository.existsByLogin(login)) {
            logger.info("User with login " + login + " already existed");
            return false;
        }


        CustomUser customUser = new CustomUser(firstName, lastName, login, passHash, role, email, phone);
        userRepository.save(customUser);//добавляем в базу
        logger.info("User with login " + login + " added");
        return true;
    }

    @Transactional
    public void updateUser(String login, String passHash, String email, String phone) {//обновляем юзера в базе по логину
        CustomUser user = userRepository.findByLogin(login);//ищем юзера в базе по логину
        if (user == null)
            return;

        user.setEmail(email);//устанавливаем даному юзеру новый email
        user.setPhone(phone);//устанавливаем даному юзеру новый phone
        user.setPassword(passHash);//устанавливаем даному юзеру новый passHash

        userRepository.save(user);//сохраняем в базу
    }


}
