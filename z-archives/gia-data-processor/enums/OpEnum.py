from enum import Enum


class OpEnum(Enum):
    CLEAR_USER = 'clear_user'
    USER_INFO = 'user_info'

    @staticmethod
    def value_list():
        result = []
        for env in OpEnum.__members__.values():
            result.append(env.value)
        return result
