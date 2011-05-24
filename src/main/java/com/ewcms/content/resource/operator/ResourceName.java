/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.content.resource.operator;

import com.ewcms.web.util.GlobaPath;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author wangwei
 */
public class ResourceName implements ResourceNameable {

    private static final Random random = new Random();
    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private static final String DOT = ".";

    private Builder builder;

    public static class Builder {

        private String dir;
        private String newName;
        private String newNameZip;
        private String persistDir;
        private String releasePath;
        private String releasePathZip;

        public Builder(final String rootDir, final String name) {
            dir = format.format(new Date());
            persistDir = createPersistDir(rootDir);
            long time = System.currentTimeMillis();
            int randomNumber = random.nextInt();
            randomNumber = (randomNumber < 0 ?  (randomNumber* -1 ):randomNumber);
            newName = createNewName(time,randomNumber,name);
            newNameZip = createNewImageZipName(time,randomNumber,name);
            releasePath = createReleaseaPath();
            releasePathZip = createReleaseaPathZip();
        }

        private String createNewName(long time,int random,final String name) {
            String singleName = name;
            if (isIncludePath(name)) {
                singleName = getSingleName(name);
            }
            if (isSuffix(singleName)) {
                int dotIndex = singleName.lastIndexOf(".") + 1;
                String suffix = singleName.substring(dotIndex).toLowerCase();
                return String.format("%s%d.%s",String.valueOf(time), random, suffix);
            }
            return String.format("%s%d",String.valueOf(time), random);
        }
        
        private String createNewImageZipName(long time,int random,final String name) {
            String singleName = name;
            if (isIncludePath(name)) {
                singleName = getSingleName(name);
            }
            if (isSuffix(singleName)) {
                int dotIndex = singleName.lastIndexOf(".") + 1;
                String suffix = singleName.substring(dotIndex).toLowerCase();
                return String.format("%s%d_zip.%s",String.valueOf(time), random, suffix);
            }
            return String.format("%s%d_zip",String.valueOf(time), random);
        }


        private boolean isIncludePath(final String name) {
            return name.indexOf("/") == -1 ? false : true;
        }

        private String getSingleName(final String name) {
            String[] names = name.split("/");
            return names[names.length - 1];
        }

        private boolean isSuffix(final String name) {
            return name.lastIndexOf(DOT) == -1 ? false : true;
        }

        private String createPersistDir(final String root) {
            if (root == null) {
                return dir;
            }
            if (root.endsWith("/")) {
                return String.format("%s%s", root, dir);
            } else {
                return String.format("%s/%s", root, dir);
            }
        }

        private String createReleaseaPath() {
            return String.format("%s/%s/%s", GlobaPath.ResourceDir.getPath(), dir, newName);
        }

        private String createReleaseaPathZip() {
            return String.format("%s/%s/%s", GlobaPath.ResourceDir.getPath(), dir, newNameZip);
        }

        public  ResourceName build(){
            return new ResourceName(this);
        }
    }

    private ResourceName(final Builder builder) {
        this.builder = builder;
    }

    @Override
    public String getNewName(){
        return builder.newName;
    }

    @Override
    public String getFileNewName() {
        return String.format("%s/%s", builder.persistDir, builder.newName);
    }

    @Override
    public String getPersistDir() {
        return builder.persistDir;
    }

    @Override
    public String getReleaseaPath() {
        return builder.releasePath;
    }

    @Override
    public String getReleasePathZip(){
        return builder.releasePathZip;
    }
    
    @Override
    public String getFileNewNameZip(){
    	return String.format("%s/%s", builder.persistDir, builder.newNameZip);
    }
}
