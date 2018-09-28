## 运行环境

- Java：1.8.0_181
- MySQL：5.7.23
- Tomcat：8.5.34

## 接口

## /auth

- PUT：注册

```javascript
params={
  "username":"",
  "real_name":"",
  "password":"",
  "role_id":0
}
```

- POST：登录

```javascript
params={
  "username":"",
  "password":""
}
```

## /users

- GET：获取用户信息

```javascript
URL=/{id}
header={
  "token":""
}
```

- POST：修改用户信息

```javascript
URL=/{id}
header={
  "token":""
}
params={
  "username":"",
  "real_name":"",
  "password":"",
  "role_id":0
}
```

- DELETE：删除用户信息

```javascript
URL=/{id}
header={
  "token":""
}
```

## /role


- GET：获取角色信息

```javascript
URL=/{id}
header={
  "token":""
}
```

- DELETE：删除角色信息

```javascript
URL=/{id}
header={
  "token":""
}
```