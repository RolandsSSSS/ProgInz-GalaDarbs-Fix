package lv.venta.services.impl;

import java.util.List;

import lv.venta.models.users.User;

public interface IUserCRUDService {
	List<User> selectAllUsers();

	User selectUserById(long idc);

	void deleteUserById(long idc);

	void updateUserById(long idc, User updatedUser);

	void insertNewUser(User User);
}
