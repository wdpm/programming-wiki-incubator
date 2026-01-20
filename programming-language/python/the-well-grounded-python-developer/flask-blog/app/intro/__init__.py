from flask import Blueprint

# static_folder 和 template_folder 指定的文件夹路径都是相对于蓝图所在的模块的。
# 而 static_url_path 指定的 URL 前缀则是相对于应用程序的根路径的。
intro_bp = Blueprint(
    'intro_bp', __name__,
    static_folder="static",
    static_url_path="/intro/static",
    template_folder="templates"
)

from . import intro
