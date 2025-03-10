# Simple-Microservices-Architecture  
## Simple Architecture of Auth service  
\
\
Basic microservices-based application using Spring Boot to understand how microservices interact. \
The project will consist of three main services: \
1.	Auth Service – Handles user authentication and issues JWT tokens. \
2.	API Gateway – Routes requests to appropriate services and performs authentication checks. \
3.	Service 1 (You can decide what it does, e.g., a simple product service). \
4. \
5. #📜 Аутентификация с помощью JWT в микросервисной архитектуре

📖 ##Введение

В современных приложениях, где используется микросервисная архитектура, важно безопасно проверять, кто делает запросы к нашим сервисам. Для этого часто используется JWT — JSON Web Token.

JWT — это специальная строка, которая содержит информацию о пользователе (например, его имя), а также подпись, которая подтверждает, что токен не был изменён.

🏗️ ##Как это работает?

Авторизация

Пользователь вводит логин и пароль.

Сервер проверяет данные и создаёт JWT.

Токен отправляется пользователю.

Аутентификация

При последующих запросах клиент отправляет токен в заголовке: Authorization: Bearer <token>.

Сервер проверяет токен, и если он валиден — даёт доступ к данным.

🔒 ##Разбор кода

Вот пример кода для аутентификации с JWT:

String token = authHeader.substring(7); // убираем "Bearer "
try {
Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody(); // проверка токена
String username = claims.getSubject(); // получаем имя пользователя из токена
Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
SecurityContextHolder.getContext().setAuthentication(authentication); // устанавливаем пользователя в контекст безопасности
filterChain.doFilter(request, response); // передаём запрос дальше
} catch (Exception e) {
response.setStatus(HttpStatus.UNAUTHORIZED.value()); // ошибка — отправляем 401
}

📌 ##Пояснение:

authHeader.substring(7) — убираем "Bearer " из начала строки, чтобы получить сам токен.

Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody() — проверка токена и получение данных (claims) из него.

claims.getSubject() — получаем имя пользователя, закодированное в токене.

UsernamePasswordAuthenticationToken — создаём объект аутентификации для Spring Security.

SecurityContextHolder.getContext().setAuthentication(authentication) — сохраняем пользователя в текущий контекст безопасности.

filterChain.doFilter(request, response) — пропускаем запрос дальше по цепочке фильтров.

response.setStatus(HttpStatus.UNAUTHORIZED.value()) — если токен невалидный, возвращаем ошибку 401 (неавторизовано).

🧩 ##Как это связано с микросервисами?

В микросервисной архитектуре каждый сервис может проверять JWT независимо:

Сервис аутентификации создаёт JWT.

Другие сервисы принимают запросы с токеном и проверяют его с помощью общего секретного ключа (или публичного ключа, если используется асимметричная криптография).

Такой подход позволяет не хранить сессии на каждом сервисе и минимизировать связь между ними.

🚀 ##Заключение

JWT — это простой и удобный способ аутентификации в распределённых системах. Если вам нужно безопасно проверять пользователей в микросервисах, JWT — ваш выбор!
  
