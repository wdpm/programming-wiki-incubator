import os
import sys

from PySide6 import QtWidgets
from PySide6.QtUiTools import QUiLoader

basedir = os.path.dirname(__file__)

loader = QUiLoader()

app = QtWidgets.QApplication(sys.argv)
window = loader.load(os.path.join(basedir, "mainwindow.ui"), None)
window.show()
app.exec()
