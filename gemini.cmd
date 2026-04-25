@echo off
set "NODE_PATH=%~dp0tmp\node\node-v24.15.0-win-x64"
set "PATH=%NODE_PATH%;%PATH%"
"%NODE_PATH%\gemini.cmd" %*
