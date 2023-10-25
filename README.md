[![Build status](https://ci.appveyor.com/api/projects/status/iq1775aqwyau2433?svg=true)](https://ci.appveyor.com/project/QA-USV/myautomation2)

# Домашнее задание к занятию «1.2. Тестирование API, CI»

## Задача №1: настройка CI

Сделать:   
- клонировать [репозиторий с примерами учебного кода](https://github.com/netology-code/aqa-code/tree/master) во временную папку на локальный компьютер       
- из папки api-ci локальной копии репозитория aqa-code копировать папку rest в папку с учебными проектами      
- открыть папку rest в IDEA как проект Gradle, инициилизировать новый репозиторий   
- настроить для проекта одну из CI на выбор: Appveyor (см. инструкцию ниже) или [Github Actions](../github-actions-integration)   
- добавить файлы проекта в репозиторий, выполнить коммит и пуш   
- удостовериться, что CI показывает, что в сборке прогоняются все автотесты.            

Целевой сервис (SUT — System under test), расположен в файле [app-mbank.jar](app-mbank.jar). Необходимо положить его в каталог `artifacts` проекта, который необходимо создать.

Поскольку файлы с расширением `.jar` находятся в списках `.gitignore`, вам нужно принудительно заставить Git следить за ними: `git add -f artifacts/app-mbank.jar`.

## Задача №2: JSON Schema

JSON Schema предлагает нам инструмент валидации JSON-документов. С описанием вы можете познакомиться по [адресу](https://json-schema.org/understanding-json-schema).

Как строится схема: 
```js
{
  "$schema": "http://json-schema.org/draft-07/schema", // версия схемы: https://json-schema.org/understanding-json-schema/reference/schema.html
  "type": "array", // тип корневого элемента: https://json-schema.org/understanding-json-schema/reference/type.html
  "items": { // какие элементы допустимы внутри массива: https://json-schema.org/understanding-json-schema/reference/array.html#items
    "type": "object", // должны быть объектами: https://json-schema.org/understanding-json-schema/reference/object.html
    "required": [ // должны содержать следующие поля: https://json-schema.org/understanding-json-schema/reference/object.html#required-properties
      "id",
      "name",
      "number",
      "balance",
      "currency"
    ],
    "additionalProperties": false, // дополнительных полей быть не должно 
    "properties": { // описание полей: https://json-schema.org/understanding-json-schema/reference/object.html#properties
      "id": {
        "type": "integer" // целое число: https://json-schema.org/understanding-json-schema/reference/numeric.html#integer
      },
      "name": {
        "type": "string", // строка: https://json-schema.org/understanding-json-schema/reference/string.html
        "minLength": 1 // минимальная длина — 1: https://json-schema.org/understanding-json-schema/reference/string.html#length
      },
      "number": {
        "type": "string", // строка: https://json-schema.org/understanding-json-schema/reference/string.html
        "pattern": "^•• \\d{4}$" // соответствует регулярному выражению: https://json-schema.org/understanding-json-schema/reference/string.html#regular-expressions
      },
      "balance": {
        "type": "integer" // целое число: https://json-schema.org/understanding-json-schema/reference/numeric.html#integer
      },
      "currency": {
        "type": "string" // строка: https://json-schema.org/understanding-json-schema/reference/string.html
      }
    }
  }
}
```

Что нужно сделать:

#### Шаг 1. Добавить зависимость

```groovy
dependencies {
    testImplementation 'io.rest-assured:rest-assured:4.3.0'
    testImplementation 'io.rest-assured:json-schema-validator:4.3.0'
    testImplementation 'org.junit.jupiter:junit-jupiter:5.6.1'
}
```

#### Шаг 2. Сохранить схему в ресурсах

Создать каталог `resources` в `src/test` и поместить туда схему.
![](pic/schema.png)

#### Шаг 3. Включить проверку схемы

Модифицируйте существующий тест так, чтобы он проверял соответствие схеме. Для этого:

```java
      // код теста
      .then()
          .statusCode(200)
          // static import для JsonSchemaValidator.matchesJsonSchemaInClasspath
          .body(matchesJsonSchemaInClasspath("accounts.schema.json"))
      ;
```

Удостоверьтесь, что тесты проходят при соответствии ответа схеме и падают, если вы поменяете что-то в схеме, например, тип для `id`.

#### Шаг 4. Доработать схему

Изучить документацию на тип [`object`](https://json-schema.org/understanding-json-schema/reference/object.html) и найдите способ валидации значения поля на два из возможных значений: «RUB» или «USD».

Доработать схему соответствующим образом, удостовериться, что тесты проходят, в том числе в CI.

Поменять «RUB» на «RUR» в схеме и удостовериться, что тесты падают, в том числе в CI.

Прислать на проверку ссылку на репозиторий. Удостовериться, что в истории сборки были как success, так и fail, иначе будет не видно, как проверяли, что сборка падает в CI.
