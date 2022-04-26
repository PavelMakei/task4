package by.makei.shop.model.command.impl.common;

import by.makei.shop.exception.CommandException;
import by.makei.shop.exception.ServiceException;
import by.makei.shop.model.command.Command;
import by.makei.shop.model.command.Router;
import by.makei.shop.model.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import static by.makei.shop.model.command.AttributeName.BRANDS_MAP;
import static by.makei.shop.model.command.AttributeName.TYPES_MAP;
import static by.makei.shop.model.command.PagePath.ADDNEWPRODUCT;

public class GoToAddNewProduct implements Command {


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<String,Integer> brands;
        Map<String,Integer> types;
        Router router = new Router();
        AdminServiceImpl adminService = new AdminServiceImpl();
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
