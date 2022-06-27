package by.makei.shop.util;

import by.makei.shop.exception.ServiceException;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public final class ImageConverter {
    private static final Logger logger = LogManager.getLogger();
    private static final String CHARSET_NAME = "UTF-8";
    private static final String IMAGE_FORMAT = "jpeg";


    private ImageConverter(){}

    /**
     * convert image to String format to be sent/used on jsp pages
     * @param imageBytes - an image as byte array
     * @return coverted to String image
     */
    public static String imageToString(byte[] imageBytes) {
        String base64Encoded = null;
        try {
            byte[] encodeBase64 = Base64.encodeBase64(imageBytes);
            base64Encoded = new String(encodeBase64, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.WARN,"ImageConverter imageToString can't be reached. {}",e.getMessage());
        }
        return base64Encoded;
    }
//TODO обрезать большие картинки?

    /**
     * Resize image
     * @param data image as byte array
     * @param nw - new width
     * @param nh - new higth
     * @return converted image as byte array
     * @throws ServiceException
     */
    private static byte[] changeImgSize(byte[] data, int nw, int nh) throws ServiceException {
        byte[] newdata = null;
        try{
            BufferedImage bis = ImageIO.read(new ByteArrayInputStream(data));
            int w = bis.getWidth();
            int h = bis.getHeight();
            double sx = (double) nw / w;
            double sy = (double) nh / h;
            AffineTransform transform = new AffineTransform();
            transform.setToScale(sx, sy);
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            //Original color
            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            ato.filter(bis, bid);

            //Convert to byte byte
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bid, IMAGE_FORMAT, baos);
            newdata = baos.toByteArray();

        }catch(IOException e){
            logger.log(Level.ERROR,"Image converter changeImgSize IO exception :{}",e.getMessage());
            throw new ServiceException("Image converter changeImgSize IO exception", e);

        }
        return newdata;
    }
}
