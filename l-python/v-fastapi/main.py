import hashlib
import os
from typing import Annotated, Optional

import oss2
from fastapi import Depends, FastAPI, File, Form, UploadFile
from sqlalchemy.orm import Session

import crud
import models
import schemas
from database import SessionLocal, engine

models.Base.metadata.create_all(bind=engine)

app = FastAPI()


# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@app.put("/router/{auth}/{name}", response_model=str)
async def update_router(
        name: str,
        auth: str,
        file: Optional[UploadFile] = File(default=None),
        group: Optional[str] = Form(default=None),
        description: Optional[str] = Form(default=None),
        db: Session = Depends(get_db)
):
    if auth != 'BIZ_BUYER' and auth != 'BIZ_SELLER' and auth != 'CUSTOMER':
        return "auth fail"
    if group is None and description is None and file is None:
        return "param fail"
    icon: str = ""
    if file is not None:
        contents = await file.read()
        md5_hash = hashlib.md5(contents)
        filename, ext = os.path.splitext(file.filename)
        icon = "ckmro/apps/router/" + md5_hash.hexdigest() + ext

        app_oss_key_id = "xx"
        app_oss_key_secret = "xx"
        app_oss_endpoint = "http://oss-cn-shanghai.aliyuncs.com"
        bucket_name = "ckmro"
        bucket = oss2.Bucket(oss2.Auth(app_oss_key_id, app_oss_key_secret), app_oss_endpoint, bucket_name)
        result = bucket.put_object(icon, contents)
        if result.status != 200:
            return "oss fail"
    crud.update(
        db,
        auth=auth,
        name=name,
        icon=icon,
        group=group,
        description=description
    )
    return icon


@app.get("/router/{auth}/{name}", response_model=schemas.AppRouterModel)
def get_router(
        auth: str,
        name: str,
        db: Session = Depends(get_db)
):
    return crud.get_user_by_name(db, name=name, auth=auth)


@app.post("/oss-test", response_model=str)
def oss_test(
        file: Annotated[bytes, File()],
        token: Annotated[str, Form()],
):
    print(token)
    app_oss_key_id = "xx"
    app_oss_key_secret = "x"
    app_oss_endpoint = "http://oss-cn-shanghai.aliyuncs.com"
    bucket_name = "ckmro"
    auth = oss2.Auth(app_oss_key_id, app_oss_key_secret)
    bucket = oss2.Bucket(auth, app_oss_endpoint, bucket_name)
    bucket.put_object('ckmro/apps/router/test.png', file)
    return "ok"


@app.get("/", response_model=str)
def probe():
    return "ok"


@app.post("/deploy/{env}", response_model=str)
def oss_test(
        env: str,
):
    if env != 'dev' and env != 'test':
        return "[env]: [dev | test]"
    return "ok"

