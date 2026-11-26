@echo off
for /f "tokens=5" %%p in ('netstat -ano ^| findstr :6767 ^| findstr LISTENING') do (
    taskkill /PID %%p /F
)
echo Servidor detenido.
pause