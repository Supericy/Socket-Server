mkdir build

cd src

javac -d ..\build -cp .;..\vendors\jSSC-2.8.0-Release\jssc.jar pkg\Server\*.java
javac -d ..\build -cp .;..\vendors\jSSC-2.8.0-Release\jssc.jar pkg\Client\*.java

pause