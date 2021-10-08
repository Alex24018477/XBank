package com.alex.xbank.service;

import org.apache.commons.validator.EmailValidator;

public class Utils {
    public static boolean isValidEmailAddress(String email) {//класс Email-валидатор из библиотеки apache.commons, которая подключена в зависимостях
        return EmailValidator.getInstance().isValid(email);
    }
}
