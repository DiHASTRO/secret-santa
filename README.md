
# secret-santa

Rest service for Secret Santa based on Java Spring + Maven + Hibernate

## Installation

To use this program, it is necessary to install and open MySQL server and input its data in
```
  application.properties
```
    
## API Reference

### User actions
#### Create a user

```http
  POST /user/create
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Unique username |

#### Create a group

```http
  POST /group/create
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `executor`      | `string` | **Required**. Username of creator |
| `groupname`      | `string` | **Required**. Unique group name |

### Group actions
#### Create a group

```http
  POST /group/join
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `executor`      | `string` | **Required**. Username of joiner |
| `groupname`      | `string` | **Required**. Group name to join in |

#### Get list of groups with status (opened | closed)

```http
  GET /list-groups
```

#### Add a wish

```http
  POST /group/add-wish
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `executor`      | `string` | **Required**. Username |
| `groupname`      | `string` | **Required**. Group name to set wish |
| `operand`      | `string` | **Required**. Wish |

#### Appoint an admin

```http
  POST /group/admin-appointment
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `executor`      | `string` | **Required**. Username of admin |
| `groupname`      | `string` | **Required**. Group name |
| `operand`      | `string` | **Required**. Username of new admin |

#### Start Sercret Santa

```http
  POST /group/start-secret-santa
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `executor`      | `string` | **Required**. Username of admin |
| `groupname`      | `string` | **Required**. Group name where to start Secret Santa |

#### Get to who make a gift and his wish

```http
  POST /group/get-to-who
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `executor`      | `string` | **Required**. Username of a person in a group |
| `groupname`      | `string` | **Required**. Group name |

#### Exit from group

```http
  POST /group/exit
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `executor`      | `string` | **Required**. Username of quitter |
| `groupname`      | `string` | **Required**. Group name of group to exit from |

#### Remove a group

```http
  POST /group/remove
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `executor`      | `string` | **Required**. Username of admin |
| `groupname`      | `string` | **Required**. Group name of group to remove |





## Authors

- [@DiHASTRO](https://www.github.com/DiHASTRO)

