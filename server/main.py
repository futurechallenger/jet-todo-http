from typing import Union

from fastapi import FastAPI

app = FastAPI()

data = [
    {"id": 1, "title": "todo 1", "description": "todo 1 desc", "status": 0},
    {"id": 2, "title": "todo 2", "description": "todo 2 desc", "status": 0},
    {"id": 3, "title": "todo 3", "description": "todo 3 desc", "status": 0},
]


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
