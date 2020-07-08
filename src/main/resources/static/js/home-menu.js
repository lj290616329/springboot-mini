layui.config({
    base: '/layuiadmin/' //静态资源所在路径
    ,version:'132'
}).extend({
    index: 'lib/index' //主入口模块,
    ,CoreUtil : 'core-util/CoreUtil'
}).use(['index','laytpl'],function(){
    var element = layui.element;
    var $ = layui.jquery;
    $.ajax({
        url:"/manager/home/menus",
        type:"get",
        dataType:"json",
        success:function(res){
            var data=res.data;
            if(data!= "" && data.length>0){
                var ulHtml = '';
                if(data!= null&&data.length > 0){

                    $.each(data,function(index,item){
                        ulHtml += '<li class="layui-nav-item">';
                        ulHtml += '<a href="javascript:;">';
                        ulHtml += '<i class="layui-icon '+item.icon+'"></i><cite>'+item.title+'</cite>';
                        ulHtml += '</a>'
                        if(item.children != null && item.children.length > 0){
                            ulHtml += '<dl class="layui-nav-child">';
                            $.each(item.children,function(index,child){
                                if(child.children !=null&& child.children.length>0){
                                    ulHtml +='<i class="layui-icon '+child.icon+'"></i><a>'+child.title+'</a>';
                                    ulHtml +=getChild(child.children,"");
                                }else {
                                    ulHtml += '<dd><a lay-href="'+child.url+'" data-title="'+child.title+'" data-id="'+child.id+'" class="menuNvaBar">';
                                    ulHtml += '<cite>'+child.title+'</cite></a></dd>';
                                }

                            });
                            ulHtml += "</dl>"
                        }
                        ulHtml += '</li>'
                    });
                };
                $("#LAY-system-side-menu").append(ulHtml);
                element.init();
            }else{
                $("#LAY-system-side-menu").empty();
            }
        }
    });
    //递归获取无限层级
    var getChild=function (item,ulHtml) {
        ulHtml += '<dl class="layui-nav-child">';
        $.each(item,function (index,child) {
            if(child.children !=null&& child.children.length>0){
                ulHtml +='<i class="layui-icon '+child.icon+'"></i><a>'+child.title+'</a>';
                ulHtml +=getChild(child.children,"");
            }else {
                ulHtml += '<dd><a lay-href="'+child.url+'" data-title="'+child.title+'" data-id="'+child.id+'" class="menuNvaBar">';
                ulHtml += '<i class="layui-icon '+child.icon+'"></i><cite>'+child.title+'</cite></a></dd>';
            }
        });
        ulHtml += "</dl>"
        return ulHtml;
    };
})