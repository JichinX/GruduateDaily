### 考研日记Android Sqlite数据库设计

---



### 表一、用户信息

| 字段名       | 类型      | 备注      |
| --------- | ------- | ------- |
| id        | integer | 主键 ID   |
| name      | TEXT    | 姓名      |
| pwd       | TEXT    | 密码      |
| phone     | TEXT    | 手机号码    |
| v_code    | integer | 验证码     |
| v_code_ms | integer | 验证码有效时间 |

#### 表二、清单

| 字段名             | 类型      | 备注     |
| --------------- | ------- | ------ |
| id              | integer | 主键ID   |
| date_time_start | integer | 开始时间   |
| date_time_end   | integer | 结束时间   |
| is_important    | blob    | 是否是重要的 |
| is_urgent       | bolb    | 是否是紧急的 |
| content         | TEXT    | 清单内容   |

#### 表三、计划

| 字段名        | 类型      | 备注   |
| ---------- | ------- | ---- |
| id         | integer | 主键ID |
| label      | integer | 标签ID |
| time_start | integer | 开始时间 |
| time_end   | integer | 结束时间 |

#### 表四、标签

| 字段    | 数据类型    | 备注   |
| ----- | ------- | ---- |
| id    | integer | 主键ID |
| name  | TEXT    | 标签名  |
| color | integer | 颜色值  |

#### 表五、打卡

| 字段名       | 数据类型    | 备注   |
| --------- | ------- | ---- |
| id        | integer | 主键ID |
| date_time | integer | 打卡时间 |
| content   | TEXT    | 打卡内容 |
| picture   | TEXT    | 照片路径 |
