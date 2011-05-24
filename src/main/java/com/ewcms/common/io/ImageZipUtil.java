/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
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

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

/**
 * 图象压缩工具
 *
 * @author 吴智俊
 */
public class ImageZipUtil {
	
	/**
	 * 图象压缩 
	 * @param sourceFileName 源文件名
	 * @param targetFileName 目标文件名
	 * @param width 压缩宽度
	 * @param hight 压缩高度
	 * @return 压缩是否成功
	 */
	public static Boolean compression(String sourceFileName, String targetFileName, int width, int hight) {
		BufferedImage srcImage;
		String imgType = "JPEG";
		if (sourceFileName.toLowerCase().endsWith("png")) {
			imgType = "PNG";
		}else if (sourceFileName.toLowerCase().endsWith("bmp")){
			imgType = "BMP";
		}else if (sourceFileName.toLowerCase().endsWith("gif")){
			imgType = "GIF";
		}
		File sourceFile = new File(sourceFileName);
		File targetFile = new File(targetFileName);
		try{
			srcImage = ImageIO.read(sourceFile);
			if (width > 0 || hight > 0) {
				srcImage = resize(srcImage, width, hight);
			}
			ImageIO.write(srcImage, imgType, targetFile);
		}catch(IIOException e){
			return false;
		}catch(IOException e){
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

}
