/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.io;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图象工具
 *
 * @author 吴智俊
 */
public class ImageUtil {
	
    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    
	/**
	 * 图象压缩 
	 * 
	 * @param sourcePath 源文件名
	 * @param targetPath 目标文件名
	 * @param width 压缩宽度
	 * @param hight 压缩高度
	 * @return 压缩是否成功
	 */
	public static Boolean compression(String sourcePath, String targetPath, int width, int hight) {
		
		try{
		    File sourceFile = new File(sourcePath);
	        File targetFile = new File(targetPath);
	        
		    String format = getFormatName(new File(sourcePath));
		    logger.info("Image Format is {}",format);
		    
		    BufferedImage srcImage = ImageIO.read(sourceFile);
			if (width > 0 || hight > 0) {
				srcImage = resize(srcImage, width, hight);
			}
			ImageIO.write(srcImage, format, targetFile);
		}catch(IIOException e){
		    logger.error("Image IIOException:{}",e);
			return false;
		}catch(IOException e){
		    logger.error("Image file IOException:{}",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 图象按比率压缩
	 * 
	 * @param source 源图象
	 * @param targetW 目标宽度
	 * @param targetH 目标长度
	 * @return BufferedImage
	 */
	private static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}
	
	/**
	 * 盘读图片格式
	 * 
	 * @param file 图片文件
	 * @return
	 * @throws IOException
	 */
	public static String getFormatName(File file) throws IOException {
	    ImageInputStream iis = ImageIO.createImageInputStream(file);
	    Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
	    while (iterator.hasNext()) {
	       ImageReader reader = (ImageReader)iterator.next();
	       return reader.getFormatName();
	    }
	    
	    throw new IOException("It is not image");
    }

}
