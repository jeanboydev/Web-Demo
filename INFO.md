## 运行环境

- Java：1.8.0_181
- MySQL：5.7.23
- Tomcat：8.5.34


## 接口

## /users

- PUT

```javascript
header={
    token:""
}
params={
    username:"",
    password:"",
    role_id:0
}
```

- POST

```javascript
params={
    username:"",
    password:""
}

response={
    token:""
}
```

- POST

```javascript
URL="/{id}"
header={
    token:""
}
params={
    password:"",
    role_id:0
}
```
- GET

```javascript
URL="/{id}"
header={
    token:""
}
```

- DELETE

```javascript
URL="/{id}"
header={
    token:""
}
```


## /users/info

- PUT

```javascript
header={
    token:""
}
params={
    user_id:0,
    real_name:"",
    gender:0,
    birthday:0,
    education_level:0,
    job_id:0,
    department_id:0
}
```

- POST

```javascript
URL="/{id}"
header={
    token:""
}
params={
    real_name:"",
    gender:0,
    birthday:0,
    education_level:0,
    import_time:0,
    update_time:0,
    job_id:0,
    department_id:0
}
```
- GET

```javascript
URL="/{id}"
header={
    token:""
}
```

- DELETE

```javascript
URL="/{id}"
header={
    token:""
}
```

## /role

- PUT

```javascript
header={
    token:""
}
params={
    name:""
}
```

- GET

```javascript
URL="/{id}"
header={
    token:""
}
```

- DELETE

```javascript
URL="/{id}"
header={
    token:""
}
```

## /permission

- PUT

```javascript
URL="/relation"
header={
    token:""
}
params={
    role_id:0,
    permission_identity:0
}
```

- POST

```javascript
URL="/relation/{id}"
header={
    token:""
}
params={
    role_id:0,
    permission_identity:0
}
```
- GET

```javascript
URL="/relation/{id}"
header={
    token:""
}
```
```javascript
header={
    token:""
}
```

- DELETE

```javascript
URL="/relation/{id}"
header={
    token:""
}
```

## /permission

- PUT

```javascript
header={
    token:""
}
params={
    role_id:0,
    permission_identity:0
}
```

- POST

```javascript
URL="/{id}"
header={
    token:""
}
params={
    permission_identity:0
}
```
- GET

```javascript
URL="/{id}"
header={
    token:""
}
```

- DELETE

```javascript
URL="/{id}"
header={
    token:""
}
```


## /department

- PUT

```javascript
header={
    token:""
}
params={
    name:""
}
```

- POST

```javascript
URL="/{id}"
header={
    token:""
}
params={
    name:""
}
```
- GET

```javascript
URL="/{id}"
header={
    token:""
}
```

- DELETE

```javascript
URL="/{id}"
header={
    token:""
}
```

## /job

- PUT

```javascript
header={
    token:""
}
params={
    name:""
}
```

- POST

```javascript
URL="/{id}"
header={
    token:""
}
params={
    name:""
}
```
- GET

```javascript
URL="/{id}"
header={
    token:""
}
```

- DELETE

```javascript
URL="/{id}"
header={
    token:""
}
```

## /salary

- PUT

```javascript
header={
    token:""
}
params={
    monthly_value:0,
    job_id:0
}
```

- POST

```javascript
URL="/{id}"
header={
    token:""
}
params={
    monthly_value:0,
    job_id:0
}
```
- GET

```javascript
URL="/{id}"
header={
    token:""
}
```

- DELETE

```javascript
URL="/{id}"
header={
    token:""
}
```

## /attendance/type

- PUT

```javascript
header={
    token:""
}
params={
    name:""
}
```

- POST

```javascript
URL="/{id}"
header={
    token:""
}
params={
    name:""
}
```
- GET

```javascript
URL="/{id}"
header={
    token:""
}
```

- DELETE

```javascript
URL="/{id}"
header={
    token:""
}
```

## /attendance

- PUT

```javascript
header={
    token:""
}
params={
    user_id:0,
    start_time:0,
    end_time:0,
    create_date:0,
    attendance_type_id:0
}
```

- POST

```javascript
URL="/{id}"
header={
    token:""
}
params={
    user_id:0,
    start_time:0,
    end_time:0,
    create_date:0,
    attendance_type_id:0
}
```
- GET

```javascript
URL="/{id}"
header={
    token:""
}
```

- DELETE

```javascript
URL="/{id}"
header={
    token:""
}
```