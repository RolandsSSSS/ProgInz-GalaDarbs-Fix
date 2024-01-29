package lv.venta.services;

import org.springframework.beans.factory.annotation.Autowired;

import lv.venta.repos.users.IUserRepo;

public abstract class UserCRUDService implements IUserRepo {

	 @Autowired
	    private IUserRepo userRepo;



}
