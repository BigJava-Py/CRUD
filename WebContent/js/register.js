var t1 = false;
var t2 = false;
var t3 = false;
var t4 = false;
var t5 = false;


function fillProvince() {
    $.ajax({
        type: "post",
        url: "queryProvinceCity.do",
        dataType: "json",
        success: function(response) {
            var provinceElement = document.getElementById("province");
            //清除select的所以option
            provinceElement.DOCUMENT_POSITION_DISCONNECTED.length = 0;
            //增加一个选项
            provinceElement.add(new Option("请选择省份", ""));
            //循环增加其它所有选项
            for (index = 0; index < response.length; index++) {
                provinceElement.add(new Option(response[index].proName, response[index].provinceid));
            }
        }
    });
}

$(document).ready(function() {
    fillProvince();
    $("#province").change(function(e) {
        $("#city").empty();
        $("#city").append($("<option>").val("").text("请选择城市"));
        if ($(this).val() == "") {
            $("#provinceError").css("color", "#c00202");
            $("#provinceError").text("必须选择省份!");
            return;
        }
        province_correct = true;
        $("#provinceError").text("");
        var provinceCode = $("#province").val();
        $.ajax({
            type: "post",
            url: "queryProvinceCity.do",
            data: { provinceCode: provinceCode },
            dataType: "json",
            success: function(response) {
                for (index = 0; index < response.length; index++) {
                    var option = $().val().text(response[index].cityName);
                    $("#city").append(option);
                }
            },

        })
    })
})

//邮箱地址
$(document).ready(function() {
    $("#mail").focus(function() {
        t2 = false;
        $(this).css("background-color", "#cccccc");
        var value = $("#mail").val();
        if (!/^\w+@\w+.\w+$/.test(value)) {
            $("#mailError").text("格式不正确");
        }
        $("#mail").blur(function() {
            $.ajax({
                type: "post",
                url: "MailController.do",
                data: { mail: $("#mail").val() },
                dataType: "json",
                success: function(response) {
                    t2 = true;
                    $("#mailError").text(response.info);
                }
            });
        })
    });
})


$(document).ready(function() {
    //用户名
    $("#name").focus(function() {
        $(this).css("background-color", "#cccccc");
        $("#name").blur(function() {
            t1 = false;
            $.ajax({
                type: "post",
                url: "NameController.do",
                data: { name: $("#name").val() },
                dataType: "json",
                success: function(response) {
                    t1 = true;
                    $("#nameError").text(response.info);
                }
            });
        })
    });

    //密码
    $("#password1").focus(function() {
        $("#password1").blur(function() {
            t3 = false;
            var password1 = $("#password1").val();

            if (password1 == "") {
                $("#passwordError").text("密码不能为空");

            } else if (password1.length < 4) {
                $("#passwordError").text("密码长度不能小于4位");
            } else {
                t3 = true;
                passwords = password1;
                $("#passwordError").text("密码符合要求!");
            }
        })

    });

    //确认密码
    $("#password2").focus(function() {
        $("#password2").blur(function() {
            t4 = false;
            var password22 = document.getElementById("password2").value;

            if ((password22.trim()) === "") {
                $("#password2Error").text("密码不能为空");
            } else if (password22 != $("#password1").val()) {
                $("#password2Error").text("密码不一致");
            } else {
                t4 = true;
                $("#password2Error").text("密码符合要求!");
            }
        })
    });

    //中文名
    $("#realname").focus(function() {
        $("#realname").blur(function() {
            t5 = false;
            var re = /[^\u4e00-\u9fa5]{1,}/;
            var realname = document.getElementById("realname").value;

            var result = re.test(realname);
            if ((realname.trim()) === "") {
                $("#realnameError").text("真实姓名不能为空!");
            } else if (result == false) {
                $("#realnameError").text("真实姓名必须为中文!");
            } else {
                t5 = true;
                $("#realnameError").text("真实姓名符合要求!");
            }

        })
    })

})

function ajaxRegister() {
    if (t1 == true && t2 == true && t3 == true && t4 == true && t5 == true) {
        $.ajax({
            type: "post",
            url: "RegisterController.do",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            data: { name: $("#name").val(), realname: $("#realname").val(), mail: $("#mail").val(), province: $("#province").val(), city: $("#city").val(), password: $("#password2").val() },
            dataType: "json",
            success: function(data) {
                if (data.code == 1) {
                    alert("注册成功!");
                    window.location.href = "login.html";
                } else {
                    alert("注册失败！请重新注册！");
                }
            }
        });
    }

}

//确认密码



//邮箱地址

//中文名