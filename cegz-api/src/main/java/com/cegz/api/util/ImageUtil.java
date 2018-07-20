package com.cegz.api.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图像处理工具
 * 
 * @author lijiaxin
 * @date 2018年7月20日
 */
public class ImageUtil {
	/**
	 *  根目录
	 */
	private static String imgRootDir;
	
	/**
	 * 驾驶证图片目录
	 */
	private static String imgDriveLicenseDir;
	
	/**
	 * 行驶证图片目录
	 */
	private static String imgDriveRegistrationDir;
	
	/**
	 * 身份证图片目录
	 */
	private static String imgIdCardDir;
	

	/**
	 * 保存图片
	 * 
	 * @param multipartFile
	 * @param path
	 * @return
	 * @throws IOException
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	public static String saveImg(MultipartFile multipartFile, String path) throws IOException {

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
		String fileName = UUID.randomUUID() + ".png";
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
		byte[] bs = new byte[1024];
		int len;
		while ((len = fileInputStream.read(bs)) != -1) {
			bos.write(bs, 0, len);
		}
		bos.flush();
		bos.close();
		return fileName;
	}

	public static String getRootImgDir() throws FileNotFoundException {
		if (imgRootDir != null) {
			return imgRootDir;
		}
		File path = new File(ResourceUtils.getURL("classpath:").getPath());
		if (!path.exists())
			path = new File("");
		String dir = path.getAbsolutePath();
		System.out.println(dir);
		return dir;
	}
	
	/**
	 * 获取驾驶证上传图片目录
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	public static String getDriveLicenseImgDir() throws FileNotFoundException {
		if (imgDriveLicenseDir != null) {
			return imgDriveLicenseDir;
		}
		String root = getRootImgDir();
		File upload = new File(root, Constant.STATIC_DIR + Constant.DRIVE_LICENSE_IMG_DRI);
		if (!upload.exists())
			upload.mkdirs();
		String url = upload.getAbsolutePath();
		imgDriveLicenseDir = url;
		System.out.println("upload url:" + url);
		return url;
	}
	
	/**
	 * 获取行驶证上传图片目录
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	public static String getDriveRegistrationImgDir() throws FileNotFoundException {
		if (imgDriveRegistrationDir != null) {
			return imgDriveRegistrationDir;
		}
		String root = getRootImgDir();
		File upload = new File(root, Constant.STATIC_DIR + Constant.DRIVE_REGISTRATION_IMG_DRI);
		if (!upload.exists())
			upload.mkdirs();
		String url = upload.getAbsolutePath();
		imgDriveRegistrationDir = url;
		System.out.println("upload url:" + url);
		return url;
	}
	
	/**
	 * 获取身份证上传图片目录
	 * 
	 * @return
	 * @throws FileNotFoundException
	 * @author lijiaxin
	 * @date 2018年7月20日
	 */
	public static String getIdCardImgDir() throws FileNotFoundException {
		if (imgIdCardDir != null) {
			return imgIdCardDir;
		}
		String root = getRootImgDir();
		File upload = new File(root, Constant.STATIC_DIR + Constant.CARD_IMG_DRI);
		if (!upload.exists())
			upload.mkdirs();
		String url = upload.getAbsolutePath();
		imgIdCardDir = url;
		System.out.println("upload url:" + url);
		return url;
	}
}
