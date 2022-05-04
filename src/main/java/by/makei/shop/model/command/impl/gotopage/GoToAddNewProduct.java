package by.makei.shop.model.command.impl.gotopage;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.impl.AdminServiceImpl;
import by.makei.shop.util.PagePathExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

import static by.makei.shop.model.command.AttributeName.*;
import static by.makei.shop.model.command.PagePath.ADDNEWPRODUCT;


public class GoToAddNewProduct implements Command {


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String,Integer> brands;
        Map<String,Integer> types;
        Router router = new Router();
        AdminServiceImpl adminService = new AdminServiceImpl();
        HttpSession session = request.getSession();
        String currentPage = PagePathExtractor.extractPagePath(request);
        session.setAttribute(CURRENT_PAGE,currentPage);
        try {
            brands = adminService.getAllBrandsMap();
            types = adminService.getAllTypesMap();

            request.setAttribute(BRANDS_MAP, brands);
            request.setAttribute(TYPES_MAP, types);
            router.setCurrentPage(ADDNEWPRODUCT);
        } catch (ServiceException e) {
            //TODO!!!!!!!!!!!!!!!!!
            e.printStackTrace();
        }
        return router;
    }
}
