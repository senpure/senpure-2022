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

//网关注册处理消息返回
message CS RegServerHandleMessage 101 {
    boolean success = 1;
    String  message = 2;
}

//服务器注册消息处理器到网关
message SC RegServerHandleMessage 102 {
    String           serverName         = 1; //服务名
    String           serverKey          = 2; //服务实例唯一标识
    String           readableServerName = 3; //服务名
    HandleMessage [] messages           = 4; //可以处理的消息
}

//数字id与字符串的关联
message SC IdName 104 {
    IdName [] idNames = 1;
}

//关联用户与网关
message CS RelationUserGateway 105 {
    long channelToken  = 1;               // channel token
    long userId        = 2;               //userId
    long relationToken = 3;               //relation token
}

message SC RelationUserGateway 106 {
    long channelToken  = 1;               // channel token
    long userId        = 2;               //userId
    long relationToken = 3;               //relation token
}

message CS BreakUserGateway 107 {
    long   channelToken  = 1;             //channel token
    long   userId        = 2;             //用户Id
    long   relationToken = 3;             //relation token
    String type          = 4;             //error,userChange,userOffline
}

//断开用户与网关
message SC BreakUserGateway 108 {
}

message CS AskHandle 109 {
    long   askToken      = 1;             //  askToken
    int    fromMessageId = 2;
    String askValue      = 3;             //值
}

message SC AskHandle 110 {
    boolean handle        = 1;            //是否可以处理
    long    askToken      = 2;            // token
    int     fromMessageId = 3;
    String  askValue      = 4;            //值
}

#以下是客户端会用到的

//心跳
message CS Heart 65 {
}

//心跳
message SC Heart 66 {
}

//服务器内部错误提示
message SC InnerError 100 {
    String    result;                     //错误码
    String    message;                    //提示内容
    String [] args;                       //参数
}