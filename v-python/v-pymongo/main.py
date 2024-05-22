from pymongo import MongoClient


if __name__ == '__main__':
    client = MongoClient('mongodb://vitamin:vitamin@127.0.0.1:23017/')
    db = client['vitamin']

    foo_collection = db['v_foo']
    query = {}
    foo_list = list(foo_collection.find(query))

    for document in foo_list:
        _id = str(document.get('_id'))
        print(_id)
