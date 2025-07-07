from sqlalchemy.orm import Session

import models
import schemas


def create(db: Session, app_router: schemas.AppRouterModel):
    db_app_router = models.AppRouter(
        name=app_router.name,
        icon=app_router.icon,
        auth=app_router.auth,
        group=app_router.group,
        sort=app_router.sort,
        description=app_router.description
    )
    db.add(db_app_router)
    db.commit()
    db.refresh(db_app_router)
    return db_app_router


def update(
        db: Session,
        auth: str,
        name: str,
        group: str,
        icon: str,
        description: str
):
    tmp: models.AppRouter = db.query(
        models.AppRouter
    ).filter(
        models.AppRouter.auth == auth,
        models.AppRouter.name == name
    ).first()
    if group:
        tmp.group = group
    if icon:
        tmp.icon = icon
    if description:
        tmp.description = description
    db.commit()


def get_user_by_name(db: Session, auth: str, name: str):
    return db.query(
        models.AppRouter
    ).filter(
        models.AppRouter.auth == auth,
        models.AppRouter.name == name
    ).first()
