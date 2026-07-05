#!/bin/bash

cd /Users/Abhi/Desktop/MacPilot/server

exec ./venv/bin/uvicorn main:app --host 0.0.0.0 --port 8000q