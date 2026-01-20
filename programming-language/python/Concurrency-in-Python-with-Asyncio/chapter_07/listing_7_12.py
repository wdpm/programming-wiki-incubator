import tkinter
from time import sleep
from tkinter import ttk

window = tkinter.Tk()
window.title('Hello world app')
window.geometry('200x100')


def say_hello():
    sleep(5)
    print('Hello there!')


hello_button = ttk.Button(window, text='Say hello', command=say_hello)
hello_button.pack()

window.mainloop()

# 点击 btn 后，由于 sleep() 函数，因此会阻塞 main thread 的 tk event loop，此时 UI 陷入卡死状态。
