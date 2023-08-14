from typing import Union

from fastapi import FastAPI
from fastapi.encoders import jsonable_encoder
from fastapi.responses import JSONResponse
import uvicorn
from TodoItem import TodoItem

app = FastAPI()

data = [
    {"id": 1, "title": "todo 1", "description": "todo 1 desc", "status": 0},
    {"id": 2, "title": "todo 2", "description": "todo 2 desc", "status": 0},
    {"id": 3, "title": "todo 3", "description": "todo 3 desc", "status": 0},
]

MAX_ID_VAL: int = 3


@app.get("/items/{status}/status")
def read_items(status: str):
    if status == "all":
        return data

    if status == "initial":
        ret = []
        for d in data:
            if d["status"] == 0:
                ret.append(d)
        return ret

    return []


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    for d in data:
        if d.id == item_id:
            return d

    return None


@app.post("/items/add/{title}/desc/{desc}")
def add_item(title: str, desc: str) -> TodoItem:
    global MAX_ID_VAL
    MAX_ID_VAL = MAX_ID_VAL + 1
    item: TodoItem = TodoItem(id=MAX_ID_VAL, title=title, description=desc, status=0)
    data.append(item)

    encoded_ret = jsonable_encoder(item)
    ret = JSONResponse(content=encoded_ret)
    return ret


@app.post("/items/update")
def update_item(item: TodoItem):
    target = None
    for el in data:
        if item.id == el.id:
            target = el

    if target is None:
        return None

    target = item


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
