## ElasticSearch

http://localhost:9200/

### インデックス

作成

```
curl -X PUT 'localhost:9200/test?pretty'
```

確認（インデックス全体）

```
curl -X GET 'localhost:9200/_cat/indices?v'
```

確認（インデックス個別）

```
curl -X GET 'localhost:9200/test?pretty'
```

### ドキュメント

作成

```
curl -X POST 'localhost:9200/test/_doc/?pretty' -H 'Content-Type:application/json' -d '{
  "name": "test document",
  "key": "value",
  "numKey": 100
}'
```

確認（インデックス指定なし）

```
curl -X GET 'localhost:9200/_search?pretty' -H 'Content-Type:application/json' -d '{
  "query": {
    "bool": {
      "must": [
        {"match":{"name":"test document"}}
      ]
    }
  }
}'
```

確認（インデックス指定あり）

```
curl -X GET 'localhost:9200/test/_search?pretty' -H 'Content-Type:application/json' -d '{
  "query": {
    "bool": {
      "must": [
        {"match":{"name":"test document"}}
      ]
    }
  }
}'
```

更新
差分更新は無理っぽい

```
curl -X PUT 'localhost:9200/test/_doc/[更新したいドキュメントの_id]?pretty' -H 'Content-Type:application/json' -d '{
  "name": "test document",
  "key": "value",
  "numKey": 10
}'
```

削除

```
curl -X DELETE 'localhost:9200/test/_doc/[削除したいドキュメントの_id]?pretty'
```

## 参考

- https://qiita.com/hapoon/items/f35d359c7076b3112195