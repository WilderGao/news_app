package com.qg.newsapp.servlet;

import com.google.gson.Gson;
import com.qg.newsapp.dao.impl.FileDaoImpl;
import com.qg.newsapp.dao.impl.NewDaoImpl;
import com.qg.newsapp.model.FeedBack;
import com.qg.newsapp.model.News;
import com.qg.newsapp.model.ViceFile;
import com.qg.newsapp.utils.NowTime;
import com.qg.newsapp.utils.StatusCode;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * 发表新闻的Servlet
 */
@WebServlet(name = "AddNewsServlet",urlPatterns = "/admin/addnews")
public class AddNewsServlet extends HttpServlet {
    private static final String INIT_PARAM_SAVE_DIR_KEY = "UPLOAD_SAVE_DIR";
    private File saveDir;
    private int getNewId;
    private ViceFile viceFile;
    private News getNewsJson;
    private String regetPath;

    public void init(ServletConfig config) throws ServletException {
        String saveDirStr = config.getInitParameter(INIT_PARAM_SAVE_DIR_KEY);
        if (StringUtils.isEmpty(saveDirStr)) {
            saveDirStr = new StringBuffer(config.getServletContext().getRealPath("/"))
                    .append("\\saved").toString(); // 保存文件的文件夹
        }
        regetPath = saveDirStr;
        saveDir = new File(saveDirStr);
        saveDir.mkdir(); // 创建目录
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 构造工厂
        DiskFileItemFactory factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, saveDir);
        // 解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        Gson gson = new Gson();

        PrintWriter out = response.getWriter();
        try {
            getNewsJson = new News();
            List items = upload.parseRequest(request);
            Iterator iterator = items.iterator();
            NewDaoImpl newDao = new NewDaoImpl();
            FileDaoImpl fileDao = new FileDaoImpl();

            while (iterator.hasNext()) {

                FileItem item = (FileItem) iterator.next();
                if (!item.isFormField()) {

                    //第一次是发送json因此一定是先到下面，这里判断假如newsFace的参数是空的就说明全都是附件，假如有的话就证明第一个是封面
                    if (getNewsJson.getNewsFace()!=null){
                        //获得封面路径
                        String originalPath = item.getName();
                        String originalFileName = originalPath.substring(originalPath.lastIndexOf('\\') + 1);
                        //判断成功则证明第一张是一张封面，存到不一样的路径
                        File saveFace = new File("D://news_app//web//news_face");
                        if (!saveFace.exists())
                            saveFace.mkdir();
                        //将封面保存到指定的路径
                        File getFaceFile = new File(saveFace.getPath()+"\\"+originalFileName);
                        item.write(getFaceFile);

                        //接下来设置新闻类中封面的路径，并将整个新闻插入到新闻类数据库中
                        getNewsJson.setNewsFace(saveDir.getPath()+"\\"+originalFileName);
                        getNewsJson.setNewsTime(NowTime.CurrentTime());

                        newDao.AddNews(getNewsJson);
                        //并且得到新闻的Id用来作为储存附件的路径
                        getNewId = newDao.GetNewsId(getNewsJson);
                        System.out.println("新闻的Id为"+getNewId);

                        //执行了这里以后都不用执行，然后把news的封面路径清空，以后便不会进入到这个地方
                        getNewsJson.setNewsFace(null);
                        continue;
                    }

                    // 获得文件名
                    String originalPath = item.getName();
                    String originalFileName = originalPath.substring(originalPath.lastIndexOf('/') + 1);
                    System.out.println("文件名称为"+originalFileName);
                    // 构造File实例
                    saveDir = new File(saveDir.getPath()+"\\news_id："+getNewId);
                    if (!saveDir.exists())
                        saveDir.mkdir();
                    File uploadedFile = new File(saveDir.getPath() + "\\" +
                            originalFileName);
                    item.write(uploadedFile); // 写入文件
                    // 写入文件的时间
                    String dateStr = NowTime.CurrentTime();

                    //之后要将将附件信息先保存到附件类中
                    viceFile = new ViceFile();
                    viceFile.setFileName(originalFileName);
                    viceFile.setFilePath(saveDir.getPath()+"\\news_id："+getNewId+"\\"+originalFileName);
                    viceFile.setFilesUUID(getNewsJson.getFilesUUID());
                    viceFile.setNewsId(getNewId);
                    //将文件类插入数据库中
                    fileDao.addFile(viceFile);
                    //重新设置储存的路径
                    saveDir = new File(regetPath);

                }else{
                    //假如不是文件将是以json的形式传送过来,这个getJson就是以json形式的字符串
                    String getJson = item.getString();
                    System.out.println(getJson);
                    //将得到的文件信息打包成一个新闻对象

                    //将json数据打包成一个对象
                     getNewsJson = gson.fromJson(getJson,News.class);
                     getNewsJson.setNewsTime(NowTime.CurrentTime());

                    if (getNewsJson.getNewsBody().length()>1500) {
                        String newsBody =  getNewsJson.getNewsBody().substring(0, 1500);
                        getNewsJson.setNewsBody(newsBody);
                    }


                     if (getNewsJson.getNewsFace() == null) {
                         getNewsJson.setNewsFace("No Face");
                         newDao.AddNews(getNewsJson);
                         getNewId = newDao.GetNewsId(getNewsJson);
                         getNewsJson.setNewsFace(null);
                     }



                    //有新闻封面的话现在newFace就不是空的，再次进入循环
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            saveDir = new File(regetPath);
            FeedBack feedBack = new FeedBack();
            feedBack.setState(StatusCode.OK.getStatusCode());
            String feedBackString = gson.toJson(feedBack);
            out.write(feedBackString);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
