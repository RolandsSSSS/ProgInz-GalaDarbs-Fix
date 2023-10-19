package lv.venta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.venta.models.Course;
import lv.venta.models.users.Student;
import lv.venta.repos.ICourseRepo;
import lv.venta.services.impl.ICourseCRUDService;

@Service
public class CourseCRUDService implements ICourseCRUDService {

	@Autowired
	private ICourseRepo courseRepo;
	
	@Autowired
	private final SystemLogger systemLogger;

	@Autowired
	public CourseCRUDService(ICourseRepo courseRepo, SystemLogger systemLogger) {
		this.systemLogger = systemLogger;
		this.courseRepo = courseRepo;
	}

	@Override
	public List<Course> selectAllCourses() {
		systemLogger.logInfo("Atlasīti visi kursi.");
		return (List<Course>) courseRepo.findAll();
	}

	@Override
	public Course selectCourseById(long idc) {
		for (Course course : selectAllCourses()) {
			if (course.getIdc() == idc) {
				systemLogger.logInfo("Atlasīts kurss ar ID: " + idc);
				return course;
			}
		}
		systemLogger.logWarning("Kurss ar ID " + idc + " netika atrasts.");
		return null;
	}

	@Override
	public void deleteCourseById(long idc) {
		Course course = selectCourseById(idc);
		if (course != null) {
			systemLogger.logInfo("Izdzēsts kurss ar ID: " + idc);
			
			for (Student student : course.getDebtStudents()) {
				student.getDebtCourses().remove(course);
			}
			course.getDebtStudents().clear();
			courseRepo.delete(course);
		} else {
	        systemLogger.logWarning("Mēģināts izdzēst neesošu kursu ar ID " + idc);
	    }
	}

	@Override
	public void updateCourseById(long idc, Course updatedCourse) {
		Course course = selectCourseById(idc);
		if (course != null) {
			course.setTitle(updatedCourse.getTitle());
			course.setCreditPoints(updatedCourse.getCreditPoints());
			courseRepo.save(course);
			
			systemLogger.logInfo("Kurss atjaunināts ar ID: " + idc);
		} else {
	        systemLogger.logWarning("Mēģināts atjaunināt neesošu kursu ar ID " + idc);
		}
	}
	
	@Override
	public void insertNewCourse(Course course) {
		for(Course course1 : selectAllCourses()) {
			if(course1.getTitle().equals(course.getTitle()) && course1.getCreditPoints() == (course.getCreditPoints())) {
				return;
			}
		}
		selectAllCourses().add(course);
		courseRepo.save(course);
		
		systemLogger.logInfo("Ievietots jauns kurss: " + course.getTitle());
	}

}
