from fastapi import FastAPI
import subprocess

app = FastAPI()

@app.get("/")
def home():
    return {"status": "MacPilot Server Running"}

@app.post("/lock")
def lock_mac():
    subprocess.run([
        "osascript",
        "-e",
        'tell application "System Events" to keystroke "q" using {control down, command down}'
    ])
    return {"message": "Mac Locked"}

import psutil

@app.get("/status")
def status():
    battery = psutil.sensors_battery()

    return {
        "device": "MacBook Pro",
        "battery": battery.percent if battery else None,
        "charging": battery.power_plugged if battery else None,
        "cpu": psutil.cpu_percent(interval=1),
        "ram": psutil.virtual_memory().percent
    }