 function  tt() {
    $("#upload").click(function () {
        var formData = new FormData();
        formData.append("fileName", document.getElementById("file1").files[0]);
        $.ajax({
            url: "/fileUpload",
            type: "POST",
            data: formData,
            /**
             *必须false才会自动加上正确的Content-Type
             */
            contentType: false,
            /**
             * 必须false才会避开jQuery对 formdata 的默认处理
             * XMLHttpRequest会对 formdata 进行正确的处理
             */
            processData: false,
            success: function (dat) {
                var data = eval('(' + dat + ')');
                var list = data.list
                var str = "";
                $.each(list, function (key, value) {  //遍历键值对

                    str += "<tr><td>" + value.name + "</td>" + "<td><a href=\"/download?";
                    var a = "poth=" + value.id + "&name=" + value.name;
                    str += a.replace(/\s+/g, "");
                    str += "\">下载</a>";
                    str += "</td></tr>";

                })

                $('#list').html(str)
                alert("上传成功！");
            },
            error: function () {
                alert("上传失败！");

            }
        });
    });
};