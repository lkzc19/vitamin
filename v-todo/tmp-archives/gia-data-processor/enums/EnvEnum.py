from enum import Enum


class EnvEnum(Enum):
    LOCAL = 'local'
    DEV = 'dev'
    TEST = 'test'

    @staticmethod
    def value_list():
        result = []
        for env in EnvEnum.__members__.values():
            result.append(env.value)
        return result
