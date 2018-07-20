package com.cegz.api.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cegz.api.dao.ContactsRepository;
import com.cegz.api.model.Contacts;
import com.cegz.api.util.ImageUtil;
import com.cegz.api.util.ResultData;

/**
 * 测试
 * @author lijiaxin
 * @date 2018年7月19日
 */
@RestController
@RequestMapping("test")
public class Test {
	@Autowired
	private ContactsRepository contactsRepository;
	@GetMapping("/insert")
	public ResultData insertData() {
		ResultData resultData = new ResultData();
//		resultData.setCode(200);
//		Contacts contacts = new Contacts();
//		contacts.setCreateTime(new Date());
//		contacts.setCreateUserId(1);
//		contacts.setUpdateUserId(1);
//		contacts.setDrivingLicenseId(1);
//		contacts.setDrivingRegistrationId(1);
//		contacts.setIdcardId(1);
//		contacts.setPhone("15730472271");
//		contacts.setPictureUrl("url");
//		contactsRepository.save(contacts);
		return resultData;
	}
	@PutMapping("img/upload")
    public String uploadImg(@RequestParam("editormd-image-file") MultipartFile multipartFile, String name, String age)  {
      
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
           return "-1";
        }
      //  String root_fileName = multipartFile.getOriginalFilename();
        //处理图片
        //User currentUser = userService.getCurrentUser();
        //获取路径
        //String return_path = ImageUtil.getFilePath(currentUser);
        //String filePath = location + return_path;
        String filePath = null;
		try {
			filePath = ImageUtil.getIdCardImgDir();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //logger.info("图片保存路径={}", filePath);
        String file_name = null;
        try {
            file_name = ImageUtil.saveImg(multipartFile, filePath);
            System.out.println(name+age);
            System.out.println(file_name);
            return "1";
        } catch (IOException e) {
        	e.printStackTrace();
           return "0";
        }
    }
}
