import os
import sys

from PySide6 import QtWidgets
from PySide6.QtUiTools import QUiLoader

loader = QUiLoader()
basedir = os.path.dirname(__file__)


def mainwindow_setup(w):
    w.setWindowTitle("MainWindow Title")


app = QtWidgets.QApplication(sys.argv)
window = loader.load(os.path.join(basedir, "mainwindow.ui"), None)
mainwindow_setup(window)
window.show()
app.exec()
