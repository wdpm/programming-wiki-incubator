import login
import utils


if __name__ == '__main__':
    utils.save_json_file(path="./cookie.json", data=login.get_cookie())

