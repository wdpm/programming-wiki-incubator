# sums.py
# add the numbers in `data.txt`
import os
from typing import Optional

import typer


def sum(filename):

    # print(os.path.abspath(filename))

    sum = 0.0

    with open(filename, "r") as file:
        for line in file:
            number = float(line)
            sum += number

    return f"{sum:.2f}"


app = typer.Typer()


@app.command()
def main(filename: Optional[str] = typer.Argument("data.txt")):
    print(sum(filename))


if __name__ == "__main__":
    app()
