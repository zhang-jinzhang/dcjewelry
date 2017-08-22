package com.ceyi.project.dcjewelry.util;


import com.ceyi.project.dcjewelry.picture.NewImage;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

	private static String realPath;

	/**
	 * 判断是否是图片文件后缀名
	 * @param filename
	 * @return
	 */
	public static boolean isImgExt(String filename){
		String[] exts = {"BMP", "JPG", "JPEG", "PNG", "GIF"};
		String ext = getFileExt(filename).toUpperCase();
		return StringUtil.strInArray(exts, ext);
	}

	/**
	 * 是否是mp4
	 */
	public static boolean isMp4(String filename){
		String[] exts = {"MP4"};
		String ext = getFileExt(filename).toUpperCase();
		return StringUtil.strInArray(exts, ext);
	}

	/**
	 * 获取文件后缀名
	 * @param filename
	 * @return
	 */
	public static String getFileExt(String filename){

		if(filename!=null && !"".equals(filename) && filename.indexOf(".")>-1){
			return filename.split("\\.")[1];
		}
		return "";
	}

	public static NewImage createNewImgFilePath(HttpServletRequest request){
		return createNewImgFilePath("cmm", ".png" ,request);
	}

	public static NewImage createNewImgFilePath(String dir, HttpServletRequest request){
		return createNewImgFilePath(dir, ".png" ,request);
	}

	public static NewImage createNewImgFilePath(String dir, String ext, HttpServletRequest request){
		String imgName = System.currentTimeMillis()+ext;
		String imgPath = request.getSession().getServletContext()
				.getRealPath(FileUtil.getImgDirPath(dir))
				+ "/" + imgName;
		String url = request.getContextPath() + "/"+FileUtil.getImgDirPath(dir)
				+ "/" + imgName;

		File file = new File(imgPath);
		if(!file.getParentFile().isDirectory()){
			file.getParentFile().delete();
		}
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}

		NewImage newImage = new NewImage(imgPath, url, imgName, ext);
		return newImage;
	}

	public static String getImgDirPath(String dir){
		return ""+dir;
	}

	public static String getTempFile(HttpServletRequest request, String ext, File reFile){
		String fname = System.currentTimeMillis()+ext;
		String fpath = request.getSession().getServletContext()
				.getRealPath("temp")
				+ "/" + fname;
		File file = new File(fpath);
		if(!file.getParentFile().isDirectory()){
			file.getParentFile().delete();
		}
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		reFile = file;
		String url = request.getContextPath() + "/"+request.getSession().getServletContext().getRealPath("temp")
				+ "/" + fname;
		return url;
	}

	/**
	 * 获取文件的项目绝对路径
	 * @param fpath 相对路径
	 * @return
	 */
	public static String getAbsFilePath(String fpath){
		if(realPath == null){
    		realPath = FileUtil.class.getResource("").getPath().replace("\\", "/")
    				.split("/WEB-INF/classes/")[0];
    		if(realPath.contains(":/")){
    			realPath = realPath.substring(1);
    		}
//    				ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("");
    	}

        if (fpath.startsWith("/"))
            return realPath + fpath;
        else
            return realPath +"/"+ fpath;
	}
	public static File getAbsFile(String fpath){
		File file = new File(getAbsFilePath(fpath));
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		return file;
	}
	public static void main(String[] args) {
//		System.out.println(isImgExt("fdajpG.bmpf"));
		System.out.println(getAbsFilePath("web.xml"));
		System.out.println(createImgAbsPath(".jpg"));
	}

	public static String getFname(String fpath) {
		if(fpath==null || "".equals(fpath))return "";
		String ss = fpath.replace("\\", "/");
		String[] arr = ss.split("/");
		return arr[arr.length-1];
	}

	/**
	 * 把数据写入文件
	 * @param f
	 * @param data
	 * @return
	 */
	public static String writeToFile(File f, String data){
		try {
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			if(!f.exists()){
				f.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(data.getBytes("utf-8"));
			fos.flush();
			fos.close();
			return f.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String createImgPath(String ext) {
		return "/"+getImgDirPath("upload")+"/"+System.currentTimeMillis()+ext;
	}
	public static String createImgAbsPath(String ext) {
		return getAbsFilePath(createImgPath(ext));
	}
}
