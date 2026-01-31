# 序列化

## 策略
- implements Serializable 
  - transient 敏感字段，来忽略某些字段
  - 或使用标准的writeObject或者readObject方法来控制I/O
- implements externalizable
  - writeExternal，readExternal方法