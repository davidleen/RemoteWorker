package com.eu.remote.test;

import java.util.List;

public class Data_Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long projectId;
	private String projectIdDic;
	private long taskId;
	private String taskName;
	private int taskType;
	private String taskTypeDic;
	private int timeProgress;
	private int executiveProgress;
	private int seriousDegree;
	private int seriousDegreeDic;

	public String seriousDegreeDicDic;

	private int isExceed;
	private String endTime;
	private String startTime;
	private double remainDays;
	private long executorId;
	private String executorIdDic;
	private long originatorId;
	private String originatorIdDic;
	private int executeState;
	private String executeStateDic;
	private String target;
	private String place;
	private String note;
	private int hasFeedBack;
	private String orgName;

	private String finishTime;
	private int isNew = 0;
	private int isEdit = 0;
	private String lastFeedbackTime;

	private List<Data_Attachment> attachment;
	private String sendEmail;

	public int allTaskCheckCount;// 总任务清单数
	public int finishTaskCheckCount;// 已完成任务清单数

	public String copyToEmpNames;
	public String copyToEmpIds;

	public int commentType;

	public int classesId;

	public Data_Task() {
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectIdDic() {
		return projectIdDic;
	}

	public void setProjectIdDic(String projectIdDic) {
		this.projectIdDic = projectIdDic;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public String getTaskTypeDic() {
		return taskTypeDic;
	}

	public void setTaskTypeDic(String taskTypeDic) {
		this.taskTypeDic = taskTypeDic;
	}

	public int getTimeProgress() {
		return timeProgress;
	}

	public void setTimeProgress(int timeProgress) {
		this.timeProgress = timeProgress;
	}

	public int getExecutiveProgress() {
		return executiveProgress;
	}

	public void setExecutiveProgress(int executiveProgress) {
		this.executiveProgress = executiveProgress;
	}

	public int getSeriousDegree() {
		return seriousDegree;
	}

	public void setSeriousDegree(int seriousDegree) {
		this.seriousDegree = seriousDegree;
	}

	public int getIsExceed() {
		return isExceed;
	}

	public void setIsExceed(int isExceed) {
		this.isExceed = isExceed;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public double getRemainDays() {
		return remainDays;
	}

	public void setRemainDays(double remainDays) {
		this.remainDays = remainDays;

	}

	public long getExecutorId() {
		return executorId;
	}

	public void setExecutorId(long executorId) {
		this.executorId = executorId;
	}

	public String getExecutorIdDic() {
		return executorIdDic;
	}

	public void setExecutorIdDic(String executorIdDic) {
		this.executorIdDic = executorIdDic;
	}

	public long getOriginatorId() {
		return originatorId;
	}

	public void setOriginatorId(long originatorId) {
		this.originatorId = originatorId;
	}

	public String getOriginatorIdDic() {
		return originatorIdDic;
	}

	public void setOriginatorIdDic(String originatorIdDic) {
		this.originatorIdDic = originatorIdDic;
	}

	public int getExecuteState() {
		return executeState;
	}

	public void setExecuteState(int executeState) {
		this.executeState = executeState;
	}

	public String getExecuteStateDic() {
		return executeStateDic;
	}

	public void setExecuteStateDic(String executeStateDic) {
		this.executeStateDic = executeStateDic;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getHasFeedBack() {
		return hasFeedBack;
	}

	public void setHasFeedBack(int hasFeedBack) {
		this.hasFeedBack = hasFeedBack;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getSeriousDegreeDic() {
		return seriousDegreeDic;
	}

	public void setSeriousDegreeDic(int seriousDegreeDic) {
		this.seriousDegreeDic = seriousDegreeDic;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	public int getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}

	public String getLastFeedbackTime() {
		return lastFeedbackTime;
	}

	public void setLastFeedbackTime(String lastFeedbackTime) {
		this.lastFeedbackTime = lastFeedbackTime;
	}

	public List<Data_Attachment> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<Data_Attachment> attachment) {
		this.attachment = attachment;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o instanceof Data_Task) {
			return taskId == ((Data_Task) o).getTaskId();
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Data_Task [projectId=").append(projectId).append(", projectIdDic=").append(projectIdDic).append(", taskId=").append(taskId)
				.append(", taskName=").append(taskName).append(", taskType=").append(taskType).append(", taskTypeDic=").append(taskTypeDic)
				.append(", timeProgress=").append(timeProgress).append(", executiveProgress=").append(executiveProgress).append(", seriousDegree=")
				.append(seriousDegree).append(", seriousDegreeDic=").append(seriousDegreeDic).append(", seriousDegreeDicDic=").append(seriousDegreeDicDic)
				.append(", isExceed=").append(isExceed).append(", endTime=").append(endTime).append(", startTime=").append(startTime).append(", remainDays=")
				.append(remainDays).append(", executorId=").append(executorId).append(", executorIdDic=").append(executorIdDic).append(", originatorId=")
				.append(originatorId).append(", originatorIdDic=").append(originatorIdDic).append(", executeState=").append(executeState)
				.append(", executeStateDic=").append(executeStateDic).append(", target=").append(target).append(", place=").append(place).append(", note=")
				.append(note).append(", hasFeedBack=").append(hasFeedBack).append(", orgName=").append(orgName).append(", finishTime=").append(finishTime)
				.append(", isNew=").append(isNew).append(", isEdit=").append(isEdit).append(", lastFeedbackTime=").append(lastFeedbackTime)
				.append(", attachment=").append(attachment).append(", sendEmail=").append(sendEmail).append(", allTaskCheckCount=").append(allTaskCheckCount)
				.append(", finishTaskCheckCount=").append(finishTaskCheckCount).append(", copyToEmpNames=").append(copyToEmpNames).append(", copyToEmpIds=")
				.append(copyToEmpIds).append(", commentType=").append(commentType).append(", classesId=").append(classesId).append("]");
		return builder.toString();
	}

}