@echo off 
chcp 65001>nul

set fld=%2
set ico=%1
set ini=%3

attrib -h -s %ini% 2>nul
(
echo/[.ShellClassInfo]
echo/IconResource=%ico%,0
) > %ini%

attrib +h +s -a %ini%
attrib +r %fld%