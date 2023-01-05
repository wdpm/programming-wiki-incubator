# Configuration file for the Sphinx documentation builder.
#
# For the full list of built-in configuration values, see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

# -- Project information -----------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#project-information
import os
import sys

project = 'sw-project'
copyright = '2022, wdpm'
author = 'wdpm'
release = '1.0'

sys.path.insert(0, os.path.abspath('.'))

# -- General configuration ---------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#general-configuration

# https://www.sphinx-doc.org/en/master/usage/extensions/math.html
extensions = ['sphinx.ext.imgmath',
              'sphinx.ext.autodoc',
              'sphinx.ext.todo']
todo_include_todos = True
templates_path = ['_templates']
exclude_patterns = []
language = "zh_CN"

# -- Options for HTML output -------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#options-for-html-output

html_theme = 'alabaster'
html_static_path = ['_static']
