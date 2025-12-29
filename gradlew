#!/usr/bin/env sh
# Minimal gradlew stub - will try to use existing gradle on PATH or instruct user.
DIR="$(cd "$(dirname "$0")" && pwd)"
if command -v gradle >/dev/null 2>&1; then
  exec gradle "$@"
else
  echo "Gradle not found on PATH. If you want to use the wrapper, please ensure gradle-wrapper.jar exists in gradle/wrapper or install Gradle."
  echo "You can run the project from IDE (Run 'CrmRestApplication') or install Gradle."
  exit 1
fi
