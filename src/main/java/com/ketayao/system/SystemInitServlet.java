/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.system.SystemInitServlet.java
 * Class:			SystemInitServlet
 * Date:			2011-11-14
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.0.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.ketayao.fensy.db.DBManager;
import com.ketayao.fensy.util.Exceptions;
import com.ketayao.lucene.LuceneTasker;
import com.ketayao.pojo.SiteConfig;
import com.ketayao.pojo.User;
import com.ketayao.search.IndexHolder;
import com.ketayao.util.QiNiuUtils;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @created 2011-11-14 下午3:12:07 
 */

public class SystemInitServlet extends HttpServlet {
    protected final Log       logger           = LogFactory.getLog(getClass());

    public static IndexHolder indexHolder;

    /** 描述  */
    private static final long serialVersionUID = -2320798905826904304L;

    @Override
    public void init() throws ServletException {
        initDruid();

        Properties properties = new Properties();
        InputStream inStream = null;
        try {
            // 初始化配置文件
            inStream = SystemInitServlet.class.getResourceAsStream("/loveJ.properties");
            properties.load(inStream);

            Set<Entry<Object, Object>> keys = properties.entrySet();
            for (Entry<Object, Object> entry : keys) {
                SystemConfig.getConfig().put(entry.getKey().toString(),
                    entry.getValue().toString());
            }

            getServletContext().setAttribute(Constants.SYSTEM_CONFIG, SystemConfig.getConfig());

            // 初始化上传路径		
            // 文件保存目录路径
            SystemConfig.ROOT_DIR = getServletContext().getRealPath("/");

            // 初始化文件扩展类型
            SystemConfig.EXTEND_TYPE.put("image",
                SystemConfig.getConfig().get("lovej.upload.image.type"));
            SystemConfig.EXTEND_TYPE.put("flash",
                SystemConfig.getConfig().get("lovej.upload.flash.type"));
            SystemConfig.EXTEND_TYPE.put("media",
                SystemConfig.getConfig().get("lovej.upload.media.type"));
            SystemConfig.EXTEND_TYPE.put("file",
                SystemConfig.getConfig().get("lovej.upload.file.type"));

            // 初始化数据库数据
            initDatabase();
            // 启动lucene更新
            initLucene();

            // 初始化七牛
            QiNiuUtils.init(properties);

            logger
                .warn("======================system initialize success==========================");
        } catch (Exception e) {
            logger.error("System initialize failure:" + Exceptions.getStackTraceAsString(e));
            System.exit(0);
            //throw new ServletException(e);
        }
    }

    private void initDruid() {
        WallConfig wallConfig = new WallConfig();
        wallConfig.setSelectIntoAllow(false);
        wallConfig.setReplaceAllow(false);
        wallConfig.setTruncateAllow(false);
        wallConfig.setCreateTableAllow(false);
        wallConfig.setAlterTableAllow(false);
        wallConfig.setDropTableAllow(false);
        wallConfig.setUseAllow(false);
        wallConfig.setDescribeAllow(false);
        wallConfig.setShowAllow(false);
        wallConfig.setCommitAllow(false);
        wallConfig.setRollbackAllow(false);

        DruidDataSource dataSource = (DruidDataSource) DBManager.getDataSource();
        System.out.println(dataSource);
    }

    @SuppressWarnings("unchecked")
    private void initDatabase() throws Exception {
        List<User> users = (List<User>) User.INSTANCE.list();
        if (users.size() < 1) {
            User user = new User();

            user.setUsername(SystemConfig.getConfig().get("lovej.user.username"));
            user.setEmail(SystemConfig.getConfig().get("lovej.user.email"));
            user.setNickname(SystemConfig.getConfig().get("lovej.user.nickname"));
            user.setPassword(SystemConfig.getConfig().get("lovej.user.password"));
            user.setRole(Byte.parseByte(SystemConfig.getConfig().get("lovej.user.role")));
            user.setSalt(SystemConfig.getConfig().get("lovej.user.salt"));
            user.setFrozen(Byte.parseByte(SystemConfig.getConfig().get("lovej.user.frozen")));
            user.save();
        }

        SiteConfig siteConfig = SiteConfig.INSTANCE.get(1);
        if (siteConfig == null) {
            siteConfig = new SiteConfig();
            siteConfig.setAbout(SystemConfig.getConfig().get("lovej.siteConfig.about"));
            siteConfig
                .setContactDescription(SystemConfig.getConfig().get("lovej.siteConfig.contact"));
            siteConfig.setIcp(SystemConfig.getConfig().get("lovej.siteConfig.icp"));
            siteConfig.setName(SystemConfig.getConfig().get("lovej.siteConfig.name"));
            siteConfig.setUrl(SystemConfig.getConfig().get("lovej.siteConfig.url"));

            siteConfig.save();
        }

        SystemConfig.setSiteConfig(siteConfig);
        getServletContext().setAttribute(Constants.SITE_CONFIG, SystemConfig.getSiteConfig());
    }

    private void initLucene() throws IOException {
        long scheduleTime = NumberUtils
            .toLong(SystemConfig.getConfig().get("blog.lucene.scheduleTime"), 1000 * 60 * 5);

        LuceneTasker tasker = new LuceneTasker(SystemConfig.getConfig().get("blog.lucene.path"),
            scheduleTime);
        Timer timer = new Timer();
        // 从现在开始 1 秒钟之后，每隔 1 秒钟执行一次 job1
        timer.schedule(tasker, 0, tasker.getScheduleTime());

        indexHolder = IndexHolder.init(tasker.getPath());
    }
}
