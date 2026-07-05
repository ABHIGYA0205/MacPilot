#!/bin/bash

cd /Users/Abhi/Desktop/MacPilot/server

source venv/bin/activate
pip install -r requirements.txt

exec uvicorn main:app --host 0.0.0.0 --port 8000