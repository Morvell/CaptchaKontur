**Captcha сервис**
===================


Версия 1.0 - Snapshot

Автор: Захаров Андрей (mrmorvell@gmail.com)

----------


Описание
----------

Данное приложение является реализацией Rest - сервиса 
для генерации и валидации captcha

----------


Требования
-------------------

- Java 8
- Maven

----------

Состав
-------------
- Контроллер: CaptchaController.java
- Реализации генерации captcha: CaptchaMaker.java
- Класс для хранения необходимой информации о captcha: Captcha.java

----------

Предоставляемые методы
----------

- /get - можно получить в теле запроса Base64 captcha. 
 В заголовке запроса имеет 2 дополнительных параметра id и answer.
 
- /post - необходимо отправить 2 заголовка id и answer для подтверждения captcha.


----------

Прицип реализации CaptchaMaker
-------------
- капча состоит из символов латинского алфавита и цифр
- капча имеет переменную длину 5-7 символов.
- расстояние между символами так же варьируется
- расположение символов по вертикали варьируется
- наклон символов варьируется от -30 до  30 градусов
- имеет 4 генерируемые полосы 
----------

Прицип реализации CaptchaController
-------------
- /get генерирует капчу и UUID, записывает данные в Map<(String)UUID, Captcha>.
Добавляет в заголовок Http id = (String)UUID, answer = разгадка капчи
- /post проверяет на все необходимые условия. Если ответ совпал, или не совпал, или истек
лимит жизни капчи = 1 минуте, то капча удаляется из словаря. Вместе с удаление конкретных данных
удаляются и все другие данные с истекшим сроком жизни
- так же реализован @Scheduled метод, запусающий чистку каждый час  
----------

Запуск
-------------
- Собирается с помощью maven
- запускается java -jar captcha-1.0-SNAPSHOT.jar
----------


