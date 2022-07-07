package by.makei.shop.command.impl.admin;

import by.makei.shop.command.AttributeName;
import by.makei.shop.command.Command;
import by.makei.shop.command.Router;
import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.service.UserService;
import by.makei.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;

import static by.makei.shop.command.AttributeName.ID;

public class DeleteUserCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String currentPage = session.getAttribute(AttributeName.CURRENT_PAGE).toString();

        try{
            if(userService.delete(request)){
               logger.log(Level.DEBUG, "user has been successfully deleted");
               router.setCurrentPage(currentPage);
            }else{
                logger.log(Level.ERROR,"user has not been deleted");
                //return to page with error message?
                throw new CommandException("DeleteUser command error");
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, "DeleteUser command error {}", e.getMessage());
            throw new CommandException("DeleteUser command error", e);
        }

        return router;
    }
}
