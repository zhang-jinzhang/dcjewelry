package com.ceyi.project.dcjewelry.util;
import org.apache.log4j.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VideoUtil {

	private static final Logger log = Logger.getRootLogger();
	public static final String ffmpeg_path = "/opt/app/dachao/webapps/ROOT/upload/ffmpeg/ffmpeg";
	public static void main(String[] args) throws Exception {
//		String f = "C:/Users/Administrator/Desktop/temp/1.mp4";
//		String d = "C:/Users/Administrator/Desktop/temp/11.jpg";
//		cutImg(f, d);
		
		int a = getAmrDuration(new File("C:/Users/Administrator/Desktop/1.amr"));
		System.out.println(a);
	}
	
	/**
	 * 视频截图
	 */
		public static boolean cutImg(String video_Path, String img_Path) {
			File imgFile = FileUtil.getAbsFile(img_Path);
			List commend = new java.util.ArrayList();
			commend.add(ffmpeg_path);
			commend.add("-i");
			commend.add(video_Path);
			commend.add("-y");
			commend.add("-f");
			commend.add("image2");
			commend.add("-ss");
			commend.add("0.510");
			commend.add("-t");
			commend.add("0.001");
			commend.add("-s");
			commend.add("352x201");
			commend.add(imgFile.getAbsolutePath());
			try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
			return false;
		}
	}
	
	public static long getAudioSeconds(String filepath){
		try {
			File file = new File(filepath);
			Clip clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			clip.open(ais);
			long s = clip.getMicrosecondLength() / 1000000;
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	 /**
     * 得到amr的时长
     * 
     * @param file
     * @return amr文件时间长度
     * @throws java.io.IOException
     */
    public static int getAmrDuration(File file) throws Exception {
        long duration = -1;
        int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0 };
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            long length = file.length();// 文件的长度
            int pos = 6;// 设置初始位置
            int frameCount = 0;// 初始帧数
            int packedPos = -1;

            byte[] datas = new byte[1];// 初始数据值
            while (pos <= length) {
                randomAccessFile.seek(pos);
                if (randomAccessFile.read(datas, 0, 1) != 1) {
                    duration = length > 0 ? ((length - 6) / 650) : 0;
                    break;
                }
                packedPos = (datas[0] >> 3) & 0x0F;
                pos += packedSize[packedPos] + 1;
                frameCount++;
            }

            duration += frameCount * 20;// 帧数*20
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
        return (int)((duration/1000)+1);
    }
	/**
	 * 获取视频总时间
	 * @param video_path    视频路径
	 * @return
	 */
	public static int getVideoTime(String video_path) {
		List<String> commands = new java.util.ArrayList<String>();
//		if(PConfig.DevelopMode){
//			commands.add("/opt/app/xiuka/webapps/ROOT/ffmpeg/ffmpeg");// 视频提取工具的位置
//		}else{
//			commands.add("/var/tomcat/tomcat-7/webapps/ROOT/ffmpeg/ffmpeg");// 视频提取工具的位置
//		}
		commands.add("-i");
		commands.add(FileUtil.getAbsFilePath(video_path));
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			final Process p = builder.start();

			//从输入流中读取视频信息
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();

			//从视频信息中解析时长
			String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (/d*) kb//s";
			Pattern pattern = Pattern.compile(regexDuration);
			Matcher m = pattern.matcher(sb.toString());
			if (m.find()) {
				int time = getTimelen(m.group(1));
				log.info(video_path+",视频时长："+time+", 开始时间："+m.group(2)+",比特率："+m.group(3)+"kb/s");
				return time;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	//格式:"00:00:10.68"
	private static int getTimelen(String timelen){
		int min=0;
		String strs[] = timelen.split(":");
		if (strs[0].compareTo("0") > 0) {
			min+=Integer.valueOf(strs[0])*60*60;//秒
		}
		if(strs[1].compareTo("0")>0){
			min+=Integer.valueOf(strs[1])*60;
		}
		if(strs[2].compareTo("0")>0){
			min+=Math.round(Float.valueOf(strs[2]));
		}
		return min;
	}
}
