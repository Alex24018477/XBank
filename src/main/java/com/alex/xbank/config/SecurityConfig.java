package com.alex.xbank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
@Configuration
@EnableWebSecurity//анотация которая включает фреймворк Spring Security
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired//инжектим бин из UserDetailsServiceImp
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired//этот метод вызовется один раз при старте приложения
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth//вызывается спец.билдер в который передается информация о сервисе через который спринг будет получать информацию о пользователях
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);//и передаем passwordEncoder - для подсчета и сравнения хешей с теми что хранятся в базе
    }

//    //кого, куда и как пускать
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()//отключаем csrf
//                .exceptionHandling()
//                .authenticationEntryPoint(entryPoint())  //
//                .and()
//                .authorizeRequests()  //не пускает пользователей без авторизации
//                .antMatchers("/").permitAll() //в корень пускаем всех (курс валют)
//                .antMatchers("/admin/**").hasRole("ADMIN") //в "/admin" пускаем только администратора, с ролью АДМИН
//                .and()
//                .httpBasic(); // включает Basic-авторизацию через Base64
//    }
//
//    @Bean
//    public AuthenticationEntryPoint entryPoint() {
//        BasicAuthenticationEntryPoint point = new BasicAuthenticationEntryPoint();
//        point.setRealmName("Geo");
//        return point;
//    }

    //кого, куда и как пускать
    @Override
    protected void configure(HttpSecurity http) throws Exception {//метод конфигуратор (паттерн-билдер)
        http//вызывая методы параметра http, мы формируем правила по которым будет проходить авторизация
                .csrf().disable()//отключаем защиту от csrf атак(чтобы не усложнять код)
                .authorizeRequests()//включаем режим при котором ничего нельзя делать пока пользователь не авторизировался
                .antMatchers("/", "/confirmationСode", "/login")//задает енд-поинт и правила по которым туда пускают
                .permitAll()//значит что на енд-поинт "/register" можно зайти всем
                .antMatchers("/admin") //@PreAuthorize   вместо анотации  @PreAuthorize(она хуже)
                    .hasRole("ADMIN")             //значит что на енд-поинт "/admin" можно зайти всем кто зарегистрировался как "ADMIN"
                .antMatchers("/registerNewUser")
                .permitAll()//значит что на енд-поинт "/register" можно зайти всем
                .and()//связывает со следующим блоком в билдере
                .exceptionHandling()//обработка исключений
                .accessDeniedPage("/")//обработка исключений, если пользователь лезет туда куда ему нельзя его автоматически перекинет на енд-поинт "/unauthorized"
                .and()
                .formLogin()//описывает форму через которую нужно проходить авторизацию
                .loginPage("/")//если пользователь питается зайти на страницу для которой нужна авторизация- его автоматически перебрасывает на енд-поинт "/login"
//                .loginProcessingUrl("/j_spring_security_check")//специальный енд-поинт который облуживается спрингом и на который в форме(в login.jsp) должен прийти введенный пользователем логи и пароль в виде Post или Get запроса
//                .failureUrl("/login?error")//специальный Url, куда отправит пользователя если он ввел неправильный логин и пароль
                .usernameParameter("firstName")//название инпута в форме(в login.jsp) в которой пользователь вводит логин
                .passwordParameter("password")//название инпута в форме(в login.jsp) в которой пользователь вводит пароль
//                .permitAll()//значит что логинится можна всем
                .and()
                .logout()
                .permitAll()//значит что логинится можна всем
                .logoutUrl("/logout")//при переходе на енд-поинт пользователя перекинет на "/login?logout"
                .logoutSuccessUrl("/login?logout");
    }
}