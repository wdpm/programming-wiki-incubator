import datetime

# Create a timezone object for the local timezone
local_tz = datetime.datetime.now(datetime.timezone.utc).astimezone().tzinfo

# Create two datetime objects for the same local time, but different UTC times
dt1 = datetime.datetime(2022, 11, 6, 1, 30, tzinfo=local_tz)
dt2 = datetime.datetime(2022, 11, 6, 2, 30, tzinfo=local_tz)

# Check if the times are ambiguous
if dt1.fold == dt2.fold:
    print("The times are not ambiguous.")
else:
    print("The times are ambiguous.")
