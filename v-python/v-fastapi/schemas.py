from pydantic import BaseModel

from datetime import datetime


class AppRouterModel(BaseModel):
    id: int
    created_at: datetime
    updated_at: datetime | None = None
    name: str
    icon: str
    auth: str
    group: str
    sort: int
    description: str

    class Config:
        orm_mode = True
