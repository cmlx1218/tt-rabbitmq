# tt-rabbitmq(配置多ip rabbitmq)  

主要主要@Primary、@Qualifier注解的使用  
@Primary：优先注入该注解的标注的bean  
@Qualifier：确定注入该注解标定的bean  

多ip 的 RabbitMQ需要在代码里面创建交换机和队列，不然会创建到我们加了@Primary注解的地址上去  
且持久化的顺序为 exchange、queue、binding  
