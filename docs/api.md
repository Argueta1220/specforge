# API Notes

The backend starts on `http://localhost:8080`.

## Get sample parts

`GET /api/parts`

Returns seeded CPUs, motherboards, RAM modules, and PSUs.

## Check compatibility

`POST /api/compatibility/check`

Request body:

```json
{
  "cpuId": 1,
  "motherboardId": 1,
  "ramId": 1,
  "psuId": 2
}
```

Response body:

```json
{
  "compatible": true,
  "errors": [],
  "warnings": [],
  "estimatedWattage": 373
}
```
