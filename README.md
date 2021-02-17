**Endpoints:**

1) Comments:
    1) Get comments:
        1) [GET] http://localhost:8189/api/comments
        2) [GET] http://localhost:8189/api/comments?page=1
    2) Add comment:
        1) [POST] http://localhost:8189/api/comments
            Body: { "text": "Comment text" }
           
2) Notifications:
    1) Get notifications:
        1) [GET] http://localhost:8189/api/notifications
        2) [GET] http://localhost:8189/api/notifications?page=1
                