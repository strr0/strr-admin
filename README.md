## 平台简介

[![strr-admin](https://img.shields.io/badge/strr_admin-1.0-success.svg)](https://github.com/strr0/strr-admin)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2022-blue.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-blue.svg)]()
[![JDK-17](https://img.shields.io/badge/JDK-17-green.svg)]()
[![GitHub](https://img.shields.io/github/stars/strr0/strr-admin.svg?style=social&label=Stars)](https://github.com/strr0/strr-admin)

> strr-admin 微服务通用权限管理系统

## 快速开始

### 核心依赖

| 依赖                          | 版本         |
|-----------------------------|------------|
| Spring Boot                 | 3.1.7      |
| Spring Cloud                | 2022.0.4   |
| Spring Cloud Alibaba        | 2022.0.0.0 |
| Spring Authorization Server | 1.1.4      |
| Mybatis                     | 3.5.13     |
| Vue                         | 3.4.35     |
| Naive UI                    | 2.39.0     |

### 模块说明

```
strr-admin
├── strr-boot -- 单体模式[8080]
└── strr-api -- 接口模块
     ├── strr-api-bom -- 接口依赖管理
     ├── strr-api-annotation -- 接口注解
     ├── strr-api-system -- 系统模块接口
├── strr-auth -- 授权服务[8082]
└── strr-common -- 公共模块
     ├── strr-common-bom -- 公共依赖管理
     ├── strr-common-alibaba-bom -- 公共阿里依赖管理
     ├── strr-common-core -- 公共核心
     ├── strr-common-dubbo -- 远程接口调用
     ├── strr-common-dubbo-oauth2 -- 远程接口调用认证
     ├── strr-common-mybatis -- mybatis 扩展封装
     ├── strr-common-web -- web 封装
     ├── strr-common-security -- 安全封装
     ├── strr-common-oauth2 -- 授权封装
     └── strr-common-oss -- 文件封装
├── strr-gateway -- 网关服务[8080]
└── strr-module -- 通用模块
     ├── strr-system -- 系统模块[8081]
     ├── strr-data -- 数据模块[8084]
     └── strr-resource -- 资源模块[8083]
└── strr-visual
     └── strr-nacos -- 注册中心[8848]
```

### Sql

导入 script/sql 底下的 sql 文件（mysql）

### Nacos

启动 strr-nacos 服务

将 config/nacos 底下的配置文件配置到对应的位置

### 服务启动

- strr-system
- strr-auth
- strr-gateway
- strr-data(optional)
- strr-resource(optional)