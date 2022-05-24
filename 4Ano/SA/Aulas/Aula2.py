from pynput import keyboard
import datetime

#detect key press
def on_press(key):
    try:
        print('alphanumeric key {0} pressed'.format(key.char))
    except:
        print('special key {0} pressed'.format(key))

#detect key releases
def on_release(key):
    print('{0} released'.format(key))
    if key == keyboard.Key.esc:
        #stop listener
        print('Gracefully Stopping!')
        return False

def saveLogs(event, value):
    timestamp = get_current_time()
    f = open('logger.txt', 'a')
    f.write(timestamp + '|' + event + '|' + value + '\n')
    f.close()

def get_current_time():
    now = datetime.datetime.now()
    return now.strftime("%Y-%m-%d %H:%M:%S")

#collecting events
with keyboard.Listener(on_press=on_press, on_release=on_release) as listener:
    listener.join()
    
