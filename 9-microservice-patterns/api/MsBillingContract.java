package ru.user.microservice.contract;

import feign.Response;



/**
 * Интерфейс взаимодействия с сервисом биллинга
 */
public interface MsBillingContract {

    /**
     * Списать деньги с баланса пользователя
     */
    BillingServiceResponse withDrawMoneyFromUserBalance(@RequestBody MsWithdrawRequest request) throws Exception;

}