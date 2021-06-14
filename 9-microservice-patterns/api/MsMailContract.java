package ru.user.microservice.contract;

import feign.Response;



/**
 * Интерфейс взаимодействия с почтовым сервисом
 */
public interface MsMailContract {

    /**
     * Отправить письмо
     */
    MailServiceResponse sendMail(@RequestBody MsSendMailRequest request) throws Exception;

}