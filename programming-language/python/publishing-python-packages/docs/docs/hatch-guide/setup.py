import os

from setuptools import find_packages, setup

about = {}
with open(os.path.join('src', 'foo', '__about__.py'), 'r', 'utf-8') as f:
    exec(f.read(), about)

with open('README.md', 'r', 'utf-8') as f:
    readme = f.read()

setup(
    # Metadata
    name='foo',
    version=about['__version__'],
    description='...',
    long_description=readme,
    long_description_content_type='text/markdown',
    author='...',
    author_email='...',
    project_urls={
        'Documentation': '...',
        'Source': '...',
    },
    classifiers=[
        '...',
    ],
    keywords=[
        '...',
    ],
    python_requires='>=3.8',
    install_requires=[
        '...',
    ],
    extras_require={
        'feature': ['...'],
    },

    # Packaging
    packages=find_packages(where='src'),
    package_dir={'': 'src'},
    package_data={
        'foo': ['py.typed'],
    },
    zip_safe=False,
    entry_points={
        'console_scripts': [
            'foo = foo.cli:main',
        ],
    },
)
