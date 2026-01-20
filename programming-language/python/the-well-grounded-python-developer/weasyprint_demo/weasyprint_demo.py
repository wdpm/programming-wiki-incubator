import os

# os.add_dll_directory(r"C:\GTK3-Runtime Win64\bin")

# insert the GTK3 Runtime folder at the beginning. Can be bin or lib, depending on path you choose while installing.
GTK_FOLDER = r'C:\GTK3-Runtime Win64\bin'
os.environ['PATH'] = GTK_FOLDER + os.pathsep + os.environ.get('PATH', '')

print(os.environ['PATH'])

from weasyprint import HTML
HTML('https://weasyprint.org/').write_pdf('weasyprint-website.pdf')