package org.bidtime.utils.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
 * methods. * * * @author huahua
 */

public class UploadFileService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String uploadPath = "d:/uploads/"; // 定义文件的上传路径
	//private static int MAXFILESIZE = 100 * 1024 * 1024; // 限制文件的上传大小

	public UploadFileService() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UploadFile.uploadFileOfRequest(request, response, uploadPath);
	}

	/**
	 * * Handles the HTTP <code>GET</code> method. * * @param request * servlet
	 * 请求 * @param response * servlet 响应
	 */
	// 如果是 GET 请求,则调用 doGet 方法.
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method. * * @param request * servlet
	 * 请求 * @param response * servlet 响应
	 */
	// 如果是 POST 请求,则调用 doPost 方法
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/** * Returns a short description of the servlet. */
	public String getServletInfo() {
		return "ServletName: FileUploaded. extends: HttpServlet.";
	}

	/*
	 * web.xml 中再配置一下: <servlet> <servlet-name>UploadHandler</servlet-name>
	 * <servlet-class>fileupload.FileUploaded</servlet-class> </servlet>
	 * <servlet-mapping> <servlet-name>UploadHandler</servlet-name>
	 * <url-pattern>/*</url-pattern> </servlet-mapping>
	 */
}