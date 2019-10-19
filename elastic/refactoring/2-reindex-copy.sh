curl -X POST \
  http://localhost:9200/_reindex \
  -H 'Authorization: Basic TODO' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 87c09da5-9105-4cc0-826b-3bbbf531ecde' \
  -H 'cache-control: no-cache' \
  -d '{
  "source": {
    "index": "parkings"
  },
  "dest": {
    "index": "parkings2"
  }
}'