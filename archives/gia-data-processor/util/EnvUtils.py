import yaml

from config import PgConfig
from config import RcConfig


def load_env(env: str):
    env_file = 'env-' + env + '.yml'
    with open(env_file, 'r') as it:
        config = yaml.safe_load(it)
    PgConfig.config = config[PgConfig.root]
    RcConfig.config = config[RcConfig.root]
