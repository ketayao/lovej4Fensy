/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Date:			2013年8月25日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>	
 *
 * </pre>
 **/

package com.ketayao.action.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ketayao.annotation.RolePermission;
import com.ketayao.fensy.mvc.IUser;
import com.ketayao.fensy.mvc.WebContext;
import com.ketayao.pojo.Track;
import com.ketayao.pojo.TrackList;
import com.ketayao.system.Constants;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.0.0
 * @since   2013年8月25日 上午9:45:14 
 */

public class TrackAction {

    private final static String READ      = "admin/site/track-read";

    private final static String PLAY_PATH = "/styles/dewplayer/playlist.xml";

    @RolePermission(role = IUser.ROLE_TOP)
    @SuppressWarnings("unchecked")
    public String r(WebContext rc) throws Exception {
        String playlist = rc.getContext().getRealPath("/") + PLAY_PATH;

        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding("UTF-8");
        File file = new File(playlist);
        Document document = saxReader.read(file);

        Element rootElement = document.getRootElement();
        Element tracksElement = rootElement.element("trackList");

        List<Track> tracks = new ArrayList<Track>();
        // 组装track
        Iterator<Element> eIterator = tracksElement.elementIterator();
        while (eIterator.hasNext()) {
            Track track = new Track();
            Element element = eIterator.next();

            track.setCreator(element.element("creator").getText());
            track.setLocation(element.element("location").getText());
            track.setTitle(element.element("title").getText());

            tracks.add(track);
        }

        rc.setRequestAttr("tracks", tracks);

        return READ;
    }

    @RolePermission(role = IUser.ROLE_TOP)
    @SuppressWarnings("rawtypes")
    public String u(WebContext rc) throws Exception {
        String playlist = rc.getContext().getRealPath("/") + PLAY_PATH;
        Document document = DocumentHelper.createDocument();

        Element rootElement = document.addElement("playlist");
        Element tracksElement = rootElement.addElement("trackList");

        TrackList trackList = new TrackList();
        Map map = rc.getRequest().getParameterMap();

        for (int i = 0; i < map.size() / 3; i++) {
            trackList.getTracks().add(new Track());
        }

        BeanUtils.populate(trackList, map);
        for (Track track : trackList.getTracks()) {
            //	过滤掉title或者location为空的track
            if (track.getTitle().trim().equals("") || track.getLocation().trim().equals("")) {
                continue;
            }
            Element trackElement = tracksElement.addElement("track");
            trackElement.addElement("creator").setText(track.getCreator());
            trackElement.addElement("location").setText(track.getLocation());
            trackElement.addElement("title").setText(track.getTitle());
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        XMLWriter writer = new XMLWriter(new FileOutputStream(new File(playlist)), format);
        writer.write(document);
        writer.close();

        rc.setRequestAttr(Constants.OPERATION_SUCCESS, Constants.OPERATION_SUCCESS);

        return r(rc);
    }
}
