from fastapi import FastAPI, Form
import subprocess
import psutil
from pathlib import Path
from pydantic import BaseModel
from fastapi.responses import FileResponse
import tempfile
import os
from fastapi import Header, HTTPException
from dotenv import load_dotenv


load_dotenv()

API_KEY = os.getenv("API_KEY")


class CommandRequest(BaseModel):
    command: str

class VolumeRequest(BaseModel):
    value: int

class VolumeRequest(BaseModel):
    value: int
app = FastAPI()


def verify_api_key(x_api_key: str = Header(None)):
    if x_api_key != API_KEY:
        raise HTTPException(
            status_code=401,
            detail="Invalid API Key"
        )


class ClipboardRequest(BaseModel):
    text: str

@app.get("/")
def home():
    return {"status": "MacPilot Server Running"}



# ----------------------------
# SYSTEM STATUS
# ----------------------------
@app.get("/status")
def status(
    x_api_key: str = Header(None)
):

    verify_api_key(x_api_key)

    battery = psutil.sensors_battery()

    return {
        "device": "MacBook Pro",
        "battery": battery.percent if battery else None,
        "charging": battery.power_plugged if battery else None,
        "cpu": psutil.cpu_percent(interval=1),
        "ram": psutil.virtual_memory().percent
    }



# ----------------------------
# OPEN FILE / FOLDER
# ----------------------------
@app.post("/open")
def open_file(path: str = Form(...)):

    print("========== OPEN REQUEST ==========")
    print(path)
    print("==================================")

    try:

        subprocess.run(
            ["open", path],
            check=True,
            capture_output=True,
            text=True
        )

        print("Opened Successfully")

        return {
            "success": True
        }

    except Exception as e:

        print(e)

        return {
            "success": False,
            "error": str(e)
        }
    


@app.get("/clipboard")
def get_clipboard(
    x_api_key: str = Header(None)
):

    verify_api_key(x_api_key)

    try:

        result = subprocess.run(
            ["pbpaste"],
            capture_output=True,
            text=True
        )

        return {
            "success": True,
            "text": result.stdout
        }

    except Exception as e:

        return {
            "success": False,
            "error": str(e)
        }
    
@app.post("/clipboard")
def set_clipboard(
    request: ClipboardRequest,
    x_api_key: str = Header(None)
):

    verify_api_key(x_api_key)

    try:

        process = subprocess.Popen(
            ["pbcopy"],
            stdin=subprocess.PIPE,
            text=True
        )

        process.communicate(request.text)

        return {
            "success": True,
            "message": "Clipboard Updated"
        }

    except Exception as e:

        return {
            "success": False,
            "error": str(e)
        }

# ----------------------------
# COMMAND CENTER
# ----------------------------
@app.post("/command")
def execute_command(
    request: CommandRequest,
    x_api_key: str = Header(None)
):

    verify_api_key(x_api_key)

    command = request.command.lower().strip()

    try:

        # ---------------- APPS ----------------

        if any(x in command for x in ["vs code", "vscode", "code"]):

            subprocess.run(["open", "-a", "Visual Studio Code"])

            return {
                "success": True,
                "message": "VS Code opened"
            }

        elif "chrome" in command:

            subprocess.run(["open", "-a", "Google Chrome"])

            return {
                "success": True,
                "message": "Chrome opened"
            }
        elif "brave" in command:

            subprocess.run([
                "open",
                "-a",
        "Brave Browser"
    ])

            return {
                "success": True,
                "message": "Brave opened"
    }

        elif any(x in command for x in ["terminal", "iterm", "iterm2"]):

            subprocess.run(["open", "-a", "Terminal"])

            return {
                "success": True,
                "message": "Terminal opened"
            }

        elif any(x in command for x in ["finder", "files"]):

            subprocess.run(["open", "-a", "Finder"])

            return {
                "success": True,
                "message": "Finder opened"
            }

        elif "safari" in command:

            subprocess.run(["open", "-a", "Safari"])

            return {
                "success": True,
                "message": "Safari opened"
            }

        elif "notes" in command:

            subprocess.run(["open", "-a", "Notes"])

            return {
                "success": True,
                "message": "Notes opened"
            }

        elif "spotify" in command:

            subprocess.run(["open", "-a", "Spotify"])

            return {
                "success": True,
                "message": "Spotify opened"
            }
        # ---------------- FOLDERS ----------------

        elif "downloads" in command:

            subprocess.run(["open", str(Path.home() / "Downloads")])

        elif "desktop" in command:

            subprocess.run(["open", str(Path.home() / "Desktop")])

        elif "documents" in command:

            subprocess.run(["open", str(Path.home() / "Documents")])

        elif "pictures" in command:

            subprocess.run(["open", str(Path.home() / "Pictures")])

        elif "movies" in command:

            subprocess.run(["open", str(Path.home() / "Movies")])

        elif "music" in command:

            subprocess.run(["open", str(Path.home() / "Music")])

        elif "applications" in command:

            subprocess.run(["open", "/Applications"])

        # ---------------- SYSTEM ----------------

        elif "lock" in command:

            subprocess.run([
                "osascript",
                "-e",
                'tell application "System Events" to keystroke "q" using {control down, command down}'
            ])

        elif "sleep" in command:

            subprocess.run([
                "pmset",
                "sleepnow"
            ])

        elif "restart" in command:

            subprocess.run([
                "osascript",
                "-e",
                'tell application "System Events" to restart'
            ])

        elif "shutdown" in command:

            subprocess.run([
                "osascript",
                "-e",
                'tell application "System Events" to shut down'
            ])
           
# ---------------- AUDIO ----------------

        elif "volume up" in command:

            subprocess.run([
                "osascript",
                "-e",
                'set volume output volume (output volume of (get volume settings) + 10)'
            ])

            return {
                "success": True,
                "message": "Volume Increased"
            }

        elif "volume down" in command:

            subprocess.run([
                "osascript",
                "-e",
                'set volume output volume (output volume of (get volume settings) - 10)'
            ])

            return {
                "success": True,
                "message": "Volume Decreased"
            }
        elif "screenshot" in command:

            subprocess.run([
                "screencapture",
                str(Path.home() / "Desktop" / "MacPilot_Screenshot.png")
            ])

            return {
                "success": True,
                "message": "Screenshot captured"
            }
        elif "dark mode" in command:

            subprocess.run([
                "osascript",
                "-e",
                'tell application "System Events" to tell appearance preferences to set dark mode to not dark mode'
            ])

            return {
                "success": True,
                "message": "Dark Mode Toggled"
            }

        else:

            return {
                "success": False,
                "message": "Unknown command"
            }

        return {
            "success": True,
            "message": "Command executed successfully"
        }

    except Exception as e:

        return {
            "success": False,
            "error": str(e)
        }
#
# ----------------------------
# VOLUME
# ----------------------------

@app.get("/volume")
def get_volume(
    x_api_key: str = Header(None)
):

    verify_api_key(x_api_key)

    try:

        result = subprocess.run(
            [
                "osascript",
                "-e",
                "output volume of (get volume settings)"
            ],
            capture_output=True,
            text=True
        )

        return {
            "success": True,
            "value": int(result.stdout.strip())
        }

    except Exception as e:

        return {
            "success": False,
            "error": str(e)
        }


@app.post("/volume")
def set_volume(
    request: VolumeRequest,
    x_api_key: str = Header(None)
):

    verify_api_key(x_api_key)

    try:

        value = max(0, min(100, request.value))

        result = subprocess.run(
            [
                "osascript",
                "-e",
                f"set volume output volume {value}"
            ],
            capture_output=True,
            text=True
        )

        print(result.stdout)
        print(result.stderr)

        return {
            "success": True,
            "value": value
        }

    except Exception as e:

        return {
            "success": False,
            "error": str(e)
        }
@app.get("/screenshot")
def get_screenshot(
    x_api_key: str = Header(None)
):

    verify_api_key(x_api_key)

    try:

        # Temporary file
        temp_file = os.path.join(
            tempfile.gettempdir(),
            "macpilot_screen.png"
        )

        # Take screenshot (silent)
        subprocess.run(
            [
                "screencapture",
                "-x",
                temp_file
            ],
            check=True
        )

        # macOS notification
        subprocess.run(
            [
                "osascript",
                "-e",
                'display notification "Android requested a screenshot." with title "MacPilot Security"'
            ]
        )

        return FileResponse(
            temp_file,
            media_type="image/png",
            filename="screen.png"
        )

    except Exception as e:

        return {
            "success": False,
            "error": str(e)
        }