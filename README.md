Магические числа:
    В классе ElasticSearchConfig
        new HttpHost("localhost", 9200, "http"))
    В классе JedisConfig
        jedisConnectionFactory.setPort(9999)
    В классе EmailService
        helper.setFrom("geek-market@gb.ru")
    В классе ProxyProductService
        if (productList.isEmpty() || (System.currentTimeMillis() - updateTime) > 600000L)
Цифры и текст можно заменить  именованными константами.

Жесткое кодирование
    В файле application.yaml
        url: jdbc:h2:file:/home/andrey/IntellijWorkPlace/spring-boot-market-algorythm/src/main/resources/db/market-db;MODE=PostgreSQL
Заменить /home/andrey/ на ~/

Фактор невероятности
    В классе FileSystemStorageService
        Path destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename()))
Заменить на Path destinationFile = rootLocation.resolve(Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
и сделать обработчик исключения
