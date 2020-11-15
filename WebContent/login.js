//点击验证码图片



var xmlHttp;

function createXmlHttp() {
    if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
    } else {
        xmlHttp = new ActiveXObject("microsoft.XMLHTTP");
    }
}


/*
$("input").blur(function(){
    createXmlHttp();
    xmlHttp.open("post", "ajaxCheckLogin.do", true);
    xmlHttp.setRequestHeader("Content-Type", "application/X-www-form-urlencoded");
    var data=document.getElementById.value;
    xmlHttp.send(data);


    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            var info = xmlHttp.responseText;
            var obj = JSON.parse(info);
            if (obj.code == 0) {
                
            } else {
                document.getElementById("checkError").innerText = json.info;
            }
        }
    }
})
*/


function ajaxCheckLogin() {

    //取表单数据
    var username = document.getElementById("name").value;

    var password = document.getElementById("password").value;

    var code = document.getElementById("userCode").value;

    var login = document.getElementById("box");
    alert(username + password + code + login);
    //1.创建XNLHttpResquest对象
    createXmlHttp();
    //2.发送请求
    xmlHttp.open("post", "AjaxLoginCheck.do", true);
    //2.2设置请求头，使用缺省的
    xmlHttp.setRequestHeader("Content-Type", "application/X-www-form-urlencoded");
    //2.3:key-value对的字符串
    var data = "username=" + username + "&password=" + password + "&vcode=" + code + "&box=" + login;

    xmlHttp.send(data);

    //3
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            var info = xmlHttp.responseText;
            var obj = JSON.parse(info);
            if (obj.code == 0) {
                window.location.href = "\main.jsp";
            } else {
                document.getElementById("checkError").innerText = obj.info;
            }
        }
    }
}

function jqajaxCheckLogin() {
    $.ajax({
        type: "post",
        url: "AjaxLoginCheck.do",
        data: { name: $("#name").val(), password: $("#password").val(), userCode: $("#userCode").val(), box: $("#box").val() },
        dataType: "json",
        success: function(response) {
            if (response.code == 0) {
                window.location.href = "main.jsp";
            } else {
                //alert(response.info);
                $("#checkError").text(response.info);
            }
        }
    });
}