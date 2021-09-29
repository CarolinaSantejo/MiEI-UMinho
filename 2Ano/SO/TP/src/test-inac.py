import sys
import time

def main():
    t = 10
    if(len(sys.argv) == 3):
        t = int(sys.argv[2])

    if(len(sys.argv) < 2):
        string = input()
        time.sleep(t)
        print(string)
    else:
        string = sys.argv[1]
        time.sleep(t)
        print(string)

if __name__ == "__main__":
    main()