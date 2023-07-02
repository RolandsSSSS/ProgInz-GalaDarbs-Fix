package lv.venta.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import lv.venta.models.users.User;
import lv.venta.repos.users.IUserRepo;

public abstract class UserCRUDService implements IUserRepo {

	 @Autowired
	    private IUserRepo userRepo;



}
