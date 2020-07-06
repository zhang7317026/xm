package com.zrz.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zrz.entity.ImgTablePO;
import com.zrz.service.ImgTableService;
import com.zrz.util.Base64Util;
import com.zrz.util.Constans;
import com.zrz.util.DateUtils;
import com.zrz.util.FileUtilsQN;
import com.zrz.util.MemcachedUtils;
import com.zrz.util.UUIDUtil;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(MemcachedUtils.class);
	@Autowired
	private ImgTableService imgTableService;
	
	//根据文件id查询文件信息
	@RequestMapping(value = "/getById", method = RequestMethod.POST)
	@ResponseBody
	public ImgTablePO getById(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
		ImgTablePO imgTablePO = new ImgTablePO();
		imgTablePO = imgTableService.getById(id);

		return imgTablePO;
	}
	
	//根据文件id删除文件信息
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public void deleteById(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
		imgTableService.deleteById(id);
	}
	

	// 文件上传
	@RequestMapping(method = RequestMethod.POST, value = "uploadFile")
	public @ResponseBody Map<String,Object> uploadFile(
			@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//组装新名称
		String fileName = file.getOriginalFilename();
		String[] fileTypeArgs = fileName.split("\\.");
		String fileType = fileTypeArgs[1];
		String fileNameNew = fileTypeArgs[0]+UUIDUtil.uuidRandom()+"."+fileType;
		
		//存放路径
		String realPath = request.getSession().getServletContext().getRealPath("upload");//实际图片文件夹路径  
		//上传到服务器本地
        File targetFile = new File(realPath, fileNameNew);
        //大小判断 500KB
        long fileSize = targetFile.length();
        if(fileSize>500*1024){
        	map.put("status", Constans.ERROR_01);
        	return map;
        }
        
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		FileUtilsQN.getUrl();
        //执行文件上传到云服务
    	try {
			FileUtilsQN.upload(realPath+"\\"+fileNameNew, fileNameNew);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("执行文件上传到云服务错误,路径:"+realPath+"\\"+fileNameNew, e);
			map.put("status", Constans.ERROR);
		}
    	
    	//获取文件路径等
    	String URL = FileUtilsQN.getUrl()+"/"+fileNameNew;
        String fileId = UUIDUtil.uuidRandom();
		
		//组装PO 保存
		ImgTablePO imgTablePO = new ImgTablePO();
		imgTablePO.setId(fileId);
		imgTablePO.setUrl(URL);
		imgTablePO.setFileName(fileNameNew);
		imgTablePO.setCreateTime(DateUtils.getTime());
		imgTablePO.setCreater("admin");
		int num = imgTableService.save(imgTablePO);
		
		//组装map返回值
		map.put("URL", URL);
		map.put("fileId", fileId);
		if(num>0){
			map.put("status", Constans.SUCCESS);
		}else{
			map.put("status", Constans.ERROR);
		}
		

		return map;
	}
	
	
	// base64文件上传
	@RequestMapping(method = RequestMethod.POST, value = "uploadFileBase64")
	@ResponseBody
	public Map<String,Object> uploadFileBase64(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		String base64Data = request.getParameter("base64Data");
        //数据判断
		if(base64Data == null || "".equals(base64Data)){
            map.put("status", Constans.ERROR);
            return map;
        }
		//统一转换为png文件
        String fileNameNew = UUIDUtil.generateShortUuid()+".png";
        //存放路径
      	String realPath = request.getSession().getServletContext().getRealPath("upload");//实际图片文件夹路径  
		Base64Util.GenerateImage(base64Data, realPath+"\\"+fileNameNew);
		
        //执行文件上传到云服务
    	try {
			FileUtilsQN.upload(realPath+"\\"+fileNameNew, fileNameNew);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("执行文件上传到云服务错误,路径:"+realPath+"\\"+fileNameNew, e);
			map.put("status", Constans.ERROR_02);
		}
    	
    	//获取文件路径等
    	String URL = FileUtilsQN.getUrl()+"/"+fileNameNew;
        String fileId = UUIDUtil.uuidRandom();
		
		//组装PO 保存
		ImgTablePO imgTablePO = new ImgTablePO();
		imgTablePO.setId(fileId);
		imgTablePO.setUrl(URL);
		imgTablePO.setFileName(fileNameNew);
		imgTablePO.setCreateTime(DateUtils.getTime());
		imgTablePO.setCreater("admin");
		int num = imgTableService.save(imgTablePO);
		
		//组装map返回值
		map.put("URL", URL);
		map.put("fileId", fileId);
		if(num>0){
			map.put("status", Constans.SUCCESS);
		}else{
			map.put("status", Constans.ERROR_03);
		}
		

		return map;
	}

}