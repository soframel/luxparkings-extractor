curl -X PUT \
  http://localhost:9200/parkings \
  -H 'Authorization: Basic TODO' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 0bc2bac5-a03c-4127-a980-30e2caf4f088' \
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