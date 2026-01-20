import sums
from typer.testing import CliRunner

runner = CliRunner()


def test_sums_app_no_filename():
    result = runner.invoke(sums.app)
    assert result.stdout.rstrip() == "200.00"
