@echo off
cd build
java -cp .;..\vendors\jSSC-2.8.0-Release\jssc.jar pkg/Server/LaunchServer
pause