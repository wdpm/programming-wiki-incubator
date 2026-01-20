from flask import Flask
from flask_mail import Mail, Message

import myenv

app = Flask(__name__)

# pricing of free plan:
# - Email Sending: 1000 per month
# - Email Testing: 100 per month
app.config['MAIL_SERVER'] = 'sandbox.smtp.mailtrap.io'
app.config['MAIL_PORT'] = 2525
app.config['MAIL_USERNAME'] = myenv.MAILTRAP_EMAIL_HOST_USER
app.config['MAIL_PASSWORD'] = myenv.MAILTRAP_EMAIL_HOST_PASSWORD
app.config['MAIL_USE_TLS'] = True
app.config['MAIL_USE_SSL'] = False

mail = Mail(app)

# https://mailtrap.io/blog/flask-email-sending/

@app.route("/mail")
def index():
    msg = Message(subject='Hello from the other side!', sender='peter@mailtrap.io', recipients=['paul@mailtrap.io'])
    msg.body = "Hey Paul, sending you this email from my Flask app, lmk if it works"
    mail.send(msg)
    return "Message sent!"


if __name__ == '__main__':
    app.run(debug=True,port=7000)
