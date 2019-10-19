curl -X PUT \
  http://localhost:9200/parkings2 \
  -H 'Authorization: Basic TODO' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: f6c9edb3-8a1b-4f9f-a371-90cb1f9fa43c' \
  -H 'cache-control: no-cache' \
  -d '{
    "settings" : {
        "number_of_shards" : 1
    },
    "mappings" : {
    "properties": {
      "message": {
        "properties": {
          "actuel": {
            "type": "integer"
          },
          "complet": {
            "type": "keyword"
          },
          "coords": {
            "properties": {
              "latitude": {
                "type": "float"
              },
              "longitude": {
                "type": "float"
              }
            }
          },
          "id": {
            "type": "text",
            "fields": {
              "keyword": {
                "type": "keyword",
                "ignore_above": 256
              }
            }
          },
          "ouvert": {
            "type": "keyword"
          },
          "tendance": {
            "type": "keyword"
          },
          "title": {
            "type": "keyword"
          },
          "total": {
            "type": "long"
          }
        }
      },
      "parkingName": {
        "type": "keyword"
      },
      "postDate": {
        "type": "date"
      },
      "time": {
        "type": "text",
        "fields": {
          "keyword": {
            "type": "keyword",
            "ignore_above": 16
          }
        }
      },
      "user": {
        "type": "keyword"
      }
    }
    }
}'