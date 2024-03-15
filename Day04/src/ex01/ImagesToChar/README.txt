JAR - архив классов, с его помощью программу легче запускать и переносить. Для его создания нужно:
0. Выполнить первые два пункта из прошлого таска + скопируем директорию resources в target:
    mkdir target
    javac -d target/ src/java/edu/school21/printer/*/*.java
    cp -R src/resources target/
1. Далее выполним сборку архива:
    jar -cmf src/manifest.txt target/images-to-chars-printer.jar -C target/ .
Где:
    cmf — флаг, после которого следует указать путь к файлу манифеста;
    target/images-to-chars-printer.jar — имя архива;
    -C — флаг, который указывает на корневую директорию для архива;
    . — добавляет все в архив;
3. Для запуска же необходимо использовать команду:
    java -jar target/images-to-chars-printer.jar <white_color> <black_color>