namespace com.senpure.io.server;

bean HandleMessage {
    int     handleMessageId = 1;          //可以处理的消息ID
    String  messageName     = 2;          //消息名提高可读性
    boolean direct          = 3;          //true网关直接选择服务器转发，false 网关会对所有处理该消息的服务器进行一次询问
}

bean IdName {
    int    id          = 1;               //消息id
    String messageName = 2;               //有意义的字符串
}

bean Statistic {
    int score = 1;                        //分数0-100
}

bean Consumer {
    long token  = 1;
    long userId = 2;
}

//框架内部验证
message CS FrameworkVerify 101 {
    String serverName   = 1;              //服务名
    String serverKey    = 2;              //服务实例唯一标识
    String serverType   = 3;              //服务类型
    String serverOption = 4;              //服务扩展字段
    String ip           = 5;              //ip
    String userName     = 6;              //账号名
    String userType     = 7;              //账号类型
    String password     = 8;              //账号密码
    String token        = 9;              //账号token
}

//框架内部验证结果
message SC FrameworkVerify 102 {
    boolean success = 1;                  //
    String  message = 2;                  //message
    String  userId  = 3;                  //认证分配的id
}

//网关注册处理消息返回
message CS RegServerHandleMessage 103 {
    boolean success = 1;
    String  message = 2;
}

//服务器注册消息处理器到网关
message SC RegServerHandleMessage 104 {
    String           serverName         = 1; //服务名
    String           serverKey          = 2; //服务实例唯一标识
    String           serverType         = 4; //服务类型
    String           serverOption       = 5; //服务扩展字段
    String           readableServerName = 6; //服务名
    HandleMessage [] messages           = 7; //可以处理的消息
}

//向网关表示自己可以提供框架内部验证功能
message CS FrameworkVerifyProvider 105 {
    String serverName   = 1;              //服务名
    String serverKey    = 2;              //服务实例唯一标识
    String serverType   = 4;              //服务类型
    String serverOption = 5;              //服务扩展字段
}

//通知网关数字id与字符串的关联
message SC IdName 108 {
    IdName [] idNames = 1;
}

//关联用户与网关
message CS RelationUserGateway 109 {
    long token         = 1;               // channel token
    long userId        = 2;               //userId
    long relationToken = 3;               //relation token
}

message SC RelationUserGateway 110 {
    long token         = 1;               // channel token
    long userId        = 2;               //userId
    long relationToken = 3;               //relation token
}

message CS BreakUserGateway 111 {
    long   token         = 1;             //channel token
    long   userId        = 2;             //用户Id
    long   relationToken = 3;             //relation token
    String type          = 4;             //error,userChange,userOffline
}

//断开用户与网关
message SC BreakUserGateway 112 {
}

message CS AskHandle 113 {
    long  askToken      = 1;              //  askToken
    int   fromMessageId = 2;
    bytes data          = 3;
}

message SC AskHandle 114 {
    boolean handle        = 1;            //是否可以处理
    long    askToken      = 2;            // token
    int     fromMessageId = 3;
    String  askValue      = 4;            //值
}

message SC KickOff 116 {
    long token;                           // token
    long userId;                          //userId
}

message SC Statistic 118 {
    Statistic statistic = 1;
}

message SC MessageForward 120 {
    String serverName   = 1;              //服务名
    String serverKey    = 2;              //服务实例唯一标识
    String serverType   = 3;              //服务类型
    String serverOption = 4;              //服务扩展字段
    int    id           = 5;              //messageId
    bytes  data         = 6;              //message data
}

//加入匹配
message CS Matching 121 {
    Consumer [] consumers = 1;
}

//加入匹配
message SC Matching 122 {
    boolean success = 1;
}

#以下是客户端会用到的

//心跳
message CS Heart 65 {
}

//心跳
message SC Heart 66 {
}

//框架错误提示
message SC FrameworkError 100 {
    String    code;                       //错误码
    String    message;                    //提示内容
    String [] args;                       //参数
}