
package com.wpp.controller;



import com.wpp.pojo.updateimg;
import com.wpp.service.UploadImg;
import com.wpp.utils.FastDFSClient;
import com.wpp.utils.JsonUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Controller
public class FileUpload {
    @Value("${TAOTAO_IMAGE_SERVER_URL}")
    private String TAOTAO_IMAGE_SERVER_URL;
    @Autowired
    private UploadImg service;

    @RequestMapping("/index")
    public String test( @RequestParam(value="startPage", required = false,defaultValue="1" )int startPage,
                        @RequestParam(value="PageSize", required = false,defaultValue="5")int PageSize, Map map) {
       PageHelper.startPage(startPage, PageSize);
        List<updateimg> list= service.getlixt();
        PageInfo<updateimg> pageInfo = new PageInfo<>(list,5);
        map.put("up", list);
        map.put("pageInfo", pageInfo);
        map.put("startPage", startPage);
        map.put("PageSize", PageSize);
        return "upimg";
    }
















    /**
     * @param file 要上传的文件
     * @return
     **/


    @RequestMapping("fileUpload")
    @ResponseBody
    public String upload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request) throws Exception {
        try {
            // 1.获取元文件的扩展名
            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 2.获取文件的字节数组
            byte[] bytes = file.getBytes();
            // 3.通过fastdfsclient的方法上传图片（参数要求有 字节数组 和扩展名 不包含"."）
            FastDFSClient client = new FastDFSClient("classpath:/fastdfs.conf");
            // 返回值：group1/M00/00/00/wKgZhVk4vDqAaJ9jAA1rIuRd3Es177.jpg
            String string = client.uploadFile(bytes, extName);
            // System.out.println(string);
            //拼接成完整的URL
            //"http://192.168.25.133/"
            String path = TAOTAO_IMAGE_SERVER_URL + string;
            // 4.成功时，设置map
            Map<String, Object> map = new HashMap<>();
            map.put("error", 0);
            map.put("url", path);
            updateimg u = new updateimg();
            u.setId(string);
            u.setName(originalFilename);
            u.setDate(new Timestamp(System.currentTimeMillis()));
            service.upimg(u);
            PageHelper.startPage(1, 5);
            List<updateimg> list= service.getlixt();
            PageInfo<updateimg> pageInfo = new PageInfo<>(list,5);
            map.put("list", list);
            // 6.返回map
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            // 5.失败时，设置map
            Map<String, Object> map = new HashMap<>();
            map.put("error", 1);
            map.put("message", "上传失败");
            return JsonUtils.objectToJson(map);
        }

    }

    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response, String poth, @Param("name") String name) {
        try {
            FastDFSClient client = new FastDFSClient("classpath:/fastdfs.conf");
            byte[] content = client.downloadFile(poth);//得到文件的字节数组
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //设置content-disposition响应头控制浏览器以下载的形式打开文件
            response.addHeader("Content-Disposition", "attachment;filename=" + name);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(content); // 输出数据
            outputStream.flush();
            outputStream.close();


        } catch (Exception e) {
            System.out.println(e);

        }
    }


}

