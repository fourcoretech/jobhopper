#!/bin/bash

echo "Sending test Notification Event to Kafka..."

docker exec -i kafka kafka-console-producer.sh \
  --bootstrap-server kafka:9092 \
  --topic notification.events <<EOF
{"eventType":"TEST_EVENT","recipientEmail":"test.email@test.com","summary":"The notification service works!"}
EOF

echo "Event sent to Kafka topic 'notification.events'."
chmod +x send-test-event.sh
