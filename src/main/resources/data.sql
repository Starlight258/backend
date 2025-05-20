INSERT INTO Document (title, contents, writer, documentBytes, generateTime)
VALUES
    ('리브', 'Kotlin is a modern programming language...', '폴라', 2048, '2024-03-13 10:00:00'),
    ('폴라', 'Spring Boot makes it easy to create stand-alone...', '리브', 3072, '2024-03-12 15:30:00'),
    ('메이슨', 'JPA (Java Persistence API) is a powerful tool...', '토다리', 5120, '2024-03-11 08:45:00'),
    ('토다리', 'A well-designed REST API is easy to use...', '쿠키', 4096, '2024-03-10 14:20:00'),
    ('쿠키', 'Microservices architecture is a design approach...', '메이슨', 8192, '2024-03-09 11:10:00');

INSERT INTO Admin(loginId, password)
VALUES ('adminGood', 'helloWorld!!');
