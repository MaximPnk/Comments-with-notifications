**Запуск:**
1) Запустить приложение
2) В Postman использовать указанные endpoint'ы

**Тестирование**

1) В тестах запустить класс CommentControllerTest

**Endpoints:**

1) Комментарии:
    1) Получить все:
        1) [GET] http://localhost:8189/api/comments
        2) [GET] http://localhost:8189/api/comments?page=1
    2) Получить один:
        1) [GET] http://localhost:8189/api/comments/1
    3) Добавить:
        1) [POST] http://localhost:8189/api/comments
            Body: My comment
           
2) Уведомления:
    1) Получить все:
        1) [GET] http://localhost:8189/api/notifications
        2) [GET] http://localhost:8189/api/notifications?page=1
    2) Получить одно:
        1) [GET] http://localhost:8189/api/notifications/1
                