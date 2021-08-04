# API

Where full URLs are provided in responses they will be rendered as if service is running on `'http://{hostname}/'`

## Open Endpoints

Open endpoints require no Authentication.

- Endpoint : `/api/v1/`

## End point in Details

Endpoints for viewing and manipulating the Accounts that the Authenticated User
has permissions to access.

**`Click on each to Expand`**

<details close="close">
<summary><i>Save Employee</i></summary>

- [Save employee details](#) : `POST /api/v1/employees/{id}`

## Get All Employees

Get all emplooyees details from DB and if not then Null

**URL** : `/api/v1/employees`

**Method** : `GET`

**Data constraints**

Provide name of Account to be created.

```json
{
  "firstName": "[Unique, not null, String]",
  "lastName": "[String]",
  "EmailId": "[Unique, not null, String]",
  "phone": "[String]"
}
```

**Data example** All fields must be sent.

```json
{
  "firstName": "FirstName2",
  "lastName": "LastName2",
  "EmailId": "example2@email.com",
  "phone": "0987654321"
}
```

## Error Responses

**Condition** : If Data cannot be fetched

**Code** : `400 BAD REQUEST`

</details>

<details close="close">
<summary><i>Get All Employees</i></summary>

- [Get all employees details](#) : `GET /api/v1/employees`

Get the details of employee from by using ID of employee which is uniyq primary key.

**URL** : `/api/v1/employees`

**Method** : `GET`

## Success Response

**Code** : `200 OK`

**Content examples**

For a Employee with ID 1234 on the local database where that User has saved information.

```json
[
  {
    "id": 1234,
    "firstName": "FirstName",
    "lastName": "LastName",
    "EmailId": "example@email.com",
    "phone": "1234567890"
  },
  {
    "id": 5678,
    "firstName": "FirstName",
    "lastName": "LastName",
    "EmailId": "example@email.com",
    "phone": "1234567890"
  }
]
```

</details>

<details close="close">
<summary><i>Get One Employee</i></summary>

- [Get individual employee details](#) : `GET /api/v1/employees/{id}`

## Get Employee Details by ID

Get the details of employee from by using ID of employee which is uniyq primary key.

**URL** : `/api/v1/employees/{id}`

**Method** : `GET`

## Success Response

**Code** : `200 OK`

**Content examples**

For a Employee with ID 1234 on the local database where that User has saved information.

```json
{
  "id": 1234,
  "firstName": "FirstName",
  "lastName": "LastName",
  "EmailId": "example@email.com",
  "phone": "1234567890"
}
```

</details>

<details close="close">
<summary><i>Update Employee</i></summary>

- [Update/Edit/Modify employee details](#) : `PUT /api/v1/employees{id}`

</details>

<details close="close">
<summary><i>Delete Employee</i></summary>

- [Delete employee details](#) : `DELETE /api/v1/employees/{id}`

</details>

