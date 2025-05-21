@echo off
REM debug_installer.bat - Create and save in your project root directory
REM C:\Users\Luis\Documents\Proyectos\Facturacion\debug_installer.bat

echo Creating installer with debugging options...

REM Set the correct version for the installer (must be numeric for Windows)
set APP_VERSION=1.0.0

REM Build the fat JAR
echo Step 1: Building fat JAR...
call mvn clean package -DskipTests

REM Check if JAR was created successfully
if not exist "target\Facturacion-1.0.1.jar" (
    echo ERROR: JAR file was not created successfully!
    pause
    exit /b 1
)

echo Step 2: Creating installer with debug options...

REM Create installer using jpackage with debug options
jpackage ^
    --input target ^
    --name "Facturacion" ^
    --main-jar Facturacion-1.0.1.jar ^
    --main-class com.luis.facturacion.DiagnosticMain ^
    --type msi ^
    --dest target\installer ^
    --app-version %APP_VERSION% ^
    --vendor "Luis" ^
    --win-dir-chooser ^
    --win-shortcut ^
    --win-menu ^
    --win-menu-group "Facturacion" ^
    --win-console ^
    --java-options "-Dfile.encoding=UTF-8" ^
    --java-options "-Djavafx.verbose=true" ^
    --java-options "-Djava.util.logging.config.file=launcher_log.properties"

if %ERRORLEVEL% EQU 0 (
    echo ======================================================
    echo SUCCESS: Debug installer created successfully!
    echo Location: target\installer\Facturacion-%APP_VERSION%.msi
    echo ======================================================
) else (
    echo ERROR: Failed to create installer!
)

pause