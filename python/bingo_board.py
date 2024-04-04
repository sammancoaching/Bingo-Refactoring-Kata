import string


class BingoBoard:

    def __init__(self, width: int, height: int):
        self.__cells = [ [None]*height for _ in range(width) ]
        self.__marked = [ [False]*height for _ in range(width) ]

    def define_cell(self, x: int, y: int, value: string):
        if self.__cells[x][y] is not None:
            raise IllegalStateException("Cell already defined.")

        for c in range(len(self.__cells)):
            for r in range(len(self.__cells[c])):
                if value == self.__cells[c][r]:
                    raise IllegalStateException(f"{value} already present at {c}, {r}")

        self.__cells[x][y] = value

    def mark_cell(self, x: int, y: int):
        if not self.is_initialised():
            raise IllegalStateException("Board is not initialised.")

        self.__marked[x][y] = True

    def is_initialised(self):
        for row in self.__cells:
            for column in row:
                if column is None:
                    return False

        return True

    def is_marked(self, x: int, y: int):
        return self.__marked[x][y]


class IllegalStateException(Exception):
    pass
