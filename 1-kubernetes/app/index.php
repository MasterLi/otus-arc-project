<?php
/**
 * Основная точка входа в приложение. Сюда направляются все запросы (.htaccess).
 *
 * @var $configuration array конфигурация приложения (задается в config.php)
 */

header("Content-Type: application/json");
$response =
    $response = array(
        'status' => 'OK'
    );

echo json_encode($response);
