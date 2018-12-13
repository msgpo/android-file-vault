package com.vandenbreemen.secretcamera.mvp.projects

import com.vandenbreemen.mobilesecurestorage.patterns.mvp.PresenterContract
import com.vandenbreemen.mobilesecurestorage.patterns.mvp.View
import com.vandenbreemen.secretcamera.api.Project

/*

VIPER-based pattern for managing projects

 */

interface ProjectListPresenter : PresenterContract {
    fun addProject(project: Project)
    fun viewProjectDetails(project: Project)
}

interface ProjectListView : View {
    fun showProjects(projects: List<Project>)
}

interface ProjectListRouter {
    fun gotoProjectDetails(project: Project)

}
