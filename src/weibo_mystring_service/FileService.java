package weibo_mystring_service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;

public class FileService {
	/**
	 * save or read file for temporary friends
	 * @author samsung
	 *
	 */
	private Context context;
	public FileService(Context context){
		this.context = context;
	}
	public void save(String id,String num,String price) throws Exception{
		FileOutputStream outStream = context.openFileOutput("temp", Context.MODE_PRIVATE);
		outStream.write(id.getBytes());
		outStream.write(';');
		outStream.write(num.getBytes());
		outStream.write(';');
		outStream.write(price.getBytes());
		outStream.close();
	}
	public String read() throws Exception{
		FileInputStream inStream = context.openFileInput("temp");
		int len = 0;
		byte[] buffer = new byte [1024];
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while((len = inStream.read(buffer))!=-1){
			outStream.write(buffer, 0, len);
		}
		byte data[] = outStream.toByteArray();
		inStream.close();
		return new String(data);
	}
	public void deleteFile() {
		File file1 = new File("temp");
		if (file1.exists()) { // 判断文件是否存在
		if (file1.isFile()) { // 判断是否是文件
		file1.delete(); // delete()方法 你应该知道 是删除的意思;
		}
		}
		}
}
