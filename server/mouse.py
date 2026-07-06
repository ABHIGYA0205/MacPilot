from Quartz.CoreGraphics import (
    CGEventCreate,
    CGEventCreateMouseEvent,
    CGEventGetLocation,
    CGEventPost,
    kCGHIDEventTap,
    kCGEventMouseMoved,
    CGEventCreateScrollWheelEvent,
    kCGScrollEventUnitPixel,
)
from Quartz.CoreGraphics import *

def move_mouse(dx: float, dy: float):
    event = CGEventCreate(None)
    location = CGEventGetLocation(event)

    new_x = location.x + dx
    new_y = location.y + dy

    move = CGEventCreateMouseEvent(
        None,
        kCGEventMouseMoved,
        (new_x, new_y),
        0,
    )

    CGEventPost(kCGHIDEventTap, move)



def left_click():

    event = CGEventCreate(None)
    pos = CGEventGetLocation(event)

    down = CGEventCreateMouseEvent(
        None,
        kCGEventLeftMouseDown,
        pos,
        kCGMouseButtonLeft
    )

    up = CGEventCreateMouseEvent(
        None,
        kCGEventLeftMouseUp,
        pos,
        kCGMouseButtonLeft
    )

    CGEventPost(kCGHIDEventTap, down)
    CGEventPost(kCGHIDEventTap, up)

def right_click():

    event = CGEventCreate(None)
    pos = CGEventGetLocation(event)

    down = CGEventCreateMouseEvent(
        None,
        kCGEventRightMouseDown,
        pos,
        kCGMouseButtonRight
    )

    up = CGEventCreateMouseEvent(
        None,
        kCGEventRightMouseUp,
        pos,
        kCGMouseButtonRight
    )

    CGEventPost(kCGHIDEventTap, down)
    CGEventPost(kCGHIDEventTap, up)


def scroll_mouse(dy: float):

    event = CGEventCreateScrollWheelEvent(
        None,
        kCGScrollEventUnitLine,   # <-- Line-based scrolling
        1,
        int(-dy / 8)              # Scale it down
    )

    CGEventPost(kCGHIDEventTap, event)