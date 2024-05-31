from sqlalchemy import Boolean, Column, Integer, BIGINT, String, TIMESTAMP

from database import Base


class AppRouter(Base):
    __tablename__ = "rc_app_router"

    id = Column(BIGINT, primary_key=True, index=True)
    created_at = Column(TIMESTAMP)
    updated_at = Column(TIMESTAMP, nullable=True)
    name = Column(String)
    icon = Column(String)
    auth = Column(String)
    group = Column(String)
    sort = Column(Integer)
    description = Column(String)


