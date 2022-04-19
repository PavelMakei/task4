package by.makei.shop.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FooterTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();

    public int doStartTag() throws JspException {
        logger.log(Level.DEBUG,"Footer tag doStart");
        try {
            pageContext.getOut().print("© Lighting shop by Pavel Makei 2022");
        } catch (Exception ex) {
            logger.log(Level.ERROR,"Footer tag exception : {}", ex.getMessage());
            throw new JspTagException("SimpleTag: " +
                                      ex.getMessage());
        }
        return SKIP_BODY;
    }
    public int doEndTag() {
        logger.log(Level.DEBUG,"Footer tag doEnd");
        return EVAL_PAGE;
    }

}
