package com.alex.xbank.config;

import com.alex.xbank.UserRole;
import com.alex.xbank.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration//конфигурационный класс
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration {

    public static final String ADMIN = "admin";

    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean//бин который преобразует пароли в хешы по протоколу BCrypt, которые будут сохранятся в базе
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean//бин которий выполняется сразу после запуска приложения
    public CommandLineRunner demo(final UserService userService,
                                  final PasswordEncoder encoder) {
        return new CommandLineRunner() {

            @Override
            public void run(String... strings) throws Exception {
                userService.addUser("xbank", "xbank", "admin", encoder.encode("password"), UserRole.ADMIN, "alex24018477@gmail.com", "068222222222");//на старте приложения добавляем админа, с обработанным паролем и ролью "админ"

                for (int i = 0; i < 10; i++) {
                    userService.addUser("Вася"+i, "Пупкин"+i, "user"+i,
                            encoder.encode("password"),
                            UserRole.USER, "alex"+i+"@gmail.com", "0681111111"+i);
                }
            }
        };
    }
}
