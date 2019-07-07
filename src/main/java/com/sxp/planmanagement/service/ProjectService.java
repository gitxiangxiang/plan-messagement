package com.sxp.planmanagement.service;

import com.sxp.planmanagement.dao.ProjectDao;
import com.sxp.planmanagement.dao.UserToProjectDao;
import com.sxp.planmanagement.entity.Project;
import com.sxp.planmanagement.entity.UserToProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author sxp
 * @create 2019-07-05 10:13
 **/
@Service
public class ProjectService {

    @Autowired
    ProjectDao projectDao;
    @Autowired
    UserToProjectDao userToProjectDao;

    /**
     * 添加新项目
     * @param project
     * @return
     */
    public Project NewProject(Project project){
        Project project1 = projectDao.save(project);
        UserToProject userToProject = new UserToProject(
                project1.getId(),project.getManager(),new Date(),0,0);
        userToProjectDao.save(userToProject);
        return project1;
    }

    /**
     * 查看指定用户参与的项目
     * @param userId
     * @return
     */
    public List<Project> ViewMyProject(int userId){
        List<UserToProject> myUserToProjects = userToProjectDao.findByUserIdIs(userId);
        Set<Integer> myProjectIds = new LinkedHashSet<>();
        for (UserToProject userToProject : myUserToProjects) {
            myProjectIds.add(userToProject.getProjectId());
        }
        return projectDao.findByIdIn(myProjectIds);
    }

    public Project findById(int projectId){
        return projectDao.findByIdIs(projectId);
    }

}
