package com.pojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;










import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;






import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONStringer;


/**
 * Servlet implementation class MyServlet
*/
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private File[] file;              //�ļ�  
    private String[] fileFileName;    //�ļ���   
    private String[] filePath;        //�ļ�·��
    private String downloadFilePath;  //�ļ�����·��
    private InputStream inputStream; 
    
    public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String[] getFilePath() {
		return filePath;
	}

	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String filepath="uploadimg";
		String fileName="";
		response.setContentType("text/html;charset=utf-8");
		ServletOutputStream out = response.getOutputStream();
		String temFile = this.getServletContext().getRealPath("/");//��ȡ�ϴ��ļ���·��
		
		File file  = new File(temFile + "uploadimg");//���������ļ���Ŀ¼
		if (!file.exists()) {
		      file.mkdir();
		    }
		//�����ϴ��ļ���Ӳ�̶���
				//����commons-fileupload.jar/commons-io.jar���
		DiskFileItemFactory disk = new DiskFileItemFactory();
		long maxsize = 1024*1024*5;//�ϴ��ļ����������Ϊ5M
		disk.setSizeThreshold(5120);//�������С
		disk.setRepository(file);//���ϴ��ļ���Ŀ¼���뵽Ӳ�̶�����
		//�����ܹ������ϴ��ļ��Ķ�����������
		ServletFileUpload sfu = new ServletFileUpload(disk);
		sfu.setSizeMax(maxsize);//�����ϴ��ļ����������
		    List list =null;//java.io�ļ���
			try{
				list = sfu.parseRequest(request);//����ϴ��ļ��Ľ�����ͬʱ������������ݷ���list�д���
			}catch(Exception e){

				 request.setAttribute("upload.message", "�ϴ��ļ�ʧ�ܣ�");
			}
			Iterator iterator = list.iterator();
			Map<String, Object> map = new HashMap<String, Object>();
			while(iterator.hasNext()){
				FileItem  fileItem =(FileItem) iterator.next();//fileItem���ϴ��ļ�������
				if(fileItem.isFormField()){//�ж��ļ�����ͨ�ı������ļ����ϴ��ļ�����
					String fileDesc0 = fileItem.getString("utf-8");//��ȡ��ͨ�ı���
					String name=(fileItem.getFieldName());
					//System.out.println (name + "   " + fileItem.getName() + "   " +fileDesc0 );
				}else{
					fileName=new String(fileItem.getName().getBytes(),"utf-8");//��ֹ����������
				
					  int x = fileName.lastIndexOf("\\");
					  fileName = fileName.substring(x+1);

					 
					if(!"".equals(fileName)){
						FileOutputStream fos = new FileOutputStream(temFile + filepath + "\\" + fileName );
						if(fileItem.isInMemory()){
							try{
								fos.write(fileItem.get());
								}catch(Exception e){
								
							}finally{
								fos.close();
							}
						}else{
							InputStream is = fileItem.getInputStream();
							try{
							int i=-1;
							byte[] b= new byte[1024*2];
							i= is.read(b);
							while(i!=-1){
								fos.write(b,0,i);//д�뵽������
								i=is.read(b);
							}
							}catch(Exception e){
								
							}finally{
								is.close();
								fos.flush();
								fos.close();
							}
						}
					}
				}
//				String path= filepath + "\\" + fileName ;
//				System.out.println(path);	
//				 HashMap<String,String> jsonMap = new HashMap<String, String>();  
//			     jsonMap.put("path", path); 
//			     listmap.add(jsonMap);
				
			}
			map.put("message", "success");
		     JSONObject json = JSONObject.fromObject(map);  
		     out.write(json.toString().getBytes("UTF-8")); 
		     //System.out.println(json.toString());//request.getRequestDispatcher("/uploadcase.jsp").forward(request, response);
			
			out.flush();
			out.close();
				
		

	}

}
