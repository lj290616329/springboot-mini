layui.use(['layim','upload','CoreUtil'],function(){
    var layim = layui.layim,CoreUtil = layui.CoreUtil,ws,cachedata =  layui.layim.cache();
    layim.config({
        //初始化接口
        init: {
            url: '/manager/chat/init'
            ,data: {}
        }
        //上传图片接口
        ,uploadImage: {
            url: '/api/upload/file/qiniu' //（返回的数据格式见下文）
            ,type: '' //默认post
        }

        //上传文件接口
        ,uploadFile: {
            url: '/api/upload/file/qiniu' //（返回的数据格式见下文）
            ,type: '' //默认post
        }
        ,isAudio: true //开启聊天工具栏音频

        ,isVideo: true //开启聊天工具栏视频

        //扩展工具栏
        ,tool: [{
            alias: 'code'
            ,title: '代码'
            ,icon: '&#xe64e;'
        }]
        ,isgroup:false
        //,brief: true //是否简约模式（若开启则不显示主面板）
        ,title: 'My_IM' //自定义主面板最小化时的标题
        //,right: '100px' //主面板相对浏览器右侧距离
        //,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
        //,initSkin: '6.jpg' //1-5 设置初始背景
        //,skin: ['aaa.jpg'] //新增皮肤
        //,isfriend: false //是否开启好友
        //,isgroup: false //是否开启群组
        ,min: false //是否始终最小化主面板，默认false
        //,notice: true //是否开启桌面消息提醒，默认false
        //,voice: 'default.wav' //声音提醒，默认开启，声音文件为：default.wav
        //,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
        //,find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
        ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatLog.html' //聊天记录页面地址，若不开启，剔除该项即可
    });
    //面板初始化时候获取用户id
    layim.on("ready", function (res) {
        if(res.mine && res.mine.id!=undefined){
            connect(res.mine.id)
        }
    });

    //发送消息
    layim.on("sendMessage", function (res) {
        var To = res.to;
        var Mine = res.mine;
        console.log(res);
        var msg = {"type":"chatMessage","data":{
                "username":Mine.username,
                "avatar":Mine.avatar,
                "id":To.id,
                "type":"friend",
                "content":Mine.content,
                "fromid":Mine.id,
                "toid":To.id
            }};
        ws.send(JSON.stringify(msg));
    });

    //修改在线状态
    layim.on('online', function (res) {
        console.log(res);
        if(ws.readyState==1 && res=="hide"){
            ws.close();
            layer.msg("您已隐身,无法接收消息");
        };
        if(ws.readyState!=1 && res=="online"){
            connect(cachedata.mine.id);
        };
    });
    //修改签名
    layim.on('sign', function (value) {
        CoreUtil.sendAjax("/manager/chat/sign",JSON.stringify({id:layui.layim.cache().mine.id,sign:value}),function (res) {
            if(res.code==0){
                console.log("success");
            }
        },"PUT",'application/json; charset=UTF-8',false);
    });

    var connect = function (uid) {
        var wsUrl = window.location.origin.replace("http","ws")+"/websocket/"+uid;
        ws = new ReconnectingWebSocket(wsUrl, null, {debug: true, reconnectInterval: 3000});
        ws.onmessage = function (ev) {
            console.log(ev);
            var chat = JSON.parse(ev.data);
            if (chat['type'] === 'chatMessage') {
                layim.getMessage(chat['data']);
            }
        };
        ws.onopen = function (evt) {
            console.log("建立链接");
        };
        // 断开 web socket 连接成功触发事件
        ws.onclose = function (evt) {
            console.log("关闭!!!")
        };
        // 通信发生错误时触发
        ws.onerror = function (evt) {
            console.log('通信错误：' + evt.data);
        };

        window.onbeforeunload = function() {
            ws.close();
        };
    };
});

































