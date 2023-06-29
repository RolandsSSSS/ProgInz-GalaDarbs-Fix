package lv.venta.services.impl;

import java.util.List;

import lv.venta.models.Course;

public interface ICourseCRUDService {

	List<Course> selectAllCourses();
	
	Course selectCourseById(long idc);

}
