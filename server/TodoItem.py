from pydantic import BaseModel


class TodoItem(BaseModel):
    id: int
    title: str
    content: str
    status: int
