
INSERT INTO document (title, contents, writer, document_bytes, generate_time, uuid)
VALUES
    ('titleA', '내용A', '작성자1', 100, '2024-01-01T10:00:00', UUID()),
    ('titleB', '내용B', '작성자2', 200, '2024-01-02T10:00:00', UUID()),
    ('titleC', '내용C', '작성자3', 300, '2024-01-03T10:00:00', UUID()),
    ('titleD', '내용D', '작성자1', 400, '2024-01-04T10:00:00', UUID()),
    ('titleE', '내용E', '작성자2', 500, '2024-01-05T10:00:00', UUID()),
    ('titleA1', '내용A1', '작성자1', 100, '2024-01-01T10:00:00', UUID()),
    ('titleB1', '내용B1', '작성자2', 200, '2024-01-02T10:00:00', UUID()),
    ('titleC1', '내용C1', '작성자31', 300, '2024-01-03T10:00:00', UUID()),
    ('titleD1', '내용D1', '작성자11', 400, '2024-01-04T10:00:00', UUID()),
    ('titleE1', '내용E1', '작성자21', 500, '2024-01-05T10:00:00', UUID());

INSERT INTO admin(login_id, password)
VALUES
    ('adminGood', 'helloWorld!!')
