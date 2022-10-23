import logging

# 在这里调节基本 logging 的级别，目前是 level=logging.WARNING
# terminal mode
# logging.basicConfig(level=logging.WARNING, format='%(asctime)s - %(levelname)s- %(message)s')

# file mode
logging.basicConfig(filename='log.txt', level=logging.WARNING, format='%(asctime)s - %(levelname)s- %(message)s')

# file mode and terminal mode
# explore python logging handler

# 住手，不要再使用 print 来 debug 了。

# 禁用 logging 的一种办法：使用 disable()
# turn off all logging
# logging.disable(logging.CRITICAL)

logging.debug("debug msg.")
logging.info("info msg.")
logging.warning("warning msg.")
logging.error("error msg.")
logging.critical("critical msg.")

# 2022-10-14 18:37:34,235 - WARNING- warning msg.
# 2022-10-14 18:37:34,235 - ERROR- error msg.
# 2022-10-14 18:37:34,235 - CRITICAL- critical msg.
