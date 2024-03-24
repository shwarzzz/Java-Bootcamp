0. Выполним пункты из 1 и 2 заданий:
    mkdir target
    cp -R src/resources target/
1. Теперь же нам необходимо скачать библиотеки. Создадим для них директорию:
    mkdir lib
2. Загрузим в нее Jcomander:
    curl -L -o lib/jcommander-1.82.jar https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar
3. Загрузим JCDP:
    curl -L -o lib/JCDP-4.0.2.jar https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar
4. Теперь скомпилируем байт код для наших java файлов
     javac -cp .:./lib/jcommander-1.82.jar:./lib/JCDP-4.0.2.jar -d target/ src/java/edu/school21/printer/*/*.java
Где:
    -cp .:./lib/jcommander-1.82.jar:./lib/JCDP-4.0.2.jar — указывает classpath, который включает в себя текущую
    директорию (.) и две библиотеки, jcommander-1.82.jar и JCDP-4.0.2.jar, расположенные в директории lib.
    Это позволяет компилятору находить классы и ресурсы, которые используются в исходных
    файлах Java, но не находятся в текущей директории
4. Перейти в директорию target
    cd target
5. Распаковать jar файлы в текующую директорию (-C отказывается работать)
    jar -xvf ../lib/jcommander-1.82.jar
    jar -xvf ../lib/JCDP-4.0.2.jar
6. Собрать итоговый проект в jar архив
    cd ..
    jar cmf src/manifest.txt target/images-to-chars-printer.jar -C target/ .
7. Запуск программы
    java -jar target/images-to-chars-printer.jar <white_color> <black_color>