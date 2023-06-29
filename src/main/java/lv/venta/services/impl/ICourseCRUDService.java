package lv.venta.services;

import java.util.List;

import lv.venta.models.Course;

public interface ICourseCRUDService {

	List<Course> selectAllCourses();

}
