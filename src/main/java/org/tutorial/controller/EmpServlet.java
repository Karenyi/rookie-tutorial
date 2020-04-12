package org.tutorial.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tutorial.model.EmpVO;
import org.tutorial.service.EmpService;
import org.tutorial.service.impl.EmpServiceImpl;

@WebServlet("/emp/emp.do")
public class EmpServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");


        if ("getOne_For_Display".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                //1.接收請求參數 - 輸入格式的錯誤處理
                String str = req.getParameter("empno");
                if (str == null || (str.trim()).length() == 0) {
                    errorMsgs.add("請輸入員工編號");
                }
                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/emp/index.jsp");
                    failureView.forward(req, res);
                    return;
                }

                Integer empno = null;
                try {
                    empno = new Integer(str);
                } catch (Exception e) {
                    errorMsgs.add("員工編號格式不正確");
                }
                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/emp/index.jsp");
                    failureView.forward(req, res);
                    return;//程式中斷
                }

                //2.開始查詢資料
                EmpService empSvc = new EmpServiceImpl();
                EmpVO empVO = empSvc.getOneEmp(empno);
                if (empVO == null) {
                    errorMsgs.add("查無資料");
                }
                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/emp/index.jsp");
                    failureView.forward(req, res);
                    return;
                }

                //3.查詢完成,準備轉交(Send the Success view)
                req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
                String url = "/emp/listOne.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOne.jsp
                successView.forward(req, res);

                //其他可能的錯誤處理
            } catch (Exception e) {
                errorMsgs.add("無法取得資料:" + e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/emp/index.jsp");
                failureView.forward(req, res);
            }
        }


        if ("getOne_For_Update".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                //1.接收請求參數
                Integer empno = new Integer(req.getParameter("empno"));

                //2.開始查詢資料
                EmpService empSvc = new EmpServiceImpl();
                EmpVO empVO = empSvc.getOneEmp(empno);

                //3.查詢完成,準備轉交(Send the Success view)
                req.setAttribute("empVO", empVO);         // 資料庫取出的empVO物件,存入req
                String url = "/emp/update.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update.jsp
                successView.forward(req, res);

                //其他可能的錯誤處理
            } catch (Exception e) {
                errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/emp/listAll.jsp");
                failureView.forward(req, res);
            }
        }


        if ("update".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                //1.接收請求參數 - 輸入格式的錯誤處理
                Integer empno = new Integer(req.getParameter("empno").trim());

                String ename = req.getParameter("ename");
                if (ename == null || ename.trim().length() == 0) {
                    errorMsgs.add("員工姓名: 請勿空白");
                }
                //以下練習正則(規)表示式(regular-expression)
                String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
                if(!ename.trim().matches(enameReg) ) {
                    errorMsgs.add("員工姓名:只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
                }

                String job = req.getParameter("job").trim();
                java.sql.Date hiredate = null;
                try {
                    hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
                } catch (IllegalArgumentException e) {
                    hiredate=new java.sql.Date(System.currentTimeMillis());
                    errorMsgs.add("請輸入日期!");
                }

                Double sal = null;
                try {
                    sal = new Double(req.getParameter("sal").trim());
                } catch (NumberFormatException e) {
                    sal = 0.0;
                    errorMsgs.add("薪水請填數字.");
                }

                Double comm = null;
                try {
                    comm = new Double(req.getParameter("comm").trim());
                } catch (NumberFormatException e) {
                    comm = 0.0;
                    errorMsgs.add("獎金請填數字.");
                }

                Integer deptno = new Integer(req.getParameter("deptno").trim());

                EmpVO empVO = new EmpVO();
                empVO.setEmpno(empno);
                empVO.setEname(ename);
                empVO.setJob(job);
                empVO.setHiredate(hiredate);
                empVO.setSal(sal);
                empVO.setComm(comm);
                empVO.setDeptno(deptno);

                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/emp/update.jsp");
                    failureView.forward(req, res);
                    return;
                }

                //2.開始修改資料
                EmpService empSvc = new EmpServiceImpl();
                empVO = empSvc.updateEmp(empno, ename, job, hiredate, sal,comm, deptno);

                //3.修改完成,準備轉交(Send the Success view)
                req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
                String url = "/emp/listOne.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOne.jsp
                successView.forward(req, res);

                //其他可能的錯誤處理
            } catch (Exception e) {
                errorMsgs.add("修改資料失敗:"+e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/emp/update.jsp");
                failureView.forward(req, res);
            }
        }

        if ("insert".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                //1.接收請求參數 - 輸入格式的錯誤處理
                String ename = req.getParameter("ename");
                if (ename == null || ename.trim().length() == 0) {
                    errorMsgs.add("員工姓名: 請勿空白");
                }
                //以下練習正則(規)表示式(regular-expression)
                String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
                if(!ename.trim().matches(enameReg) ) {
                    errorMsgs.add("員工姓名:只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
                }

                String job = req.getParameter("job").trim();
                java.sql.Date hiredate = null;
                try {
                    hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
                } catch (IllegalArgumentException e) {
                    hiredate=new java.sql.Date(System.currentTimeMillis());
                    errorMsgs.add("請輸入日期!");
                }

                Double sal = null;
                try {
                    sal = new Double(req.getParameter("sal").trim());
                } catch (NumberFormatException e) {
                    sal = 0.0;
                    errorMsgs.add("薪水請填數字.");
                }

                Double comm = null;
                try {
                    comm = new Double(req.getParameter("comm").trim());
                } catch (NumberFormatException e) {
                    comm = 0.0;
                    errorMsgs.add("獎金請填數字.");
                }

                Integer deptno = new Integer(req.getParameter("deptno").trim());

                EmpVO empVO = new EmpVO();
                empVO.setEname(ename);
                empVO.setJob(job);
                empVO.setHiredate(hiredate);
                empVO.setSal(sal);
                empVO.setComm(comm);
                empVO.setDeptno(deptno);

                // Send the use back to the form, if there were errors
                if (!errorMsgs.isEmpty()) {
                    req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
                    RequestDispatcher failureView = req
                            .getRequestDispatcher("/emp/add.jsp");
                    failureView.forward(req, res);
                    return;
                }

                //2.開始新增資料
                EmpService empSvc = new EmpServiceImpl();
                empVO = empSvc.addEmp(ename, job, hiredate, sal, comm, deptno);

                //3.新增完成,準備轉交(Send the Success view)
                String url = "/emp/listAll.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAll.jsp
                successView.forward(req, res);

                //其他可能的錯誤處理
            } catch (Exception e) {
                errorMsgs.add(e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/emp/add.jsp");
                failureView.forward(req, res);
            }
        }


        if ("delete".equals(action)) {

            List<String> errorMsgs = new LinkedList<>();
            req.setAttribute("errorMsgs", errorMsgs);

            try {
                //1.接收請求參數
                Integer empno = new Integer(req.getParameter("empno"));

                //2.開始刪除資料
                EmpService empSvc = new EmpServiceImpl();
                empSvc.deleteEmp(empno);

                //3.刪除完成,準備轉交(Send the Success view)
                String url = "/emp/listAll.jsp";
                RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
                successView.forward(req, res);

                //其他可能的錯誤處理
            } catch (Exception e) {
                errorMsgs.add("刪除資料失敗:"+e.getMessage());
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/emp/listAll.jsp");
                failureView.forward(req, res);
            }
        }
    }

}
