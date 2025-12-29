@echo off
:: Minimal gradlew.bat stub - tries to call gradle if installed
where gradle >nul 2>nul
if %ERRORLEVEL%==0 (
  gradle %*
) else (
  echo Gradle not found on PATH. If you want to use the wrapper, ensure gradle/wrapper/gradle-wrapper.jar exists or install Gradle.
  echo You can also run the project from IDE (Run CrmRestApplication).
  exit /b 1
)
