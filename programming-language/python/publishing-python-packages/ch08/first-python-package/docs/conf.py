# Configuration file for the Sphinx documentation builder.
#
# This file only contains a selection of the most common options. For a full
# list see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

# -- Path setup --------------------------------------------------------------

# If extensions (or modules to document with autodoc) are in another directory,
# add these directories to sys.path here. If the directory is relative to the
# documentation root, use os.path.abspath to make it absolute, like shown here.
#
import os
from importlib import metadata

# import sys
# sys.path.insert(0, os.path.abspath('.'))


# -- Project information -----------------------------------------------------

project = "pubpypack-harmony-dane-hillard"
copyright = "2022, Dane Hillard"
author = "Dane Hillard"
PACKAGE_VERSION = metadata.version("pubpypack-harmony-dane-hillard")
version = PACKAGE_VERSION
release = PACKAGE_VERSION

# -- General configuration ---------------------------------------------------

# Add any Sphinx extension module names here, as strings. They can be
# extensions coming with Sphinx (named 'sphinx.ext.*') or your custom
# ones.

# Sphinx provides several extensions that are disabled by default.
# The autodoc and typehints extensions help automatically extract documentation from source code.

# for support markdown, see also: https://www.sphinx-doc.org/en/master/usage/markdown.html
extensions = [
    "sphinx.ext.autodoc",
    "sphinx.ext.autodoc.typehints",
]

# Add any paths that contain templates here, relative to this directory.
templates_path = ["_templates"]

# List of patterns, relative to source directory, that match files and
# directories to ignore when looking for source files.
# This pattern also affects html_static_path and html_extra_path.
exclude_patterns = ["_build", "Thumbs.db", ".DS_Store"]

# -- Options for HTML output -------------------------------------------------

# The theme to use for HTML and HTML Help pages.  See the documentation for
# a list of builtin themes.
# some alternative themes:
# https://sphinx-themes.org/sample-sites/sphinx-book-theme/placeholder-one/
# https://sphinx-themes.org/sample-sites/sphinx-material/search/?q=material
# https://sphinx-themes.org/sample-sites/sphinx-rtd-theme/
html_theme = "sphinx_rtd_theme"

# Add any paths that contain custom static files (such as style sheets) here,
# relative to this directory. They are copied after the builtin static files,
# so a file named "default.css" will overwrite the builtin "default.css".
html_static_path = ["_static"]

# -- Setup for sphinx-apidoc -------------------------------------------------

# Read the Docs doesn't support running arbitrary commands like tox.
# sphinx-apidoc needs to be called manually if Sphinx is running there.
# https://github.com/readthedocs/readthedocs.org/issues/1139

if os.environ.get("READTHEDOCS") == "True":
    from pathlib import Path

    PROJECT_ROOT = Path(__file__).parent.parent
    PACKAGE_ROOT = PROJECT_ROOT / "src" / "imppkg"

    def run_apidoc(_):
        from sphinx.ext import apidoc

        apidoc.main(
            [
                "--force",
                "--implicit-namespaces",
                "--module-first",
                "--separate",
                "-o",
                # dest
                str(PROJECT_ROOT / "docs" / "reference"),
                # source
                str(PACKAGE_ROOT),
                # multi ignored files
                str(PACKAGE_ROOT / "*.c"),
                str(PACKAGE_ROOT / "*.so"),
            ]
        )

    def setup(app):
        # builder-inited: which triggers just before the build occurs.
        app.connect("builder-inited", run_apidoc)
