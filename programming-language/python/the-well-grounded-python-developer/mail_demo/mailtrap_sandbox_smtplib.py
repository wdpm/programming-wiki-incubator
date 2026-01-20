import smtplib

import myenv

user = myenv.MAILTRAP_EMAIL_HOST_USER
host_password = myenv.MAILTRAP_EMAIL_HOST_PASSWORD

sender = "Private Person <from@example.com>"
receiver = "A Test User <1137299673@qq.com>"

message = f"""\
Subject: Hi Mailtrap
To: {receiver}
From: {sender}

This is a test e-mail message."""

with smtplib.SMTP("sandbox.smtp.mailtrap.io", 2525) as server:
    server.login(user, host_password)
    server.sendmail(sender, receiver, message)

print('done')