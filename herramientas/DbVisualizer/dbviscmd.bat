@echo off

rem Script to launch the command line interface for DbVisualizer

set DBVIS_HOME=%~dp0
set JAVA_EXEC=java

set CP=%DBVIS_HOME%\resources
set CP=%CP%;%DBVIS_HOME%\lib\*

rem Extract the version string
for /f "tokens=3" %%g in ('%JAVA_EXEC% -version 2^>^&1 ^| findstr /i "version"') do (
    set VERSION_STRING=%%g
)

rem Remove the double-quotes
set VERSION_STRING=%VERSION_STRING:"=%

rem Form JAVA_VER from major and minor number
for /f "delims=. tokens=1-3" %%v in ("%VERSION_STRING%") do (
    set JAVA_VER=%%v%%w
)

set JAVA_MAJOR_VERSION=%VERSION_STRING:~0,1%

rem Java arguments
set VM_ARGS=-Xmx768M 
set COMMON_ARGS=-Dsun.locale.formatasdefault=true -Djava.awt.headless=true -Ddbvis.home="%DBVIS_HOME%." -cp "%CP%"

set JAVA_ARGS=%VM_ARGS%
IF "%JAVA_MAJOR_VERSION%"=="9" (
    set JAVA_ARGS=%VM_ARGS% "@%DBVIS_HOME%\java9-args" %COMMON_ARGS%
) ELSE (
    set JAVA_ARGS=%VM_ARGS% %COMMON_ARGS%
)

%JAVA_EXEC% %JAVA_ARGS% com.onseven.dbvis.DbVisualizerCmd %*
