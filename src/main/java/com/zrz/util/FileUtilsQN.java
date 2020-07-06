package com.zrz.util;

import java.io.IOException;

import com.qiniu.common.Config;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class FileUtilsQN {

	// 设置好账号的ACCESS_KEY和SECRET_KEY
	private static String ACCESS_KEY = "U4WZStrH3qjYryGIgxewRo5CnHg3ZRgrwzCqWhpO";
	private static String SECRET_KEY = "MYaUwDeMM0XtbMzbf0avsZtXRR5Zyh0NQti1So4Y";
	// 要上传的空间
	private static String bucketname = "caiwhere";
	//构造私有空间需要生成的下载的链接
	private static String URL = "http://osmipptm9.bkt.clouddn.com";
	// 密钥配置
	private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	// 创建上传对象
	private static UploadManager uploadManager = new UploadManager();

	/**
	 * 获取URL
	 */
	public static String getUrl() {
		return URL;
	}

	public static void main(String[] args) throws IOException {
		Config.zone = Zone.zone1(); 
		upload("C:/Users/Administrator/Desktop/1.jpg", "4.jpg");
	}
	/**
	 * 普通上传
	 */
	public static void upload(String filePath, String fileName)
			throws IOException {
		try {
			Config.zone = Zone.zone1(); 
			// 调用put方法上传
			Response res = uploadManager.put(filePath, fileName, getUpToken());
			// 打印返回的信息
			System.out.println("----------"+res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println("----------"+r.toString());
			try {
				// 响应的文本信息
				System.out.println("----------"+r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	// 简单上传，使用默认策略，只需要设置上传的空间名就可以了
	public static String getUpToken() {
		return auth.uploadToken(bucketname);
	}

	/**
	 * 覆盖上传
	 */
	public static void cover(String filePath, String fileName)
			throws IOException {
		try {
			Config.zone = Zone.zone1(); 
			// 调用put方法上传，这里指定的key和上传策略中的key要一致
			Response res = uploadManager.put(filePath, fileName,
					getUpToken2(fileName));
			// 打印返回的信息
			System.out.println(res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	// 覆盖上传
	public static String getUpToken2(String fileName) {
		// <bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
		// 第三个参数是token的过期时间
		return auth.uploadToken(bucketname, fileName, 3600, null);
	}

	/**
	 * 删除文件
	 */
	public static void delete(String fileName)
			throws IOException {
		try {
			Config.zone = Zone.zone1(); 
			// 实例化一个BucketManager对象
			BucketManager bucketManager = new BucketManager(auth);
			// 调用delete方法移动文件
			bucketManager.delete(bucketname, fileName);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
		}
	}

}
