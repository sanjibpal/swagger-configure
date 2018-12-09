# swagger-configure
Swagger Spring Auto Configuration

To build the application, run the following command.

```
$ ./gradlew clean build
```

If you are using Windows, then run the following command

```
$ gradlew.bat clean build
```

When you run the full build using `./gradlew clean build` then REST API docs are packaged as well.

For local development you may run

```
$ java -Djava.security.egd=file:/dev/./urandom -jar store/build/libs/store.jar
```

You can see the documentation running at [http://localhost:9086/product/swagger-ui.html](http://localhost:9086/product/swagger-ui.html).

## Modules

| Module Name      | Application or Library | Description                                      | Port |
| ---------------- | ---------------------- | ------------------------------------------------ | ---- |
| store            | Application            | Provides the Product APIs                        | 9086 |
| core             | Library                | Core utility shared across projects              | NA   |
