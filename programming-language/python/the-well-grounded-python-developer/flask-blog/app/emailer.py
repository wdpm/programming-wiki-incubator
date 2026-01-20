from logging import getLogger

import sib_api_v3_sdk
from flask import current_app
from flask_mail import Mail, Message
from sib_api_v3_sdk.rest import ApiException

logger = getLogger(__name__)

def send_mail_by_mailtrap_testing(to, subject, contents):
    """Sends an email using SendInBlue

    Args:
        to (string): The email address to send the email to
        subject (string): The subject of the email
        contents (string): The html formatted email contents
    """

    app = current_app

    app.config['MAIL_SERVER'] = current_app.config.get("MAILTRAP_EMAIL_HOST")
    app.config['MAIL_PORT'] = current_app.config.get("MAILTRAP_EMAIL_PORT")
    app.config['MAIL_USERNAME'] = current_app.config.get("MAILTRAP_EMAIL_HOST_USER")
    app.config['MAIL_PASSWORD'] = current_app.config.get("MAILTRAP_EMAIL_HOST_PASSWORD")
    app.config['MAIL_USE_TLS'] = True
    app.config['MAIL_USE_SSL'] = False

    mail = Mail(app)

    try:
        msg = Message(subject=subject, sender='flask-blog-demo@mailtrap.io', recipients=[to])
        msg.body = contents
        mail.send(msg)
        logger.debug(f"Confirmation email sent to {to}")
    except Exception as e:
        logger.exception("Exception sending email", exc_info=e)

def send_mail_by_sendinblue(to, subject, contents):
    """Sends an email using SendInBlue

    Args:
        to (string): The email address to send the email to
        subject (string): The subject of the email
        contents (string): The html formatted email contents
    """
    configuration = sib_api_v3_sdk.Configuration()
    configuration.api_key['api-key'] = current_app.config.get("SIB_API_KEY")

    api_instance = sib_api_v3_sdk.TransactionalEmailsApi(sib_api_v3_sdk.ApiClient(configuration))
    smtp_email = sib_api_v3_sdk.SendSmtpEmail(
        to=[{"email": to}],
        html_content=contents,
        sender={"name": "MyBlog", "email": "no-reply@myblog.com"},
        subject=subject
    )
    try:
        api_instance.send_transac_email(smtp_email)
        logger.debug(f"Confirmation email sent to {to}")
    except ApiException as e:
        logger.exception("Exception sending email", exc_info=e)
