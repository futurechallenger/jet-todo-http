from typing import Union

from fastapi import FastAPI

app = FastAPI()

data = [
    {"id": 1, "title": "todo 1", "description": "todo 1 desc", "status": 1},
    {"id": 1, "title": "todo 1", "description": "todo 1 desc", "status": 1},
    {"id": 1, "title": "todo 1", "description": "todo 1 desc", "status": 1},
]


@app.get("/")
def read_root():
    return data


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    for d in data:
        if d.id == item_id:
            return d

    return None
