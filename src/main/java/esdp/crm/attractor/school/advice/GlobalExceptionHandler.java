package esdp.crm.attractor.school.advice;

import esdp.crm.attractor.school.exception.EmailExistsException;
import esdp.crm.attractor.school.exception.NotFoundException;
import esdp.crm.attractor.school.exception.NotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailExistsException.class)
    protected ModelAndView handleEmailExistsException() {
        return new ModelAndView("exceptionPage")
                .addObject("type", "Регистрация не удалась")
                .addObject("header", "Невозможно зарегистрировать такого пользователя")
                .addObject("description", "Почта с таким именем уже существует");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ModelAndView handle404Error() {
        return new ModelAndView("exceptionPage")
                .addObject("type", "404")
                .addObject("header", "Страница не найдена")
                .addObject("description", "Страница, которую вы ищете, не существует");
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ModelAndView handle403Error() {
        return new ModelAndView("exceptionPage")
                .addObject("type", "403")
                .addObject("header", "Доступ закрыт")
                .addObject("description", "У вас нет доступа к данной странице");
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ModelAndView handleBindException() {
        return new ModelAndView("exceptionPage")
                .addObject("type", "400")
                .addObject("header", "Введены некорректные данные")
                .addObject("description",
                        "Форма заполнена некорректными данными, пожалуйста вернитесь и заполните данные верно");
    }

    @ExceptionHandler(NotPermittedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ModelAndView handleNotPermittedException(NotPermittedException e) {
        return new ModelAndView("exceptionPage")
                .addObject("type", "403")
                .addObject("header", "Доступ закрыт")
                .addObject("description",
                        e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ModelAndView handleNotFoundException(NotFoundException e) {
        return new ModelAndView("exceptionPage")
                .addObject("type", "400")
                .addObject("header", "Данные не найдены")
                .addObject("description",
                        e.getMessage());
    }
}