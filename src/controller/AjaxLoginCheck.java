package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.UserDao;
import vo.User;

/**
 * Servlet implementation class AjaxLoginCheck
 */
@WebServlet(urlPatterns="/AjaxLoginCheck.do")
public class AjaxLoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		//设置编码
		System.out.println("11111");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		//获取表单数据
		String userName=request.getParameter("name");
		String password=request.getParameter("password");
		String userCode=request.getParameter("userCode");
		String check=request.getParameter("box");
		System.out.println(userName+password+userCode+check);
		
		//获取session
		HttpSession session = request.getSession();
		//获取验证码
		String vCode=session.getAttribute("verityCode").toString();
		System.out.println(vCode);
		//存放返回信息的map
		Map<String ,Object> map=new HashMap<String, Object>();
		//比较输入的验证码和随机生成的验证码是否相同
		
		if(!vCode.equalsIgnoreCase(userCode)) {
			//在map中存放返回数据
			map.put("code", 1);
			map.put("info", "验证码不正确");
		}else {//验证正确
			UserDao userDao=new UserDao();
			User user=null;
			try {
				user = userDao.get(userName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(user==null) {//用户名不存在
				map.put("code", 2);
				map.put("info", "用户名不存在");
			}else {//用户名存在
				if(!user.getPassword().equals(password)) {//密码不正确
					//System.out.println(user.getPassword());
					//System.out.println(password);
					map.put("code", 3);
					map.put("info", "密码不正确");
				}else {//用户名密码正确
					//将需要传递的数据存放在session的域范围中，一个会话阶段的所以程序都可以从中获取
					session.setAttribute("user1", user);
					session.setAttribute("userName", userName);
					if(check!=null) {//免登录选中
						//设置cookie
						Cookie cookie =new Cookie("userName", userName);
				    	cookie.setMaxAge(7 * 24 *24 *24);
					    cookie.setPath("/");
					    response.addCookie(cookie);
					    Cookie cookie2 =new Cookie("password", password);					 
					    cookie2.setMaxAge(7 * 24 *24 *24);
					    cookie2.setPath("/");
					    response.addCookie(cookie2);
					}
					map.put("code", 0);
					map.put("info", "登录成功!");
				}
			}
			
		}
		
		//调用谷歌的Gson库将map类型数据转换为json字符串
		String jsonStr=new Gson().toJson(map);
		//字符流输出字符串
		response.setContentType("text/html;chraset=utf-8");
		PrintWriter out=response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
		
	}

}
