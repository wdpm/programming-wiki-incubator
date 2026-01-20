import smtplib
from email.mime.text import MIMEText

import myenv

host = myenv.MAILTRAP_EMAIL_HOST
port = myenv.MAILTRAP_EMAIL_PORT
sender = myenv.MAILTRAP_EMAIL_HOST_USER
password = myenv.MAILTRAP_EMAIL_HOST_PASSWORD

subject = "Email Subject"
body = "This is the body of the text message."
recipients = ["1137299673@qq.com"]

# guide link:
# - https://mailtrap.io/blog/python-send-email-gmail/#How-to-send-an-email-with-Python-via-Gmail-SMTP
# - https://mailtrap.io/blog/python-send-email-gmail/#How-to-send-an-HTML-email

# 这个模式非常简单，但是依旧需要惊扰一些用于测试的email接受者，可能会混乱这些用户的收件箱。

def send_email(subject, body, sender, recipients, password):
    msg = MIMEText(body)
    msg['Subject'] = subject
    msg['From'] = sender
    msg['To'] = ', '.join(recipients)
    with smtplib.SMTP_SSL(host, port) as smtp_server:
        smtp_server.login(sender, password)
        smtp_server.sendmail(sender, recipients, msg.as_string())
    print("Message sent!")


send_email(subject, body, sender, recipients, password)
