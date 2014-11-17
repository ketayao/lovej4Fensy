/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.lucene.LuceneTask.java
 * Class:			LuceneTask
 * Date:			2013年9月5日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          3.1.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.lucene;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ketayao.pojo.Article;
import com.ketayao.search.IndexTask;
import com.ketayao.search.IndexTasker;
import com.ketayao.search.IndexUpdater;
import com.ketayao.search.Searchable;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  3.1.0
 * @since   2013年9月5日 上午10:02:45 
 */

public class LuceneTasker extends TimerTask implements IndexTasker {
    private static final Logger log          = LoggerFactory.getLogger(LuceneTasker.class);
    //private static int scheduleTime =  1000*60*5; // 五分钟

    private static String       path         = "D:\\fensy_lucene";
    private static long         scheduleTime = 1000 * 60 * 5;                              // 五分钟

    public LuceneTasker() {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public LuceneTasker(String path, long scheduleTime) {
        LuceneTasker.path = path;
        LuceneTasker.scheduleTime = scheduleTime;

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**  
     * 返回 path 的值   
     * @return path  
     */
    public String getPath() {
        return path;
    }

    /**  
     * 设置 path 的值  
     * @param path
     */
    public void setPath(String path) {
        LuceneTasker.path = path;
    }

    /**  
     * 返回 scheduleTime 的值   
     * @return scheduleTime  
     */
    public long getScheduleTime() {
        return scheduleTime;
    }

    /**  
     * 设置 scheduleTime 的值  
     * @param scheduleTime
     */
    public void setScheduleTime(long scheduleTime) {
        LuceneTasker.scheduleTime = scheduleTime;
    }

    /**   
     * @return  
     * @see net.oschina.common.search.IndexTasker#list()  
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<IndexTask> list() {
        long now = System.currentTimeMillis();
        long before = now - scheduleTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "modifyTime > '" + dateFormat.format(new Date(before)) + "'";
        // 取得scheduleTime时间之前的数据
        List<Article> addArticles = (List<Article>) Article.INSTANCE.list(sql);

        List<IndexTask> indexTasks = new ArrayList<IndexTask>();
        for (final Article article : addArticles) {
            indexTasks.add(new IndexTask() {

                @Override
                public Searchable object() {
                    return article;
                }

                @Override
                public byte getOpt() {
                    if (article.getTrash() == 1
                        || !article.getStatus().equals(Article.Status.PUBLISH)) {
                        return IndexTask.OPT_DELETE;
                    }

                    if (article.getPostTime().equals(article.getModifyTime())) {
                        return IndexTask.OPT_ADD;
                    }

                    return IndexTask.OPT_UPDATE;
                }

                @Override
                public void afterBuild() {
                    if (log.isDebugEnabled()) {
                        String tmp = null;
                        if (getOpt() == 1) {
                            tmp = "添加索引";
                        } else if (getOpt() == 2) {
                            tmp = "更新索引";
                        } else if (getOpt() == 4) {
                            tmp = "删除索引";
                        }
                        log.debug("articleId=" + article.getId() + ",OPT=" + tmp);
                    }
                }
            });
        }

        return indexTasks;
    }

    /**   
     *   
     * @see java.util.TimerTask#run()  
     */
    @Override
    public void run() {
        try {
            IndexUpdater.execute(new String[] { "-p", getPath(), "-t", this.getClass().getName() });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        LuceneTasker tasker = new LuceneTasker();
        timer.schedule(tasker, 0, tasker.getScheduleTime());
    }
}
