# orm-exposed

exposed v0.54.0 当前upsert更新数组有bug，生成的SQL如下:

```SQL
INSERT INTO city ("name", alias, history, tag) VALUES (?, ?, ?, ?)
ON CONFLICT ("name", alias) DO UPDATE SET history=5000, tag=[尤溪, 明溪, 汤川]
```

实际上应该是:

```SQL
INSERT INTO city ("name", alias, history, tag) VALUES ('四明', '四明', 5000, ARRAY['尤溪','明溪','汤川'])
ON CONFLICT ("name", alias) DO UPDATE SET history=5000, tag=ARRAY['尤溪','明溪','汤川']
```