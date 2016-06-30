 /*
@params author wangyanxiao
@params date 2016-6-8
 */
 var api = new API();
 $(function(){
 	/*顶部导航右侧用户信息图标下拉显示*/
    $(".user").hover(function(){
        $(this).parent().find("ul.userlist").css("display","block");
    },function(){
        $(this).parent().find("ul.userlist").css("display","none");
    });
})

/*
*分页
*/
function pagination(container, callback, currentPage, pageCount){
    var options = {
        bootstrapMajorVersion: 2, //版本
        currentPage: currentPage, //当前页数
        totalPages: pageCount, //总页数

       //点击事件，用于通过Ajax来刷新整个list列表
        onPageClicked : callback
        /*function(event, originalEvent, type, page)*/
    };
    $('#'+container).bootstrapPaginator(options);
}
var uuid = sessionStorage.getItem("uuid_id");
//console.log(uuid);
if(uuid ==  null || uuid == "undefined"){
    //alert("用户没有登录，无法访问页面,跳转登录页面！");
    window.location.href="login.html";
}
//所有页面右上角显示
var userName = sessionStorage.getItem("userName");
var userPwd = sessionStorage.getItem("userPwd");

indexPage(userName);

function indexPage(name) {
    $("#userName_login").append('<span>' + userName + '</span>');
    $("#userInfoPerson").append('<div class="user-right-box"><div class="user c-main"> <i class="user-icon icon"></i><ul class="userlist"><li><a href = "personal.html"><i class = "pCenter icon"></i>个人中心</a></li><li  id="logout"><a><i class = "exitLogin icon"></i>退出登录</a></li></ul></div></div>');

    //退出登录
    $("#logout").click(function() {
        console.log("退出登录");
        logout(); //注销

        sessionStorage.clear(); //清空用户登录信息
        api.windowLogin();
        //window.location.href = "login.html";
    })
}
/*
 *退出登录
 */
function logout() {
    api.logout().done(function(data) {
        console.log("data");
    })
}
