# Distribute-cloud-hub-rpc(Dch-rpc✨)

### Feature✍️

##### Protocol

- Support HTTP protocol.
- Create special protocol which name is "Dch".

```
Dch Request Protocol：
-----------------------------------------------------------------------------------------
| magic(4)| version(1)|stype(1) |ctype(1) | headLen(4)| len(4)| reqId(8)| timestamp(8)  |
-----------------------------------------------------------------------------------------
|                                                                                       |
|                                                                                       |
|                                      body                                             | 
|                                                                                       |
|                                                                                       |
-----------------------------------------------------------------------------------------

Dch Response Protocol：
-----------------------------------------------------------------------------------------
|magic(4)|version(1)|code(1)|stype(1)|ctype(1)|headLen(4)|len(4)|resId(8)| timestamp(8) |
-----------------------------------------------------------------------------------------
|                                                                                       |
|                                                                                       |
|                                      body                                             | 
|                                                                                       |
|                                                                                       |
-----------------------------------------------------------------------------------------
```

##### Compress

- Support Gzip compress.

##### Serialize

- Support Json Serialize.
- Support "Google Protobuf" Serialize.
- Support String Serialize.
- Support Non Serialize.

##### Invoke

- Support Http Invoke.
- Support "Dch" Invoke between method and method.
- Support Invoke base on annotate



