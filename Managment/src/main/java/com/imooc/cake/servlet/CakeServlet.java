package com.imooc.cake.servlet;

import com.imooc.cake.entity.Cake;
import com.imooc.cake.entity.Category;
import com.imooc.cake.service.CakeService;
import com.imooc.cake.service.CategoryService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CakeServlet", urlPatterns = {"/cake/list.do", "/cake/addPrompt.do", "/cake/addCake.do"})
public class CakeServlet extends HttpServlet {

    private CakeService cakeService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        cakeService = new CakeService();
        categoryService = new CategoryService();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/cake/list.do".equals(request.getServletPath())) {
            String categoryIdStr = request.getParameter("categoryId");
            try {
                Long categoryId = null;
                List<Cake> cakes = null;
                if (categoryIdStr != null) {
                    categoryId = Long.parseLong(categoryIdStr);
                    cakes = cakeService.getCakesByCategoryId(categoryId, 1, 10);
                } else {
                    cakes = cakeService.getCakes(1, 10);
                }
                List<Category> categories = categoryService.getCategories();
                request.setAttribute("cakes", cakes);
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/WEB-INF/views/biz/cake_list.jsp").forward(request, response);
            }catch (NumberFormatException e) {
                request.getRequestDispatcher("/WEB-INF/views/biz/cake_list.jsp").forward(request, response);
            }
        } else if ("/cake/addPrompt.do".equals(request.getServletPath())) {
            List<Category> categories = categoryService.getCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/biz/add_cake.jsp").forward(request, response);
        } else if ("/cake/addCake.do".equals(request.getServletPath())) {
            if (ServletFileUpload.isMultipartContent(request)) {
                ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
                try {
                    Cake cake = new Cake();
                    cake.setCreateTime(new Date());
                    cake.setUpdateTime(new Date());
                    List<FileItem> fileItems = upload.parseRequest(request);
                    for (FileItem fileItem:
                         fileItems) {
                        if (fileItem.isFormField()) {
                            if ("categoryId".equals(fileItem.getFieldName())) {
                                cake.setCategoryId(Long.parseLong(fileItem.getString("utf-8")));
                            } else if ("level".equals(fileItem.getFieldName())) {
                                cake.setLevel(Integer.parseInt(fileItem.getString("utf-8")));
                            } else if ("price".equals(fileItem.getFieldName())) {
                                cake.setPrice(Integer.parseInt(fileItem.getString("utf-8")));
                            } else if ("name".equals(fileItem.getFieldName())) {
                                cake.setName(fileItem.getString("utf-8"));
                            }
                        } else {
                            cake.setSmallImg(fileItem.get());
                        }
                    }
                    cakeService.addCake(cake);
                    request.getRequestDispatcher("/cake/list.do").forward(request, response);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void destroy() {
        cakeService = null;
        categoryService = null;
    }

}
