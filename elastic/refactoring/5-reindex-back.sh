curl -X POST \
  http://localhost:9200/_reindex \
  -H 'Authorization: Basic TODO' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 7d142342-e499-4e15-b03c-1ecc777a7cd2' \
  -H 'cache-control: no-cache' \
  -d '{
  "source": {
    "index": "parkings2"
  },
  "dest": {
    "index": "parkings"
  }
}'