README.md
Сoffee-orders

Сoffee-orders - back-end часть CRM системы для кофейни. Система устроена таким образом, что заказ хранится в базе в виде цепочки событий (event sourcing). 
Причины по котором был выбран такой подход - гибкость в плане дальнейшего разбиения заказа на этапы, что, например, необходимо для анализа трудозатрат и выявления проблем в скорости обслуживания клиентов.

Сoffee-orders представляет из себя  CQRS — архитектуру, в которой операции чтения отделены от операций записи:
- Booking service - сервис создания заказов, который включает в себя CRUD операции с пользователями, работниками и заказами.
Переход заказов из стадии ЗАРЕГИСТРИРОВАН в стадию В РАБОТЕ (когда работник освобождается) и из стадии В РАБОТЕ в стадию ГОТОВ (учитывается время приготовления заказа) осуществляется при помощи генерации событий,
реализуемых с помощью класса Timer.   
- Order service - сервис для операций чтения заказов, взаимодействующий с сервисом создания заказов.
- Две базы данных.
